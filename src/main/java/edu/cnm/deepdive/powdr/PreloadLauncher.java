package edu.cnm.deepdive.powdr;

import org.springframework.boot.builder.SpringApplicationBuilder;

public class PreloadLauncher {

  public static void main(String[] args) {

    new SpringApplicationBuilder()
        .sources(PowdrApplication.class)
        .profiles("preload")
        .run(args);
  }
}
