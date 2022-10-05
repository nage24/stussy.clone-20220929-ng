package com.stussy.stussy.clone20220929ng.dto.account;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class RegisterReqDto {

    @Pattern(regexp = "^[가-힇]{1,3}$",
            message = "이름은 한글만 입력가능하며 한글자 이상 세글자 이하로 작성하세요."
    , groups = "ValidationGroups.PatternCheckGroup.class") // message 는 오류메시지
    private String lastName;

    @Pattern(regexp = "^[가-힇]{1,2}$", message = "성은 한글만 입력가능하며 한글자 이상 두글자 이하로 작성하세요."
            , groups = "ValidationGroups.PatternCheckGroup.class")
    private String firstName;

    @NotBlank // message 안 달면 기본 메시지가 나옴.
    @Email(message = "잘못된 이메일 형식입니다.") // @Email 은 null 을 유효하다고 판단, NotBlank 달아줘야 함.
    private String email;

    @NotBlank(message = "비밀번호는 비워 둘 수 없습니다.")
    @Pattern(regexp = "(?=.*[a-zA-Z]$])(?=.*\\d)(?=.*[!#@%^&*_])[a-zA-Z\\d-!@#$%^&*_](8,16)$", message="숫자, 영문(대소문자), 특수기호를 하나 이상 포함하여야 하며 8자 이상 16자 이하로 작성해야 합니다."
            , groups = "ValidationGroups.NotBlankGroup.class")
    private String password;
}
