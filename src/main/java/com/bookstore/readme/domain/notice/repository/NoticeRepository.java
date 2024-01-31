package com.bookstore.readme.domain.notice.repository;

import com.bookstore.readme.domain.notice.domain.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
}
