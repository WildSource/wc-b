package com.waifucomic.www.wc_b.comics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
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

    public void createComic(String title, MultipartFile file, List<MultipartFile> pages) {
        this.repository.save(new Comic(title));

        this.repository.findByTitle(title).ifPresent((data) -> {
            Long id = data.getId();

            handleCover(id, file, data);
            handlePages(id, pages, data);

            logger.info("Comic Creation Success !");
        });
    }

    public void deleteComic(Long id) {
        this.repository.deleteById(id);
        File file = new File("img/" + id);
        boolean isDeleted = FileSystemUtils.deleteRecursively(file);
        logger.info("is comic with id: {} deleted ? {}", id, isDeleted);
    }

    private void handleCover(Long id, MultipartFile file, Comic data) {
        String coverPath = "img/" + id + "/cover/cover." + findFileExtension(file);
        data.setPath("http://localhost:8080/" + coverPath);
        File fileCover = new File(coverPath);
        uploadFile(file, fileCover);
    }

    private void handlePages(Long id, List<MultipartFile> pages, Comic data) {
        List<String> urlPaths = new ArrayList<>();
        List<String> paths = new ArrayList<>();

        for (int i = 0; i < pages.size(); i++) {
            MultipartFile mFile = pages.get(i);
            String fmt = "img/" + id + "/page" + (i + 1) + "." + findFileExtension(mFile);
            urlPaths.add("http://localhost:8080/" + fmt);
            paths.add(fmt);
        }
        data.setPaths(urlPaths);
        this.repository.save(data);

        for (int j = 0 ; pages.size() > j; j++) {
            File file = new File(paths.get(j));
            uploadFile(pages.get(j), file);
        }
    }

    public void uploadFile(MultipartFile content, File file) {
        file.getParentFile().mkdirs();

        try (OutputStream os = new FileOutputStream(file)){
            os.write(content.getBytes());
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

    public List<Comic> searchContainsByTitle(String pattern) {
        return this.repository.findByTitleContaining(pattern).get();
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
