package com.bookstore.readme.domain.community.repository;

import com.bookstore.readme.domain.community.domain.Emoji;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmojiRepository extends JpaRepository<Emoji, Long> {
}
