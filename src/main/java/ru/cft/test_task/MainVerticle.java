package ru.cft.test_task;

import io.vertx.core.AbstractVerticle;

public class MainVerticle extends AbstractVerticle {
  @Override
  public void start() {
    vertx.deployVerticle(new RestApiVerticle());
    vertx.deployVerticle(new DownloadVerticle());
    vertx.deployVerticle(new SaveFileVerticle());
  }
}
