package com.stussy.stussy.clone20220929ng.repository;

import com.stussy.stussy.clone20220929ng.domain.User;
import com.stussy.stussy.clone20220929ng.dto.account.RegisterReqDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccountRepository {
    public int save(User user);
    public User findUserByEmail(String Email); // select user by email
    public User updateUserOauth2(User user);
}
