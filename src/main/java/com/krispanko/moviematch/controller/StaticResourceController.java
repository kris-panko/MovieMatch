// StaticResourceController.java
package com.krispanko.moviematch.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StaticResourceController {

    @GetMapping("/script.js")
    public Resource getScript() {
        return new ClassPathResource("static/script.js");
    }
}
