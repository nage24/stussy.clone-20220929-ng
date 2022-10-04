package com.stussy.stussy.clone20220929ng.repository;

import com.stussy.stussy.clone20220929ng.dto.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccountRepository {
    public int save(User user);
    public User findUserByEmail(String Email); // select user by email
}
