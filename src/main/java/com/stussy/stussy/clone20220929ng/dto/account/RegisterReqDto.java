package com.stussy.stussy.clone20220929ng.dto.account;

import com.stussy.stussy.clone20220929ng.domain.User;
import com.stussy.stussy.clone20220929ng.dto.validation.ValidationGroups;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class RegisterReqDto {

    @NotBlank(message = "이름은 비워둘 수 없습니다"
            , groups = ValidationGroups.NotBlankGroup.class)
    @Size(min = 1, max = 3, message = "이름은 한글자에서 세글자 사이여야 합니다")
    @Pattern(regexp = "^[가-힇]*$",
            message = "이름은 한글만 입력가능합니다"
            , groups = ValidationGroups.PatternCheckGroup.class) // message 는 오류메시지
    private String lastName;

    @NotBlank(message = "성은 비워둘 수 없습니다"
            , groups = ValidationGroups.NotBlankGroup.class)
    @Size(min = 1, max = 3, message = "성은 한글자에서 두글자 사이여야 합니다")
    @Pattern(regexp = "^[가-힇]*$", message = "성은 한글만 입력가능합니다"
            , groups = ValidationGroups.PatternCheckGroup.class)
    private String firstName;

    @NotBlank(groups = ValidationGroups.NotBlankGroup.class) // message 안 달면 기본 메시지가 나옴.
    @Email(message = "잘못된 이메일 형식입니다") // @Email 은 null 을 유효하다고 판단, NotBlank 달아줘야 함.
    private String email;

    @NotBlank(message = "비밀번호는 비워 둘 수 없습니다"
                ,groups = ValidationGroups.NotBlankGroup.class)
    @Size(min = 8, max = 16, message = "비밀번호는 8자 이상 16자 이하로 작성해야 합니다")
    @Pattern(regexp = "(?=.*[a-zA-Z]$])(?=.*\\d)(?=.*[!#@%^&*_])[a-zA-Z\\d-!@#$%^&*_]*$", message="숫자, 영문, 특수기호를 하나 이상 포함하여야 합니다"
            , groups = ValidationGroups.PatternCheckGroup.class)
    private String password;



    public User toUserEntity() {
        return User.builder()
                .username(email)
                .password(new BCryptPasswordEncoder().encode(password)) // password 암호화
                .name(firstName + lastName)
                .email(email)
                .role_id(1)         // 일반 유저 register
                .build();
    }

}
