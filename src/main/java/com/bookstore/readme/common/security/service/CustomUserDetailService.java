package com.bookstore.readme.common.security.service;

import com.bookstore.readme.domain.member.exception.NotFoundMemberByEmailException;
import com.bookstore.readme.domain.member.model.Member;
import com.bookstore.readme.domain.member.model.MemberDetails;
import com.bookstore.readme.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Member byLoginMember = memberRepository.findByEmail(email)
                .orElseThrow(
                        () -> new NotFoundMemberByEmailException(email)
                );

        return User.builder()
                .username(byLoginMember.getEmail())
                .password(byLoginMember.getPassword())
                .roles(byLoginMember.getRole().name())
                .build();
    }
}
