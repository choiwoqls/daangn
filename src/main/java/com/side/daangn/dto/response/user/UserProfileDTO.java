package com.side.daangn.dto.response.user;

import com.side.daangn.entitiy.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class UserProfileDTO {
    private UUID id;
    private String name;
    private double temp;
    private String image;

    public UserProfileDTO(User user){
        this.id=user.getId();
        this.name=user.getName();
        this.temp= user.getTemp();
        this.image=user.getImage();
    }
}
