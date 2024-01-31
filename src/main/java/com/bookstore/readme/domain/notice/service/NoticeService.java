package com.bookstore.readme.domain.notice.service;

import com.bookstore.readme.domain.notice.domain.Notice;
import com.bookstore.readme.domain.notice.dto.NoticeSearchDto;
import com.bookstore.readme.domain.notice.exception.NotFoundNoticeByIdException;
import com.bookstore.readme.domain.notice.repository.NoticeRepository;
import com.bookstore.readme.domain.notice.request.NoticeSaveRequest;
import com.bookstore.readme.domain.notice.response.NoticeResponse;
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

    public NoticeResponse searchNotice(Long noticeId) {
        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new NotFoundNoticeByIdException(noticeId));

        NoticeSearchDto noticeSearchDto = NoticeSearchDto.of(notice);
        return NoticeResponse.ok(noticeSearchDto);
    }
}