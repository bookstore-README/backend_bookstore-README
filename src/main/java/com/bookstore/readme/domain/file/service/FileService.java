package com.bookstore.readme.domain.file.service;

import com.bookstore.readme.domain.file.exception.NotEqualFileExt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class FileService {

    @Value("${upload.path}")
    private String filePath;

    @Transactional(rollbackFor = Exception.class)
    public String saveProfileImage(MultipartFile file) {
        if (!file.getContentType().startsWith("image"))
            throw new NotEqualFileExt(file.getOriginalFilename());

        String originName = file.getOriginalFilename();
        String ext = extractExt(originName);
        String saveName = createFileName(originName) + "." + ext;

        Path savePath = Paths.get(filePath + saveName);

        try {
            file.transferTo(savePath);
        } catch(IOException e) {
            e.printStackTrace();
        }

        return filePath + saveName;
    }

    private String createFileName(String originFileName) {
        StringBuilder sb = new StringBuilder();
        LocalDateTime date = LocalDateTime.now();
        String formatted = date.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
        sb.append(formatted);

        return sb.toString();
    }

    private String extractExt(String originalFileName) {
        int pos = originalFileName.lastIndexOf(".");
        return originalFileName.substring(pos + 1);
    }

}
