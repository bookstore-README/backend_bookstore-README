package com.bookstore.readme.domain.community.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Emoji {
    @Id
    @GeneratedValue
    @Column(name = "emoji_id")
    private Long id;

    private Integer heart;
    private Integer smile;
    private Integer excited;
    private Integer angry;
    private Integer crying;

    @Builder
    public Emoji(Integer heart, Integer smile, Integer excited, Integer angry, Integer crying) {
        this.heart = heart;
        this.smile = smile;
        this.excited = excited;
        this.angry = angry;
        this.crying = crying;
    }

    public void add(String type) {
        switch (type) {
            case "heart" -> heart++;
            case "smile" -> smile++;
            case "excited" -> excited++;
            case "angry" -> angry++;
            case "crying" -> crying++;
        }
    }

    public void sub(String type) {
        switch (type) {
            case "heart" -> heart--;
            case "smile" -> smile--;
            case "excited" -> excited--;
            case "angry" -> angry--;
            case "crying" -> crying--;
        }
    }
}
