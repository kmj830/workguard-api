package com.workguard.controller;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Hidden
@Controller
public class SwaggerRedirectController {

    @GetMapping("/api")
    public String redirectToSwagger() {
        return "redirect:/swagger-ui/index.html";
    }

    @GetMapping("/docs")
    public String redirectToDocs() {
        return "redirect:/swagger-ui/index.html";
    }
}
