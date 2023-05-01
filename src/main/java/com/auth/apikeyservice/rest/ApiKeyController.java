package com.auth.apikeyservice.rest;

import com.auth.apikeyservice.model.ApiKeyRequest;
import com.auth.apikeyservice.model.ApiKeyResponse;
import com.auth.apikeyservice.model.ApiKeyVerificationRequest;
import com.auth.apikeyservice.model.ApiKeyVerificationResponse;
import com.auth.apikeyservice.service.ApiKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/api-keys")
public class ApiKeyController {

    @Autowired
    private ApiKeyService apiKeyService;

    @PostMapping(value = "/generate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiKeyResponse> generateApiKey(@RequestBody ApiKeyRequest apiKeyRequest) throws NoSuchAlgorithmException, InvalidKeyException {
        ApiKeyResponse response = apiKeyService.generateApiKey(apiKeyRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/verify", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiKeyVerificationResponse verifyApiKey(@RequestBody ApiKeyVerificationRequest apiKeyVerificationRequest) throws NoSuchAlgorithmException, InvalidKeyException {

        return apiKeyService.verifyApiKey(apiKeyVerificationRequest);
    }
}
