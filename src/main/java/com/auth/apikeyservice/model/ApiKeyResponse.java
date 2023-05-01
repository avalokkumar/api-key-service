package com.auth.apikeyservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class ApiKeyResponse {
    @JsonProperty(value = "api_key")
    private String apiKey; // Generated API key
    @JsonProperty(value = "client_type")
    private String clientType; // Mobile, Web, IoT, etc.
    @JsonProperty(value = "client_name")
    private String clientName; // Name of the client application or service
    @JsonProperty(value = "api_key_owner")
    private String apiKeyOwner; // Name of the person or team responsible for the API key
    @JsonProperty(value = "api_key_validity")
    private int apiKeyValidity; // Validity of the API key in seconds
    @JsonProperty(value = "allowed_endpoints")
    private List<String> allowedEndpoints; // List of allowed endpoints for the API key
    private String scope; // The scope of the API key, i.e., the specific APIs or resources that the client is authorized to access.
    @JsonProperty(value = "expiration_date")
    private LocalDateTime expirationDate; // The expiration date of the API key.
    @JsonProperty(value = "usage_limit")
    private int usageLimit; // The maximum number of times the API key can be used.
    @JsonProperty(value = "ip_restrictions")
    private List<String> ipRestrictions; // A list of IP addresses or IP ranges that are allowed to use the API key.
    @JsonProperty(value = "referer_restrictions")
    private List<String> refererRestrictions; // A list of domain names or URLs that are allowed to use the API key.
    @JsonProperty(value = "user_agent_restrictions")
    private List<String> userAgentRestrictions; // A list of user agents or browser types that are allowed to use the API key.
    @JsonProperty(value = "custom_fields")
    private Map<String, Object> customFields; // Any additional custom fields that may be required for your specific use case.
}