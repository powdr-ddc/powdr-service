package edu.cnm.deepdive.powdr.service;

import edu.cnm.deepdive.powdr.model.dao.UserRepository;
import edu.cnm.deepdive.powdr.model.entity.SkiResort;
import edu.cnm.deepdive.powdr.model.entity.User;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
public class UserService implements Converter<Jwt, UsernamePasswordAuthenticationToken> {

  private final UserRepository userRepository;

  @Autowired
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User getOrCreate(String oauthKey, String username) {
    return userRepository.findFirstByOauthKey(oauthKey)
        .orElseGet(() -> {
          User user = new User();
          user.setOauthKey(oauthKey);
          user.setName(username);
          return userRepository.save(user);
        }); //takes no parameters but returns something
  }

  public List<User> getAllByOrderByName() {
    return userRepository.getAllByOrderByName();
  }

  public Optional<User> getById(UUID id) {
    return userRepository.findById(id);
  }

  @Override
  public UsernamePasswordAuthenticationToken convert(Jwt jwt) { // json web token from google
    Collection<SimpleGrantedAuthority> grants =
        Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
    return new UsernamePasswordAuthenticationToken(
        getOrCreate(jwt.getSubject(), jwt.getClaimAsString("name")),
        jwt.getTokenValue(),
        grants
    );
  }
}
