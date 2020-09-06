package com.ijkalra.secureapis.resource;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/main")
public class MainController {

    @GetMapping
    public String getMainWelcomePage() {
        return "<h1>Welcome to Main page<h1>";
    }

    @GetMapping("/user")
    public String getUserWelcomePage() {
        return "<h1>Welcome to User Home page<h1>";
    }

    @GetMapping("/admin")
    public String getAdminWelcomePage() {
        return "<h1>Welcome to Admin Home page<h1>";
    }
}
