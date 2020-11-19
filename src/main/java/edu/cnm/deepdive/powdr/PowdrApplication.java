package edu.cnm.deepdive.powdr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;

/**
 * The main class for the Powdr webservice that allows the server to run through Spring.
 *
 * @author Adrian Anaya
 * @author Jonah Rodriguez
 * @author Austin West
 */
@EnableHypermediaSupport(type = {HypermediaType.HAL})
@SpringBootApplication
public class PowdrApplication {

  /**
   * Main class though which Powdr Service runs
   * @param args System Arguments
   */
  public static void main(String[] args) {
    SpringApplication.run(PowdrApplication.class, args);
  }

}
