package edu.cnm.deepdive.powdr;

import org.springframework.boot.builder.SpringApplicationBuilder;

public class ServiceLauncher {

  public static void main(String[] args) {

    new SpringApplicationBuilder()
        .sources(PowdrApplication.class)
        .profiles("service")
        .run(args);
  }
}
