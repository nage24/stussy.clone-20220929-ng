package com.stussy.stussy.clone20220929ng.service;

import com.stussy.stussy.clone20220929ng.domain.User;
import com.stussy.stussy.clone20220929ng.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrincipalDetailService implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        log.info("email >> {}", email);

        User user = accountRepository.findUserByEmail(email);

        if(user == null) { // 회원 정보 없음
            log.error("아이디를 찾지 못함");
            throw new UsernameNotFoundException("존재하지 않는 아이디입니다.");
        }

        return null;
    }
}
