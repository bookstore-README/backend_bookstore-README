package com.bookstore.readme.domain.file.service;

import com.bookstore.readme.domain.file.exception.NotEqualFileExt;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class FileService {

    @Value("${upload.path}")
    private final String filePath;

    @Transactional(rollbackFor = Exception.class)
    public String saveProfileImage(MultipartFile file) {
        if (!file.getContentType().startsWith("image"))
            throw new NotEqualFileExt(file.getOriginalFilename());

        String originName = file.getOriginalFilename();
        String ext = extractExt(originName);
        String saveName = createFileName(originName) + "." + ext;

        return "";
    }

    private String createFileName(String originFileName) {
        StringBuilder sb = new StringBuilder();

        LocalDateTime date = LocalDateTime.now();
        date.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

        sb.append(date);

        return sb.toString();
    }

    private String extractExt(String originalFileName) {
        int pos = originalFileName.lastIndexOf(".");
        return originalFileName.substring(pos + 1);
    }

}
