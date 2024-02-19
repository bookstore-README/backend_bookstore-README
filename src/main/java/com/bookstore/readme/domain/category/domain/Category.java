package com.bookstore.readme.domain.category.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {
    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    @Column(name = "main_id")
    private Long mainId;

    @Column(name = "sub_id")
    private Long subId;

    private String mainName;
    private String subName;
    private String link;

    @OneToMany(mappedBy = "category")
    private Set<PreferredCategory> categories;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createDate;

    @LastModifiedDate
    @Column(updatable = true)
    private LocalDateTime updateDate;

    @Builder
    public Category(Long mainId, Long subId, String mainName, String subName, String link) {
        this.mainId = mainId;
        this.subId = subId;
        this.mainName = mainName;
        this.subName = subName;
        this.link = link;
    }
}
