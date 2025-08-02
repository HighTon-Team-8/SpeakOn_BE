package com.example.team8be.domain.user.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MyInfoResponse {
    private String userName;
    private String introduction;
    private String profileImageUrl;
}
