package com.example.team8be.domain.user.presentation;

import com.example.team8be.domain.user.presentation.dto.response.MyInfoResponse;
import com.example.team8be.domain.user.service.QueryMyInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final QueryMyInfoService queryMyInfoService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/my-info")
    public MyInfoResponse queryMyInfo() {
        return queryMyInfoService.execute();
    }
}
