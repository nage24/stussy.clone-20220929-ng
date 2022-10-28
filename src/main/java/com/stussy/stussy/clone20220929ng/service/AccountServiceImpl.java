package com.stussy.stussy.clone20220929ng.service;

import com.stussy.stussy.clone20220929ng.domain.Product;
import com.stussy.stussy.clone20220929ng.domain.User;
import com.stussy.stussy.clone20220929ng.dto.account.RegisterReqDto;
import com.stussy.stussy.clone20220929ng.exception.CustomValidationException;
import com.stussy.stussy.clone20220929ng.repository.AccountRepository;
import com.stussy.stussy.clone20220929ng.repository.admin.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;


    @Override
    public boolean checkDuplicatedEmail(String email) {

        User user = accountRepository.findUserByEmail(email);
        if(user != null) { // 중복 id(email) 가 존재함. -> 예외 처리
            Map<String, String> errorMap = new HashMap<String, String>();
            errorMap.put("duplicatedFlag", "이미 가입된 이메일입니다");

            throw new CustomValidationException("Duplicated Email Error!", errorMap); // message , errorMap
        }

        // 중복 id(email) 가 존재하지 않음 -> true;
        return true;
    }

    @Override
    public boolean register(RegisterReqDto registerReqDto) throws Exception {
        // 비밀번호 암호화 필요 !

        User userEntity = registerReqDto.toUserEntity();
        int result = accountRepository.save(userEntity);

        return result != 0;

        // return accountRepository.save(registerReqDto.toUserEntity()) != 0;
    }

}
