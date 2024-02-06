package com.bookstore.readme.domain.file.service;

import com.bookstore.readme.domain.file.exception.NotEqualFileExt;
import com.bookstore.readme.domain.file.model.FileType;
import com.bookstore.readme.domain.file.model.Files;
import com.bookstore.readme.domain.file.repository.FileRepository;
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

    private final String rootPath = System.getProperty("user.dir");

    private final FileRepository fileRepository;

    @Transactional(rollbackFor = Exception.class)
    public Long saveProfileImage(MultipartFile file) {
        if (!file.getContentType().startsWith("image")) {
            throw new NotEqualFileExt(file.getOriginalFilename());
        }

        String originName = file.getOriginalFilename();
        String ext = extractExt(originName);
        String saveName = createFileName(originName) + "." + ext;

        Files files = Files.builder()
                .fileOriginName(originName)
                .fileSaveName(saveName)
                .ext(ext)
                .fileType(FileType.PROFILE)
                .size(file.getSize())
                .build();

        Files saved = fileRepository.save(files);

        return saved.getId();
    }

    @Transactional(rollbackFor = Exception.class)
    public void existFileId(Long fileId) {
        Files file = fileRepository.findById(fileId).orElse(null);

        if(null != file)
            fileRepository.deleteById(file.getId());

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
