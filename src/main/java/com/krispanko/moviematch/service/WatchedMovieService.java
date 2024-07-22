package com.krispanko.moviematch.service;

import com.krispanko.moviematch.model.User;
import com.krispanko.moviematch.model.WatchedMovie;
import com.krispanko.moviematch.repository.WatchedMovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service class for managing watched movies.
 */
@Service
public class WatchedMovieService {

    private final WatchedMovieRepository watchedMovieRepository;

    /**
     * Constructs the WatchedMovieService with the specified watchedMovieRepository.
     *
     * @param watchedMovieRepository the watched movie repository
     */
    @Autowired
    public WatchedMovieService(WatchedMovieRepository watchedMovieRepository) {
        this.watchedMovieRepository = watchedMovieRepository;
    }

    /**
     * Finds a watched movie by user and movie ID.
     *
     * @param user the user who watched the movie
     * @param movieId the ID of the movie
     * @return an optional containing the found watched movie or empty if not found
     */
    public Optional<WatchedMovie> findByUserAndMovieId(User user, Long movieId) {
        return watchedMovieRepository.findByUserAndMovieId(user, movieId);
    }

    /**
     * Saves a watched movie.
     *
     * @param watchedMovie the watched movie to be saved
     */
    public void saveWatchedMovie(WatchedMovie watchedMovie) {
        watchedMovieRepository.save(watchedMovie);
    }
}
