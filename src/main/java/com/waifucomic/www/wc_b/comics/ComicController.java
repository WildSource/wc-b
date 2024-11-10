package com.waifucomic.www.wc_b.comics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/comics")
@CrossOrigin(origins = {"http://127.0.0.1:4200", "http://localhost:4200", "https://waifuwebcomics.com/"})
public class ComicController {
    private static final Logger logger = LoggerFactory.getLogger(ComicController.class);
    private final ComicService service;

    @Autowired
    public ComicController(ComicService service) {
        this.service = service;
    }

    @GetMapping("/get/all")
    public List<Comic> getComics() {
        return this.service.getComics();
    }

    @GetMapping("/get/{id}")
    public Comic getComic(@PathVariable Long id) {
        return this.service.getComicById(id);
    }

    @GetMapping("/search/{pattern}")
    public List<Comic> searchComics(@PathVariable String pattern) {
        return this.service.searchContainsByTitle(pattern);
    }

    @PostMapping("/post")
    public void postComic(
            @RequestParam("title") String title,
            @RequestParam("cover") MultipartFile cover,
            @RequestParam("pages") List<MultipartFile> pages
    ) {
        this.service.createComic(title, cover, pages);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteComic(@PathVariable Long id) {
        this.service.deleteComic(id);
    }

    @PutMapping("/modify/{id}")
    public void modifyComic(
            @PathVariable Long id,
            @RequestParam("title") String title,
            @RequestParam("cover") MultipartFile cover,
            @RequestParam("pages") List<MultipartFile> pages
    ) {
        this.service.modifyComic(id, title, cover, pages);
    }
}
