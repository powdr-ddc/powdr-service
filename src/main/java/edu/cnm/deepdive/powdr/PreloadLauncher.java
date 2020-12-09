package edu.cnm.deepdive.powdr;

import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * Configuration class to preload data into the database. Should only be used once.
 */
public class PreloadLauncher {

  public static void main(String[] args) {

    new SpringApplicationBuilder()
        .sources(PowdrApplication.class)
        .profiles("preload")
        .run(args);
  }
}
