package com.auth.apikeyservice.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "api_key_verification")
public class ApiKeyVerification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "client_id")
    private String clientId;

    @Column(name = "api_key")
    private String apiKey;

    @Column(name = "timestamp")
    private long timestamp;

    @Column(name = "signature")
    private String signature;


    @Column(name = "valid_api_key")
    private boolean validApiKey;

    @Column(name = "api_key_owner")
    private String apiKeyOwner;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "allowed_endpoints")
    @Column(name = "endpoint")
    private List<String> allowedEndpoints;

    @Column(name = "error_message")
    private String errorMessage;

}