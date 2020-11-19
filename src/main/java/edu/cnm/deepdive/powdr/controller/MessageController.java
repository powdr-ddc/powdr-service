package edu.cnm.deepdive.powdr.controller;

import edu.cnm.deepdive.powdr.model.entity.Message;
import edu.cnm.deepdive.powdr.model.entity.User;
import edu.cnm.deepdive.powdr.service.MessageService;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller class for {@link Message} to receive parameter values taken from HTTP requests to
 * their respective specified URL endpoints, and returning serialized JSON object results to HTTP
 * responses.
 */
@RestController
@RequestMapping("/messages")
@ExposesResourceFor(Message.class)
public class MessageController {

  private final MessageService messageService;

  /**
   * Constructs an instance of {@link MessageService}.
   *
   * @param messageService Takes an instance of {@link MessageService}.
   */
  @Autowired
  public MessageController(MessageService messageService) {
    this.messageService = messageService;
  }

  /**
   * Gets a message according to it's specified ID, or else throws a {@link NoSuchElementException}
   * if none are found.
   *
   * @param messageId ID of the message in the database
   * @param auth Security Authentication
   * @return A message, or else throw a {@link NoSuchElementException}.
   */
  @GetMapping(value = "/{messageId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Message get(@PathVariable UUID messageId, Authentication auth) {
    return messageService.get(messageId)
        .orElseThrow(NoSuchElementException::new);
  }

  /**
   * Gets all messages specified through the specified sender/receiver.
   *
   * @param sender User who sent the message.
   * @param receiver User who receives the message.
   * @param auth Security Authentication
   * @return A list of messages of the specified sender/receiver.
   */
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Message> getAll(@RequestBody User sender,@RequestBody User receiver, Authentication auth) {
    return messageService.getAllBySenderOrReceiver(sender, receiver);
  }

  /**
   * Posts a message to the server.
   *
   * @param receiver User who first received the message.
   * @param sender User who first sent the message.
   * @param messageId Message ID between the two Users.
   * @return The message between the two users.
   */
  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public Message post(@RequestBody User receiver, @RequestBody User sender, UUID messageId) {
    return messageService.getOrCreate(receiver, sender, messageId);
  }

  /**
   * Deletes a {@link Message} according to the specified ID in the database if one is present.
   *
   * @param messageId ID of the {@link Message}
   * @param auth Security Authentication
   */
  @DeleteMapping(value = "/{messageId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable UUID messageId, Authentication auth) {
    messageService.get(messageId)
        .ifPresent(messageService::delete);
  }

}
