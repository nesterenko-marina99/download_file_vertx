package ru.cft.test_task;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SaveFileVerticle extends AbstractVerticle {
  @Override
  public void start() {
    vertx.eventBus().consumer("save.file", message -> {
      JsonObject config = config();
      String filePath = config.getString("file.path");

      if (message.body() instanceof byte[]) {
        byte[] fileData = (byte[]) message.body();
        try {
          Path savePath = Paths.get(filePath).toAbsolutePath();
          Files.write(savePath, fileData);
        } catch (IOException e) {
          throw new UncheckedIOException("Failed to save file: " + e.getMessage(), e);
        }
      } else {
        throw new IllegalArgumentException("Invalid file data format.");
      }
    });
  }
}
