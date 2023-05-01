package com.auth.apikeyservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class ApiKeyVerificationRequest {

    @JsonProperty(value = "client_email")
    private String clientEmail;

    @JsonProperty(value = "api_key")
    private String apiKey; // The API key to be verified

    @JsonProperty(value = "client_type")
    private String clientType; // The client type associated with the API key

    @JsonProperty(value = "allowed_endpoint")
    private List<String> allowedEndpoint; // The endpoint that the client is attempting to access

    @JsonProperty(value = "ip_address")
    private String ipAddress; // The IP address of the client making the request

    @JsonProperty(value = "referer_list")
    private List<String> refererList; // The referer URL of the client making the request

    @JsonProperty(value = "user_agents")
    private List<String> userAgents; // The user agent string of the client making the request

    // Any additional custom fields that may be required for your specific use case.
    @JsonProperty(value = "custom_fields")
    private Map<String, Object> customFields;
}