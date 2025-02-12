package com.side.daangn.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {

    @NotBlank(message = "이메일은 필수 입력 정보 입니다.")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "올바른 이메일 형식으로 입력해 주세요.")
    private String email;

    @NotBlank(message = "8자 이상 알파벳, 숫자, 특수문자를 포함한 비밀번호를 입력해주세요.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "8자 이상 알파벳, 숫자, 특수문자를 포함한 비밀번호를 입력해주세요.")
    private String password;
}
