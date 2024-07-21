package com.krispanko.moviematch.controller;

import com.krispanko.moviematch.service.WatchedMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    private final WatchedMovieService watchedMovieService;

    @Autowired
    public PageController(WatchedMovieService watchedMovieService) {
        this.watchedMovieService = watchedMovieService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }


    @GetMapping("/recommendations")
    public String recommendations() {
        return "recommendations";
    }

    @GetMapping("/watchlist")
    public String watchlist() {
        return "watchlist";
    }
}
