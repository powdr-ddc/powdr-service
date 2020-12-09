package edu.cnm.deepdive.powdr.service;

import edu.cnm.deepdive.powdr.model.dao.SkiResortRepository;
import edu.cnm.deepdive.powdr.model.entity.SkiResort;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

/**
 * Class to preload data into the database.
 */
@Component
@Profile("preload")
public class Preloader implements CommandLineRunner {

  private static final String PRELOAD_DATA = "preload/resorts.csv";

  private final SkiResortRepository repository;

  /**
   * Constructs an instance of Preloader.
   * @param repository A {@link SkiResortRepository} instance
   */
  public Preloader(SkiResortRepository repository) {
    this.repository = repository;
  }

  @Override
  public void run(String... args) throws Exception {
    ClassPathResource resource = new ClassPathResource(PRELOAD_DATA);
    try (
        InputStream input = resource.getInputStream();
        Reader reader = new InputStreamReader(input);
        CSVParser parser = CSVParser.parse(reader, CSVFormat.DEFAULT
            .withFirstRecordAsHeader()
            .withIgnoreEmptyLines()
            .withIgnoreSurroundingSpaces());
    ) {
      List<SkiResort> resorts = new LinkedList<>();
      for (CSVRecord record : parser) {
        SkiResort skiResort = new SkiResort();
        skiResort.setName(record.get("name"));
        skiResort.setLatitude(Double.parseDouble(record.get("latitude")));
        skiResort.setLongitude(Double.parseDouble(record.get("longitude")));
        resorts.add(skiResort);
      }
      repository.saveAll(resorts);
    }
  }
}
