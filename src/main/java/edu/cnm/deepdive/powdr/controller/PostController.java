package edu.cnm.deepdive.powdr.controller;

import edu.cnm.deepdive.powdr.model.entity.Post;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts")
@ExposesResourceFor(Post.class)
public class PostController {

}
