package com.krispanko.moviematch.controller;

import com.krispanko.moviematch.service.WatchedMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller for handling page navigation.
 */
@Controller
public class PageController {

    private final WatchedMovieService watchedMovieService;

    /**
     * Constructs the PageController with the specified watchedMovieService.
     *
     * @param watchedMovieService the watched movie service
     */
    @Autowired
    public PageController(WatchedMovieService watchedMovieService) {
        this.watchedMovieService = watchedMovieService;
    }

    /**
     * Handles GET requests to the index page.
     *
     * @return the name of the index view
     */
    @GetMapping("/")
    public String index() {
        return "index";
    }

    /**
     * Handles GET requests to the recommendations page.
     *
     * @return the name of the recommendations view
     */
    @GetMapping("/recommendations")
    public String recommendations() {
        return "recommendations";
    }

    /**
     * Handles GET requests to the watchlist page.
     *
     * @return the name of the watchlist view
     */
    @GetMapping("/watchlist")
    public String watchlist() {
        return "watchlist";
    }

    /**
     * Handles GET requests to the details page.
     *
     * @return the name of the details view
     */
    @GetMapping("/details")
    public String details() {
        return "details";
    }
}
