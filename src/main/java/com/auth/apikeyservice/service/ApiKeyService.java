package com.auth.apikeyservice.service;

import com.auth.apikeyservice.model.ApiKeyRequest;
import com.auth.apikeyservice.model.ApiKeyResponse;
import com.auth.apikeyservice.model.ApiKeyVerificationRequest;
import com.auth.apikeyservice.model.ApiKeyVerificationResponse;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface ApiKeyService {
    ApiKeyResponse generateApiKey(ApiKeyRequest clientId) throws NoSuchAlgorithmException, InvalidKeyException;

    ApiKeyVerificationResponse verifyApiKey(ApiKeyVerificationRequest apiKeyVerificationRequest) throws NoSuchAlgorithmException, InvalidKeyException;
}
