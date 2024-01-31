package com.bookstore.readme.domain.notice.service;

import com.bookstore.readme.domain.notice.domain.Notice;
import com.bookstore.readme.domain.notice.repository.NoticeRepository;
import com.bookstore.readme.domain.notice.request.NoticeSaveRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeRepository noticeRepository;

    public Long save(NoticeSaveRequest request) {
        Notice notice = request.toNotice();
        noticeRepository.save(notice);
        return notice.getId();
    }
}
