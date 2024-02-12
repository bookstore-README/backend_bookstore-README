package com.bookstore.readme.domain.community.repository;

import com.bookstore.readme.domain.community.domain.Community;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityRepository extends JpaRepository<Community, Long> {

}
