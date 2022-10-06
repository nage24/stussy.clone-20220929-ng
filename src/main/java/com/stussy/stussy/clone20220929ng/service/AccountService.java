package com.stussy.stussy.clone20220929ng.service;

import com.stussy.stussy.clone20220929ng.dto.account.RegisterReqDto;

public interface AccountService {

    public boolean checkDuplicatedEmail(String email);
    public boolean register(RegisterReqDto registerReqDto)throws Exception;

}
