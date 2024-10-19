package com.waifucomic.www.wc_b.comics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class ComicService {
    private static final Logger logger = LoggerFactory.getLogger(ComicService.class);
    private final ComicRepository repository;

    @Autowired
    public ComicService(ComicRepository repository) {
        this.repository = repository;
    }

    public void createComic(String title, MultipartFile file) {
        this.repository.save(new Comic(title, "http://localhost:8080/img/" + file.getOriginalFilename()));

        try {
            uploadFile(file.getBytes(), file.getOriginalFilename());
        } catch (IOException e) {
            logger.error("Unexpected IO error: cause unknown");
        }

        logger.info("Comic Creation Success !");
    }

    public List<Comic> getComics() {
        List<Comic> comics = new ArrayList<>();
        this.repository.findAll().forEach(comics::add);
        return comics;
    }

    public Comic getComicById(Long id) {
        return this.repository.findById(id).orElse(null);
    }

    public void uploadFile(byte[] pFile, String path) {
        File file = new File("src/main/resources/static/" + path);

        try (OutputStream os = new FileOutputStream(file)){
            os.write(pFile);
        } catch (FileNotFoundException e) {
            logger.error("Could not find file: {}", file);
        } catch (IOException e) {
            logger.error("IO error, cause unknown");
        }
    }
}
