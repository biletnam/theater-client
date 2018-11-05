package io.ricobot.theater.client.app.controller;

import io.ricobot.theater.client.app.model.Genre;
import io.ricobot.theater.client.app.model.Movie;
import io.ricobot.theater.client.core.helper.Helper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;


@Controller
public class AdminController {

    private RestTemplate restTemplate = new RestTemplate();
    private final String endpoint = "http://localhost:8081/api";

    @GetMapping("/admin")
    public String adminPage(Model model) {
        model.addAttribute("movies", restTemplate.getForEntity(endpoint + "/movies", Object[].class).getBody());
        model.addAttribute("cinemas", restTemplate.getForEntity(endpoint + "/cinemas", Object[].class).getBody());
        return "/admin/index";
    }

    @GetMapping("/admin/cinema")
    public String cinemas(Model model) {
        model.addAttribute("cinemas", restTemplate.getForEntity(endpoint + "/cinemas", Object[].class).getBody());
        return "/admin/cinema";
    }

    @GetMapping("/admin/genre")
    public String genres(Model model) {
        model.addAttribute("genres", restTemplate.getForEntity(endpoint + "/genres", Object[].class).getBody());
        model.addAttribute("cinemas", restTemplate.getForEntity(endpoint + "/cinemas", Object[].class).getBody());
        return "/admin/genre";
    }

    @GetMapping("/admin/movie/")
    public String movies(Model model) {
        model.addAttribute("section", "create");
        model.addAttribute("genres", restTemplate.getForEntity(endpoint + "/genres", Object[].class).getBody());
        model.addAttribute("cinemas", restTemplate.getForEntity(endpoint + "/cinemas", Object[].class).getBody());
        model.addAttribute("languages", restTemplate.getForEntity(endpoint + "/languages", Object[].class).getBody());
        model.addAttribute("movie", new Movie());
        return "/admin/add-movie";
    }

    @GetMapping("/admin/movie/{id}")
    public String movies(@PathVariable Long id, Model model) {
        model.addAttribute("section", "update");
        model.addAttribute("genres", restTemplate.getForEntity(endpoint + "/genres", Object[].class).getBody());
        model.addAttribute("cinemas", restTemplate.getForEntity(endpoint + "/cinemas", Object[].class).getBody());
        model.addAttribute("languages", restTemplate.getForEntity(endpoint + "/languages", Object[].class).getBody());
        model.addAttribute("movie", restTemplate.getForEntity(endpoint + "/movies/" + id, Movie.class).getBody());
        return "/admin/add-movie";
    }

    @GetMapping("/admin/movie/delete/{id}")
    public String deleteMovie(@PathVariable Long id) {
        restTemplate.delete(endpoint + "/movies/" + id);
        return "redirect:/admin";
    }

    @PostMapping("/admin/addLanguage")
    public String addLanguage(@RequestParam Map<String, Object> language) {
        restTemplate.postForLocation(endpoint + "/languages", language);
        return "redirect:/admin";
    }

    @PostMapping("/admin/addCinema")
    public String addCinema(@RequestParam Map<String, Object> cinema) {
        restTemplate.postForLocation(endpoint + "/cinemas", cinema);
        return "redirect:/admin";
    }

    @PostMapping("/admin/create-movie")
    public String addMovie(@RequestParam Map<String, Object> params, @RequestParam("cinemas") List<String> param1) {
        restTemplate.postForLocation(endpoint + "/movies", Helper.mapper(params, param1));
        return "redirect:/admin";
    }

    @PostMapping("/admin/update-movie/{id}")
    public String updateMovie(@PathVariable Long id, @RequestParam Map<String, Object> params, @RequestParam("cinemas") List<String> param1) {
        params.put("id", id);
        restTemplate.put(endpoint + "/movies/" + id, Helper.mapper(params, param1));
        return "redirect:/admin";
    }

    @PostMapping("/admin/addGenre")
    public String addGenre(@RequestParam Map<String, Object> name) {
        restTemplate.postForLocation(endpoint + "/genres", new Genre(name.get("name").toString()));
        return "redirect:/admin";
    }
}