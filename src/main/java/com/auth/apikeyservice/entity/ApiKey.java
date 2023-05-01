package com.auth.apikeyservice.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Entity
@Table(name = "api_key")
public class ApiKey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "api_key")
    private String apiKey;

    @Column(name = "client_type")
    private String clientType;

    @Column(name = "client_name")
    private String clientName;

    @Column(name = "api_key_owner")
    private String apiKeyOwner;

    @Column(name = "api_key_validity")
    private int apiKeyValidity;

    @ElementCollection
    @CollectionTable(name = "allowed_endpoints")
    @Column(name = "allowed_endpoint")
    private List<String> allowedEndpoints;

    @Column(name = "client_email")
    private String clientEmail;

    @Column(name = "client_description")
    private String clientDescription;

    @Column(name = "scope")
    private String scope;

    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;

    @Column(name = "usage_limit")
    private int usageLimit;

    @ElementCollection
    @CollectionTable(name = "ip_restrictions")
    @Column(name = "ip_restriction")
    private List<String> ipRestrictions;

    @ElementCollection
    @CollectionTable(name = "referer_restrictions")
    @Column(name = "referer_restriction")
    private List<String> refererRestrictions;

    @ElementCollection
    @CollectionTable(name = "user_agent_restrictions")
    @Column(name = "user_agent_restriction")
    private List<String> userAgentRestrictions;

    @ElementCollection
    @CollectionTable(name = "custom_fields")
    @MapKeyColumn(name = "custom_field_key")
    @Column(name = "custom_field_value")
    private Map<String, String> customFields;

}