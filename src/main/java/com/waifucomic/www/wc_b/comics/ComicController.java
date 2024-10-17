package com.waifucomic.www.wc_b.comics;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/comics")
public class ComicController {
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
}
