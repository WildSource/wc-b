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
@CrossOrigin(origins = {"http://127.0.0.1:4200", "http://localhost:4200"})
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
            @RequestPart(value = "title") String title,
            @RequestPart(value = "cover") MultipartFile cover,
            @RequestPart(value = "pages") List<MultipartFile> pages
    ) {
        //this.service.createComic(title, cover, pages);
        logger.info(Arrays.toString(new List[]{pages}));
    }
}
