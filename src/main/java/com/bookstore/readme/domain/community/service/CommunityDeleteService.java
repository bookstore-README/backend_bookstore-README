package com.bookstore.readme.domain.community.service;

import com.bookstore.readme.domain.community.repository.CommunityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommunityDeleteService {
    private final CommunityRepository communityRepository;

    public Long deleteByCommunityId(Long communityId) {
        communityRepository.deleteById(communityId);
        return communityId;
    }
}
