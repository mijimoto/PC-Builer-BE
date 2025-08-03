package com.project.PCBuilder.rest.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AppRedirectController {
    
    @GetMapping("/app-redirect")
    public String redirectToApp(@RequestParam String token, Model model) {
        // Validate token here if needed
        model.addAttribute("token", token);
        return "app-redirect";
    }
}