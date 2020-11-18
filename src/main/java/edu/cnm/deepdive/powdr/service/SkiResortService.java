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

@Service
public class SkiResortService {

  private final SkiResortRepository skiResortRepository;

  @Autowired
  public SkiResortService(SkiResortRepository skiResortRepository) {
    this.skiResortRepository = skiResortRepository;
  }

  public SkiResort save(SkiResort skiResort, User user) {
    for (FavoriteSkiResort favorite : skiResort.getFavoriteSkiResorts()) {
      favorite.setSkiResort(skiResort);
      if (favorite.getUser() == null) {
        favorite.setUser(user);
      }
    }
    return skiResortRepository.save(skiResort);
  }

  public List<SkiResort> getAll() {
    return skiResortRepository.getAllByOrderByName();
  }

  public Optional<SkiResort> get(UUID id) {
    return skiResortRepository.findById(id);
  }

  public void delete(SkiResort skiResort) {
    skiResortRepository.delete(skiResort);
  }

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
