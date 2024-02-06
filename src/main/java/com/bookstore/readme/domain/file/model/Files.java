package com.bookstore.readme.domain.file.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Files {

    @Id
    @GeneratedValue
    @Column(name = "fileId")
    private Long id;

    private String fileOriginName;

    private String fileSaveName;

    private Long size;

    private String ext;

    @Enumerated(EnumType.STRING)
    private FileType fileType;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createDate;

    @LastModifiedDate
    @Column(updatable = true)
    private LocalDateTime updateDate;

    @Builder
    public Files(String fileOriginName, String fileSaveName, Long size, String ext, FileType fileType) {
        this.fileOriginName = fileOriginName;
        this.fileSaveName = fileSaveName;
        this.size = size;
        this.ext = ext;
        this.fileType = fileType;
    }

}
