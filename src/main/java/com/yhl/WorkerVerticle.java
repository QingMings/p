package com.yhl;

import io.vertx.core.AbstractVerticle;

public class WorkerVerticle  extends AbstractVerticle {


  @Override
  public void start() throws Exception {
    System.out.println("[Worker] Starting in " + Thread.currentThread().getName());

    vertx.eventBus().<String>consumer("sample.data", message -> {

      String body = message.body();
      try {
        Thread.sleep(2000);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
//      System.out.println("[Worker] Consuming data in " + Thread.currentThread().getName());
      message.reply(body.toUpperCase()+"[Worker] Consuming data in " + Thread.currentThread().getName());
    });
  }
}
