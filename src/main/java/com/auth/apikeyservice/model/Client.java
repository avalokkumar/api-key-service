package com.auth.apikeyservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class Client {

    @JsonProperty("client_name")
    private String name;

    @JsonProperty("client_email")
    private String email;

    @JsonProperty("client_owner")
    private String owner;

    @JsonProperty("allowed_endpoints")
    private List<String> allowedEndpoints;

    @JsonProperty("scope")
    private String scope;

    @JsonProperty("expiration_date")
    private LocalDateTime expirationDate;

    @JsonProperty("usage_limit")
    private int usageLimit;

    @JsonProperty("ip_restrictions")
    private List<String> ipRestrictions;

    @JsonProperty("referer_restrictions")
    private List<String> refererRestrictions;

    @JsonProperty("user_agent_restrictions")
    private List<String> userAgentRestrictions;

    @JsonProperty("custom_fields")
    private Map<String, Object> customFields;

    // Constructor, getters, and setters omitted for brevity
}
