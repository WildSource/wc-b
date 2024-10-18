package com.waifucomic.www.wc_b.comics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/comics")
public class ComicController {
    private static final Logger logger = LoggerFactory.getLogger(ComicController.class);
    private ComicService service;

    @Autowired
    public ComicController(ComicService service) {
        this.service = service;
    }

    @CrossOrigin(origins = {"http://127.0.0.1:4200", "http://localhost:4200"})
    @GetMapping("/get/all")
    public List<Comic> getComics() {
        Comic comic1 = new Comic("Gym Fever");
        comic1.setId(1L);
        Comic comic2 = new Comic("Mommy Dommy");
        comic2.setId(2L);
        Comic comic3 = new Comic("Cutie Pie");
        comic3.setId(3L);
        Comic comic4 = new Comic("Elio");
        comic4.setId(4L);
        Comic comic5 = new Comic("Fallen Of Albaz");
        comic5.setId(5L);
        Comic comic6 = new Comic("Dogmatika Ecclesia");
        comic6.setId(6L);

        List<Comic> comics = new ArrayList<>();
        comics.add(comic1);
        comics.add(comic2);
        comics.add(comic3);
        comics.add(comic4);
        comics.add(comic5);
        comics.add(comic6);

        return comics;
    }

    @CrossOrigin(origins = {"http://127.0.0.1:4200", "http://localhost:4200"})
    @PostMapping("/post")
    public void postComic(@RequestParam String title, @RequestParam MultipartFile cover) {
        logger.info("title: {}", title);
        try {
            this.service.uploadFile(cover.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
