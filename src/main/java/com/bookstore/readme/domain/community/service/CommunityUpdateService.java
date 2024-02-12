package com.bookstore.readme.domain.community.service;

import com.bookstore.readme.domain.community.domain.Community;
import com.bookstore.readme.domain.community.dto.CommunityBookDto;
import com.bookstore.readme.domain.community.dto.CommunityMemberDto;
import com.bookstore.readme.domain.community.dto.CommunityUpdateDto;
import com.bookstore.readme.domain.community.exception.NotFoundCommunityByIdException;
import com.bookstore.readme.domain.community.repository.CommunityRepository;
import com.bookstore.readme.domain.community.request.CommunityUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommunityUpdateService {
    private final CommunityRepository communityRepository;

    @Transactional
    public CommunityUpdateDto updateByCommunityId(Long communityId, CommunityUpdateRequest request) {
        String title = request.getTitle();
        String content = request.getContent();

        Community community = communityRepository.findById(communityId)
                .orElseThrow(() -> new NotFoundCommunityByIdException(communityId));

        community.changeTitle(title);
        community.changeContent(content);
        communityRepository.flush();
        CommunityBookDto communityBookDto = CommunityBookDto.of(community.getBook());
        CommunityMemberDto communityMemberDto = CommunityMemberDto.of(community.getMember());

        return CommunityUpdateDto.builder()
                .communityId(community.getId())
                .title(community.getTitle())
                .content(community.getContent())
                .writer(communityMemberDto)
                .bookInfo(communityBookDto)
                .createDate(community.getCreateDate())
                .updateDate(community.getUpdateDate())
                .build();

    }
}
