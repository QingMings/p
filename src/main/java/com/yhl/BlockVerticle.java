package com.yhl;

import cn.hutool.core.collection.ListUtil;
import io.vertx.core.*;
import io.vertx.core.json.JsonObject;

import java.util.List;

public class BlockVerticle extends AbstractVerticle {

  public static final int LIMIT_SIZE=1000;

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    long  count = 10000;



    if (count <LIMIT_SIZE){
//      listPromise.
//      vertx.executeBlocking()
    }

    super.start(startPromise);
  }


  public  void    process(final List<JsonObject> data,List<Future<List<JsonObject>>> futureList){
    if (data.size()< LIMIT_SIZE){
      Future<List<JsonObject>> listFuture  = processData(data);
      futureList.add(listFuture);
    }else {
      List<List<JsonObject>> splitLists = ListUtil.split(data, LIMIT_SIZE);
      splitLists.forEach(t-> process(t,futureList));
    }
  }
  public Future<List<JsonObject>>  processData(List<JsonObject> data){
    Promise<List<JsonObject>>  listPromise = Promise.promise();
    vertx.executeBlocking(promise -> {
      try {
        Thread.sleep(data.size());
        System.out.println("Run at "+Thread.currentThread().getName());
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
      promise.complete();
    },false,listPromise);
    return listPromise.future();
  }


}
