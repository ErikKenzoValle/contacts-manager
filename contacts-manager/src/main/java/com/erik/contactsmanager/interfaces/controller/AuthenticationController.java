package com.erik.contactsmanager.interfaces.controller;

import com.erik.contactsmanager.usecases.GenerateAuthorizationUrl;
import com.erik.contactsmanager.usecases.ProcessCallback;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final GenerateAuthorizationUrl generateAuthUrl;
    private final ProcessCallback processCallback;

    @GetMapping("/url")
    public ResponseEntity<String> getAuthorizationUrl() {
        String url = generateAuthUrl.execute();

        return ResponseEntity.ok(url);
    }

    @PostMapping("/callback")
    public ResponseEntity<String> processCallback(@NotBlank @RequestParam("code") String code) {
        String accessToken = processCallback.execute(code);

        return ResponseEntity.ok("Access token: " + accessToken);
    }

}
