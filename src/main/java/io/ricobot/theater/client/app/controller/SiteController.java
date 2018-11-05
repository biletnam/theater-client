package io.ricobot.theater.client.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.Map;


@Controller
public class SiteController {

    private RestTemplate restTemplate = new RestTemplate();
    private final String endpoint = "http://localhost:8081/api";

    @GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("movies", restTemplate.getForEntity(endpoint + "/movies", Object[].class).getBody());
        model.addAttribute("cinemas", restTemplate.getForEntity(endpoint + "/cinemas", Object[].class).getBody());
        return "/index";
    }

    @GetMapping("/movie/{id}")
    public String findMovie(@PathVariable Long id, Model model) {
        model.addAttribute("movie", restTemplate.getForEntity(endpoint + "/movies/" + id, Object.class).getBody());
        model.addAttribute("cinemas", restTemplate.getForEntity(endpoint + "/cinemas", Object[].class).getBody());
        return "/movie";
    }

    @GetMapping("/cinema/{id}")
    public String findCinema(@PathVariable Long id, Model model) {
        model.addAttribute("cinema", restTemplate.getForEntity(endpoint + "/cinemas/" + id, Object.class).getBody());
        model.addAttribute("cinemas", restTemplate.getForEntity(endpoint + "/cinemas", Object[].class).getBody());
        model.addAttribute("movies", restTemplate.getForEntity(endpoint + "/movies/cinema/" + id, Object[].class).getBody());
        return "/cinema";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("movies", restTemplate.getForEntity(endpoint + "/movies", Object[].class).getBody());
        model.addAttribute("cinemas", restTemplate.getForEntity(endpoint + "/cinemas", Object[].class).getBody());
        return "/index";
    }

    @PostMapping("/movie/comment/{id}")
    public String addMovie(@RequestParam Map<String, Object> params, @PathVariable Long id) {
        params.put("createdAt", new Date());
        restTemplate.postForLocation(endpoint + "/movies/comment/" + id, params);
        return "redirect:/movie/" + id;
    }
}