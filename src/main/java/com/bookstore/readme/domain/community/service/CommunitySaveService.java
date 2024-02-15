package com.bookstore.readme.domain.community.service;

import com.bookstore.readme.domain.book.domain.Book;
import com.bookstore.readme.domain.book.exception.NotFoundBookByIdException;
import com.bookstore.readme.domain.book.repository.BookRepository;
import com.bookstore.readme.domain.community.domain.Community;
import com.bookstore.readme.domain.community.domain.Emoji;
import com.bookstore.readme.domain.community.dto.CommunityBookDto;
import com.bookstore.readme.domain.community.dto.CommunityMemberDto;
import com.bookstore.readme.domain.community.dto.CommunitySaveDto;
import com.bookstore.readme.domain.community.repository.CommunityRepository;
import com.bookstore.readme.domain.community.repository.EmojiRepository;
import com.bookstore.readme.domain.community.request.CommunitySaveRequest;
import com.bookstore.readme.domain.community.response.CommunityResponse;
import com.bookstore.readme.domain.member.exception.NotFoundMemberByIdException;
import com.bookstore.readme.domain.member.model.Member;
import com.bookstore.readme.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommunitySaveService {
    private final CommunityRepository communityRepository;
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;
    private final EmojiRepository emojiRepository;

    @Transactional
    public CommunitySaveDto save(CommunitySaveRequest request) {
        String content = request.getContent();
        Long bookId = request.getBookId();
        Long memberId = request.getMemberId();

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundBookByIdException(bookId));

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundMemberByIdException(memberId));

        Community community = Community.builder()
                .content(content)
                .build();

        Emoji emoji = Emoji.builder()
                .heart(0)
                .smile(0)
                .excited(0)
                .angry(0)
                .crying(0)
                .build();
        emojiRepository.save(emoji);

        community.changeBook(book);
        community.changeMember(member);
        community.changeEmoji(emoji);
        communityRepository.save(community);
        communityRepository.flush();

        CommunityMemberDto communityMemberDto = CommunityMemberDto.of(member);
        CommunityBookDto communityBookDto = CommunityBookDto.of(book);
        return CommunitySaveDto.builder()
                .communityId(community.getId())
                .content(community.getContent())
                .writer(communityMemberDto)
                .bookInfo(communityBookDto)
                .createDate(community.getCreateDate())
                .updateDate(community.getUpdateDate())
                .build();
    }
}
