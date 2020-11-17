package edu.cnm.deepdive.powdr.controller;

import edu.cnm.deepdive.powdr.model.entity.Trip;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trips")
@ExposesResourceFor(Trip.class)
public class TripController {



}
