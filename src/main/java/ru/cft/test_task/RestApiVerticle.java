package ru.cft.test_task;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;

public class RestApiVerticle extends AbstractVerticle {
  @Override
  public void start() {
    HttpServer server = vertx.createHttpServer();
    Router router = Router.router(vertx);

    router.get("/status").handler(ctx -> {
      ctx.response()
        .putHeader("content-type", "application/json")
        .end("{\"status\": \"running\"}");
    });

    router.post("/download").handler(ctx -> {
      String url = ctx.getBodyAsJson().getString("url");
      vertx.eventBus().send("download.file", url);
      ctx.response().end("Downloading...");
    });

    server.requestHandler(router).listen(8080);
  }
}
