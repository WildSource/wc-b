package com.waifucomic.www.wc_b.comics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class ComicService {
    private static final Logger logger = LoggerFactory.getLogger(ComicService.class);

    public void uploadFile(byte[] pFile) {
        File file = new File("src/main/resources/static/demo.jpg");

        try (OutputStream os = new FileOutputStream(file)){
            os.write(pFile);
        } catch (FileNotFoundException e) {
            logger.error("Could not find file: {}", file);
        } catch (IOException e) {
            logger.error("IO error, cause unknown");
        }
    }
}
