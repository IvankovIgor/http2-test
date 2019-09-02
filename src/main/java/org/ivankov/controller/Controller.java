package org.ivankov.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @PostMapping("/bla")
    public String bla() {
        return "bla";
    }
}
