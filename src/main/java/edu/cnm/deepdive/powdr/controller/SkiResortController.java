package edu.cnm.deepdive.powdr.controller;

import edu.cnm.deepdive.powdr.model.entity.SkiResort;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ski-resorts")
@ExposesResourceFor(SkiResort.class)
public class SkiResortController {

}
