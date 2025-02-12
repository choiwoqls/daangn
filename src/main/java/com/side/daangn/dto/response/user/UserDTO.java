package com.side.daangn.dto.response.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.side.daangn.entitiy.user.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private UUID id;

    @NotBlank(message = "이메일은 필수 입력 정보 입니다.")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "올바른 이메일 형식으로 입력해 주세요.")
    private String email;

    @NotBlank(message = "이름은 필수 입력 정보 입니다.")
    private String name;

    @JsonIgnore
    @NotBlank(message = "8자 이상 알파벳, 숫자, 특수문자를 포함한 비밀번호를 입력해주세요.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "8자 이상 알파벳, 숫자, 특수문자를 포함한 비밀번호를 입력해주세요.")
    private String password;

    private double temp;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime updatedAt;

    public UserDTO(User user){
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.password = user.getPassword();
        this.temp = user.getTemp();
        this.createdAt = user.getCreatedAt();
        this.updatedAt = user.getUpdatedAt();
    }

}
