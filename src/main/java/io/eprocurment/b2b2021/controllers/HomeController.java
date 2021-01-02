package io.eprocurment.b2b2021.controllers;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

import static org.springdoc.core.Constants.SWAGGER_UI_PATH;
import static org.springframework.util.AntPathMatcher.DEFAULT_PATH_SEPARATOR;
import static org.springframework.web.servlet.view.UrlBasedViewResolver.REDIRECT_URL_PREFIX;

@RestController
public class HomeController {

    @Value(SWAGGER_UI_PATH)
    private String swaggerUiPath;

    @GetMapping("/home")
    public String helloWorld() {
        return "Hello World";
    }

    @GetMapping("/protected/")
    public String helloWorldProtected(Principal principal) {
        return "Hello VIP " + principal.getName();
    }

    @GetMapping(DEFAULT_PATH_SEPARATOR)
    public String index() {
        return REDIRECT_URL_PREFIX + swaggerUiPath;
    }


}
