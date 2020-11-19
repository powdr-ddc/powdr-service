package edu.cnm.deepdive.powdr.service;

import edu.cnm.deepdive.powdr.model.dao.SkiResortRepository;
import edu.cnm.deepdive.powdr.model.entity.FavoriteSkiResort;
import edu.cnm.deepdive.powdr.model.entity.SkiResort;
import edu.cnm.deepdive.powdr.model.entity.User;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * A Service class for the {@link SkiResort} entity that participates in dependency injection,
 * and performs more detailed methods for obtaining information for the
 * {@link edu.cnm.deepdive.powdr.controller.SkiResortController} and {@link SkiResortRepository}.
 */
@Service
public class SkiResortService {

  private final SkiResortRepository skiResortRepository;

  /**
   * Constructs an instance of {@link SkiResortRepository}.
   * @param skiResortRepository An instance of {@link SkiResortRepository}.
   */
  @Autowired
  public SkiResortService(SkiResortRepository skiResortRepository) {
    this.skiResortRepository = skiResortRepository;
  }

  /**
   * Saves a {@link SkiResort} to a {@link User} as a favorite.
   * @param skiResort Ski resort to be saved into favorites.
   * @param user User to save the ski resort.
   * @return A ski resort as a favorite ski resort.
   */
  public SkiResort save(SkiResort skiResort, User user) {
    for (FavoriteSkiResort favorite : skiResort.getFavoriteSkiResorts()) {
      favorite.setSkiResort(skiResort);
      if (favorite.getUser() == null) {
        favorite.setUser(user);
      }
    }
    return skiResortRepository.save(skiResort);
  }

  /**
   * Gets all {@link SkiResort} ordered by name.
   * @return A list of ski resort.
   */
  public List<SkiResort> getAll() {
    return skiResortRepository.getAllByOrderByName();
  }

  /**
   * Gets a {@link SkiResort} according to a specified ID.
   * @param id ID of ski resort
   * @return A ski resort
   */
  public Optional<SkiResort> get(UUID id) {
    return skiResortRepository.findById(id);
  }

  /**
   * Gets a {@link SkiResort} according to it's latitude and longitude.
   * @param latitude Latitude of ski resort.
   * @param longitude Longitude of ski resort.
   * @return A ski resort
   */
  public SkiResort getByLatitudeAndLongitude(double latitude, double longitude) {
    return skiResortRepository.getByLatitudeAndLongitude(latitude, longitude);
  }

  /**
   * Gets a {@link SkiResort} according to it's name.
   * @param name Name of the ski resort
   * @return A ski resort if available.
   */
  public Optional<SkiResort> getByName(String name) {
    return skiResortRepository.getByName(name);
  }

  /**
   * Searches through a List of {@link SkiResort} by name.
   * @param fragment String fragment for search
   * @return A list of {@link SkiResort}
   */
  public List<SkiResort> searchByName(String fragment) {
    return skiResortRepository.findAllByNameContainsOrderByNameAsc(fragment);
  }

  /**
   * Deletes a ski resort from the database.
   * @param skiResort {@link SkiResort} to be deleted.
   */
  public void delete(SkiResort skiResort) {
    skiResortRepository.delete(skiResort);
  }

  /**
   * Checks a {@link User} favorite ski resorts to see if a {@link SkiResort} is already in the
   * user's favorites. If the ski resort exists, removes it from the list of favorites; otherwise,
   * a {@link FavoriteSkiResort} is created and set to the user.
   * @param skiResort Ski resort to set in favorites.
   * @param favorite Return true if the ski resort is already a part of the favorites.
   * @param user User to be checked for favorites.
   */
  public void setFavorite(SkiResort skiResort, boolean favorite, User user) {
    boolean found = false;
    List<FavoriteSkiResort> favoriteSkiResorts = skiResort.getFavoriteSkiResorts();
    for (
        Iterator<FavoriteSkiResort> iter = favoriteSkiResorts.iterator();
        iter.hasNext();
        // Updater in body.
    ) {
      FavoriteSkiResort favoriteSkiResort = iter.next();
      if (favoriteSkiResort.getSkiResort().getSkiResortId().equals(skiResort.getSkiResortId())) {
        found = true;
        if (!favorite) { // Need to remove it from list of favorites.
//          favoriteSkiResort.setSkiResort(null);
          iter.remove();
        }
        break;
      }
    }
    if (favorite && !found) { // Need to add to list of favorites if not found.
      FavoriteSkiResort favoriteSkiResort = new FavoriteSkiResort();
      favoriteSkiResort.setSkiResort(skiResort);
      favoriteSkiResort.setUser(user);
      favoriteSkiResorts.add(favoriteSkiResort);
    }
    skiResortRepository.save(skiResort);
  }

}
