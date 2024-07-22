package com.krispanko.moviematch.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for serving static resources.
 */
@RestController
public class StaticResourceController {

    /**
     * Serves the JavaScript file from the classpath.
     *
     * @return the JavaScript resource
     */
    @GetMapping("/script.js")
    public Resource getScript() {
        return new ClassPathResource("static/script.js");
    }
}
