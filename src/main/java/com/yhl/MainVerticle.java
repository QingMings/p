package com.yhl;

import cn.hutool.core.date.DateUtil;
import com.yhl.auth.UserNamePasswordAuth;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.authentication.AuthenticationProvider;
import io.vertx.ext.bridge.PermittedOptions;
import io.vertx.ext.stomp.*;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.random.RandomGenerator;

public class MainVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    AuthenticationProvider authorizationProvider = new UserNamePasswordAuth();
    StompServerHandler stompServerHandler = StompServerHandler.create(vertx)
      .authProvider(authorizationProvider)
      .bridge(new BridgeOptions()
        .addInboundPermitted(new PermittedOptions().setAddress("/toBus"))
        .addInboundPermitted(new PermittedOptions().setAddress("/reqSingRev"))
        .addOutboundPermitted(new PermittedOptions().setAddress("/respSingRev"))
        .addOutboundPermitted(new PermittedOptions().setAddress("/toStomp"))
      )
      .destinationFactory((v, name) -> {
        System.out.println(name);
        if (name.startsWith("/queue")) {
          System.out.println();
          return Destination.queue(vertx, name);
        } else {
          return Destination.topic(vertx, name);
        }
      });


    StompServer stompServer = StompServer.create(vertx, new StompServerOptions()
      .setHeartbeat(
        new JsonObject().put("x", 1000).put("y", 1000))
      .setPort(-1)      // disable tcp server
      .setSecured(true)
      .setWebsocketBridge(true)
      .setWebsocketPath("/stomp")).handler(stompServerHandler);

    HttpServer server = vertx.createHttpServer(new HttpServerOptions()
      .setWebSocketSubProtocols(Arrays.asList("v10.stomp", "v11.stomp", "v12.stomp")));
    Router router = Router.router(vertx);
    router.route("/*").handler(StaticHandler.create());
    server.requestHandler(router).webSocketHandler(stompServer.webSocketHandler()).listen(8081);

    AtomicInteger atomicInteger = new AtomicInteger(0);
    vertx.setPeriodic(1000, l -> {
//      vertx.eventBus().publish("/toStomp", JsonObject.of("fromServer",atomicInteger.incrementAndGet()).toBuffer());
//      vertx.eventBus().publish("/respSingRev", JsonObject.of("fromServerrespSingRev",atomicInteger.incrementAndGet()).toBuffer());
      vertx.eventBus().request("sample.data", String.valueOf(RandomGenerator.getDefault().nextInt()), r -> {
        System.out.println(DateUtil.now() + " " + r.result().body());
      });
    });
    vertx.deployVerticle(WorkerVerticle.class, new DeploymentOptions().setWorker(true).setInstances(5));
    startPromise.complete();
  }

}
