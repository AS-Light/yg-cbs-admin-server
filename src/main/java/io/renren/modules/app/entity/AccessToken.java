package io.renren.modules.app.entity;

import lombok.Data;

@Data
public class AccessToken {
    private String token;
    private Integer expiresIn;
    private Long tokenTime;
}