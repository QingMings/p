package com.yhl;

import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.*;
import io.vertx.core.json.JsonObject;
import io.vertx.core.spi.resolver.ResolverProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

import static java.lang.System.currentTimeMillis;

public class P extends AbstractVerticle {
  private static final Logger logger = LoggerFactory.getLogger(P.class);

  @Override
  public void start() throws Exception {
    long startTime = currentTimeMillis();
    Future<String> pwsDeployment = vertx.deployVerticle(new PWS());
    pwsDeployment
      .onSuccess(ok -> logger.info("✅ Application started in {}ms", currentTimeMillis() - startTime))
      .onFailure(err -> logger.error("❌ Application failed to start", err));
  }


  @Override
  public void stop() throws Exception {

  }
}
