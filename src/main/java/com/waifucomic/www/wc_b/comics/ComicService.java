package com.waifucomic.www.wc_b.comics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
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

            handleCover(id, file, data);
            handlePages(id, pages, data);

            logger.info("Comic Creation Success !");
            logger.info(this.repository.findById(id).toString());
        });
    }

    public void deleteComic(Long id) {
        this.repository.deleteById(id);
        File file = new File("img/" + id);
        boolean isDeleted = FileSystemUtils.deleteRecursively(file);
        logger.info("is comic with id: {} deleted ? {}", id, isDeleted);
    }

    public void modifyComic(
            Long id,
            String title,
            MultipartFile cover,
            List<MultipartFile> pages
    ) {
        Optional<Comic> comic = this.repository.findById(id);
        comic.ifPresent((data) -> {
            String coverPath = generateCoverUrl(id, cover);
            List<String> pageUrl = generatePageUrl(id, pages);
            List<String> pagePaths = generateFilePaths(id, pages);

            data.setTitle(title);
            data.setPath("http://localhost:8080/" + coverPath);
            data.setPaths(pageUrl);
            this.repository.save(data);

            File file = new File("img/" + id);
            FileSystemUtils.deleteRecursively(file);

            handleCover(id, cover, data);
            for (int i = 0 ; i < pages.size() ; i++) {
                uploadFile(pages.get(i), new File(pagePaths.get(i)));
            }
            logger.info(this.repository.findById(id).toString());
        });
    }

    private String generateCoverUrl(Long id, MultipartFile file) {
        return "img/" + id + "/cover/cover." + findFileExtension(file);
    }

    private List<String> generatePageUrl(Long id, List<MultipartFile> pages) {
        List<String> urlPaths = new ArrayList<>();

        for (int i = 0; i < pages.size(); i++) {
            MultipartFile mFile = pages.get(i);
            String fmt = "img/" + id + "/page" + (i + 1) + "." + findFileExtension(mFile);
            urlPaths.add("http://localhost:8080/" + fmt);
        }

        return urlPaths;
    }

    private List<String> generateFilePaths(Long id, List<MultipartFile> pages) {
        List<String> paths = new ArrayList<>();
        for (int i = 0; i < pages.size(); i++) {
            MultipartFile mFile = pages.get(i);
            String fmt = "img/" + id + "/page" + (i + 1) + "." + findFileExtension(mFile);
            paths.add(fmt);
        }
        return paths;
    }

    private void handleCover(Long id, MultipartFile file, Comic data) {
        String coverPath = generateCoverUrl(id, file);
        data.setPath("http://localhost:8080/" + coverPath);
        File fileCover = new File(coverPath);
        uploadFile(file, fileCover);
    }

    private void handlePages(Long id, List<MultipartFile> pages, Comic data) {
        data.setPaths(generatePageUrl(id, pages));
        this.repository.save(data);

        List<String> paths = generateFilePaths(id, pages);

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
