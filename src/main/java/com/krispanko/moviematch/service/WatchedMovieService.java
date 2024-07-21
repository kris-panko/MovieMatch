package com.krispanko.moviematch.service;

import com.krispanko.moviematch.model.User;
import com.krispanko.moviematch.model.WatchedMovie;
import com.krispanko.moviematch.repository.WatchedMovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WatchedMovieService {

    private final WatchedMovieRepository watchedMovieRepository;

    @Autowired
    public WatchedMovieService(WatchedMovieRepository watchedMovieRepository) {
        this.watchedMovieRepository = watchedMovieRepository;
    }

    public Optional<WatchedMovie> findByUserAndMovieId(User user, Long movieId) {
        return watchedMovieRepository.findByUserAndMovieId(user, movieId);
    }

    public WatchedMovie saveWatchedMovie(WatchedMovie watchedMovie) {
        return watchedMovieRepository.save(watchedMovie);
    }
}
