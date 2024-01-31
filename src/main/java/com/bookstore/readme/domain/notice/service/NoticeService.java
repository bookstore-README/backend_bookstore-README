package com.bookstore.readme.domain.notice.service;

import com.bookstore.readme.domain.notice.domain.Notice;
import com.bookstore.readme.domain.notice.dto.NoticeDto;
import com.bookstore.readme.domain.notice.dto.NoticeListDto;
import com.bookstore.readme.domain.notice.dto.NoticeSearchDto;
import com.bookstore.readme.domain.notice.exception.NotFoundNoticeByIdException;
import com.bookstore.readme.domain.notice.repository.NoticeRepository;
import com.bookstore.readme.domain.notice.request.NoticeSaveRequest;
import com.bookstore.readme.domain.notice.request.NoticeUpdateRequest;
import com.bookstore.readme.domain.notice.response.NoticeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeRepository noticeRepository;

    public NoticeResponse getNotice(Long noticeId) {
        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new NotFoundNoticeByIdException(noticeId));

        NoticeSearchDto noticeSearchDto = NoticeSearchDto.of(notice);
        return NoticeResponse.ok(noticeSearchDto);
    }

    public NoticeResponse getNotices() {
        List<Notice> notices = noticeRepository.findAll();
        List<NoticeListDto> convertNotices = notices.stream()
                .map(NoticeListDto::of)
                .toList();

        if (convertNotices.isEmpty())
            return NoticeResponse.empty(convertNotices);

        return NoticeResponse.ok(convertNotices);
    }

    public Long save(NoticeSaveRequest request) {
        Notice notice = request.toNotice();
        noticeRepository.save(notice);
        return notice.getId();
    }

    public Long delete(Long noticeId) {
        noticeRepository.deleteById(noticeId);
        return noticeId;
    }

    @Transactional
    public NoticeDto update(Long noticeId, NoticeUpdateRequest request) {
        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new NotFoundNoticeByIdException(noticeId));

        if (StringUtils.hasText(request.getTitle())) {
            notice.updateTitle(request.getTitle());
        }

        if (StringUtils.hasText(request.getContent())) {
            notice.updateContent(request.getContent());
        }

        noticeRepository.flush();
        return NoticeDto.of(notice);
    }
}