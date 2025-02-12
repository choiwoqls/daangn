package com.side.daangn.dto.response.user;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TimeLimitDTO {
    private String msg;
    private Long ttl;
}
