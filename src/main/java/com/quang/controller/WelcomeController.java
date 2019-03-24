package com.quang.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Base controller to return to index.html.
 *
 * @author Vu Ngoc Quang
 */
@Controller
public class WelcomeController {

    @GetMapping(value="/")
    public String homepage(){
        return "index";
    }
}