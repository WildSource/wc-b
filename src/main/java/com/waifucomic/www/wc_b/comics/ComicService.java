package com.waifucomic.www.wc_b.comics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ComicService {
    private static final Logger logger = LoggerFactory.getLogger(ComicService.class);
    private final ComicRepository repository;

    @Autowired
    public ComicService(ComicRepository repository) {
        this.repository = repository;
    }

    public void createComic(String title, MultipartFile file, List<MultipartFile> pages) {
        this.repository.save(new Comic(title));

        this.repository.findByTitle(title).ifPresent((data) -> {
            Long id = data.getId();

            data.setPath("http://localhost:8080/img/" + id + "/cover/cover." + findFileExtension(file));

            this.repository.save(data);

            uploadFile(file, id);
            logger.info("Comic Creation Success !");
        });
    }

    public List<Comic> searchContainsByTitle(String pattern) {
        return this.repository.findByTitleContaining(pattern).get();
    }

    public void uploadFile(MultipartFile mFile, Long id) {
        File file = new File("img/" + id + "/cover/cover." + findFileExtension(mFile));
        file.getParentFile().mkdirs();

        try (OutputStream os = new FileOutputStream(file)){
            os.write(mFile.getBytes());
        } catch (FileNotFoundException e) {
            logger.error("Could not find file: {}", file);
        } catch (IOException e) {
            logger.error("IO error, cause unknown");
        }
    }

    private String findFileExtension(MultipartFile file) {
        String fileExtension = null;

        String contentType = file.getContentType();

        if (contentType != null) {
            if (contentType.contains("png")) {
                fileExtension = "png";
            } else if (contentType.contains("jpeg") || contentType.contains("jpg")) {
                fileExtension = "jpg";
            }
        }

        return fileExtension;
    }

    public List<Comic> getComics() {
        List<Comic> comics = new ArrayList<>();
        this.repository.findAll().forEach(comics::add);
        return comics;
    }

    public Comic getComicById(Long id) {
        return this.repository.findById(id).orElse(null);
    }
}
