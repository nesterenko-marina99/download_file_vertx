package ru.cft.test_task;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.buffer.Buffer;
import io.vertx.ext.web.client.WebClient;

public class DownloadVerticle extends AbstractVerticle {

  @Override
  public void start() {
    WebClient webClient = WebClient.create(vertx);

    vertx.eventBus().consumer("download.file", message -> {
      String url = message.body().toString();

      webClient.getAbs(url)
        .send(httpResponse -> {
          if (httpResponse.succeeded()) {
            if (httpResponse.result().statusCode() == 200) {
              Buffer downloadedFile = httpResponse.result().body();
              vertx.eventBus().send("save.file", downloadedFile);
            } else {
              System.out.println("Failed to download: " + httpResponse.result().statusMessage());
            }
          } else {
            System.out.println("Download request failed: " + httpResponse.cause().getMessage());
          }
        });
    });
  }
}
