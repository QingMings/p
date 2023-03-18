package com.yhl;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * http websocket server
 */
public class PWS extends AbstractVerticle {
  private static final Logger logger = LoggerFactory.getLogger(PWS.class);

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    Router router = Router.router(vertx);
    router.route("/*").handler(StaticHandler.create("webrootold"));
    vertx.createHttpServer()
      .requestHandler(router)
      .listen(8083)
      .onSuccess(ok -> {
        logger.info("🚀 PWS started");
        startPromise.complete();
      })
      .onFailure(err -> logger.error("❌ PWS failed to listen", err));
  }
}
