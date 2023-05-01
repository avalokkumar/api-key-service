API Service
This is a RESTful API service built using Gradle, Java, Spring, Spring Security, Spring Data JPA, H2 DB, and OpenCSV. The service provides two endpoints for generating and verifying API keys.

### Dependencies
* Gradle
* Java 11 or later
* Spring Boot 2.5.0
* Spring Security 5.5.0
* Spring Data JPA 2.5.0
* H2 Database 1.4.200
* OpenCSV 5.2

### Installation
* Clone the repository
* Install Gradle if not already installed: https://gradle.org/install/
* Open the project in your preferred IDE
* Build the project using Gradle: gradle build
* Run the project: `./gradlew :bootRun`

### Endpoints

#### Generate API Key
* URL: /api-keys/generate
* Method: POST
* Request Body: ApiKeyRequest (JSON)
* Response: ApiKeyResponse (JSON)
> This endpoint generates a new API key based on the provided request body.

#### Verify API Key
* URL: /api-keys/verify
* Method: POST
* Request Body: ApiKeyVerificationRequest (JSON)
* Response: ApiKeyVerificationResponse (JSON)
> This endpoint verifies the validity of an API key based on the provided request body.

### Configuration
The service is configured to use an H2 in-memory database by default. This can be changed by modifying the application.properties file.

### H2 Console Configuration
```properties
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
spring.h2.console.settings.trace=false
spring.h2.console.settings.web-allow-others=false
spring.h2.console.settings.web-allow-credentials=false
spring.h2.console.settings.web-port=8082
```

The service is also configured to use OpenCSV for reading and writing CSV files. This can be modified in the build.gradle file.

```groovy
dependencies {
    implementation 'com.opencsv:opencsv:5.2'
}
```

### Security
The API service uses Spring Security to secure the endpoints. The ApiKeyAuthenticationFilter class is responsible for authenticating API key requests, while the ApiKeyAuthorizationFilter class handles authorization.

### Usage
The API service is intended to be used for generating and verifying API keys. The ApiKeyRequest class is used to generate new API keys, while the ApiKeyVerificationRequest class is used to verify existing API keys.

### Generate API Key
To generate a new API key, send a POST request to the `/api-keys/generate` endpoint with the following request body

```
POST 
/api-keys/generate
```
### Request:
```json
{
    "client_type": "Mobile",
    "client_name": "MyApp",
    "api_key_owner": "John Doe",
    "api_key_validity": 3600,
    "allowed_endpoints": [
        "/api/v1/users",
        "/api/v1/orders"
    ],
    "client_email": "john.doe@example.com",
    "client_description": "MyApp is a mobile app that allows users to order food online.",
    "scope": "read write",
    "expiration_date": "2023-05-31T12:00:00",
    "usage_limit": 10000,
    "ip_restrictions": [
        "192.168.0.1",
        "192.168.0.0/24"
    ],
    "referer_restrictions": [
        "https://www.example.com",
        "https://www.myapp.com"
    ],
    "user_agent_restrictions": [
        "Mozilla/5.0",
        "Chrome/91.0.4472.124"
    ],
    "custom_fields": {
        "field1": "value1",
        "field2": "value2"
    }
}
```

### Response:

```json
{
    "scope": "read write",
    "api_key": "eyJjbGllbnROYW1lIjoiTXlBcHAiLCJjbGllbnRFbWFpbCI6ImpvaG4uZG9lQGV4YW1wbGUuY29tIiwicmVmZXJlclJlc3RyaWN0aW9ucyI6WyJodHRwczpcL1wvd3d3LmV4YW1wbGUuY29tIiwiaHR0cHM6XC9cL3d3dy5teWFwcC5jb20iXSwiY3VzdG9tRmllbGRzIjp7ImZpZWxkMSI6InZhbHVlMSIsImZpZWxkMiI6InZhbHVlMiJ9LCJ1c2FnZUxpbWl0IjoxMDAwMCwiaXBSZXN0cmljdGlvbnMiOlsiMTkyLjE2OC4wLjEiLCIxOTIuMTY4LjAuMFwvMjQiXSwiYWxsb3dlZEVuZHBvaW50cyI6WyJcL2FwaVwvdjFcL3VzZXJzIiwiXC9hcGlcL3YxXC9vcmRlcnMiXSwiY2xpZW50VHlwZSI6Ik1vYmlsZSIsInVzZXJBZ2VudFJlc3RyaWN0aW9ucyI6WyJNb3ppbGxhXC81LjAiLCJDaHJvbWVcLzkxLjAuNDQ3Mi4xMjQiXSwiY2xpZW50RGVzY3JpcHRpb24iOiJNeUFwcCBpcyBhIG1vYmlsZSBhcHAgdGhhdCBhbGxvd3MgdXNlcnMgdG8gb3JkZXIgZm9vZCBvbmxpbmUuIiwic2NvcGUiOiJyZWFkIHdyaXRlIiwiYXBpS2V5VmFsaWRpdHkiOjI0LCJhcGlLZXlPd25lciI6IkpvaG4gRG9lIiwiZXhwaXJhdGlvbkRhdGUiOiIyMDI3LTA1LTMxVDEyOjAwIn0=.t1kk5lris7CayWUw1sBVyw==.rwWO4q/7ACHg49Y7/zsbJMxH+6FdgAwdYpay3JZvDjI=",
    "client_type": "Mobile",
    "client_name": "MyApp",
    "api_key_owner": "John Doe",
    "api_key_validity": 24,
    "allowed_endpoints": [
        "/api/v1/users",
        "/api/v1/orders"
    ],
    "expiration_date": "2027-04-30T19:31:59.508891",
    "usage_limit": 10000,
    "ip_restrictions": [
        "192.168.0.1",
        "192.168.0.0/24"
    ],
    "referer_restrictions": [
        "https://www.example.com",
        "https://www.myapp.com"
    ],
    "user_agent_restrictions": [
        "Mozilla/5.0",
        "Chrome/91.0.4472.124"
    ],
    "custom_fields": {
        "field1": "value1",
        "field2": "value2"
    }
}
```
---
### Verify API Key
To verify an API key, send a POST request to the `/api-keys/verify` endpoint with the following request body:

```
POST 
/api-keys/verify
```

### Request:

```json
{
  "client_email": "john.doe@example.com",
  "api_key": "eyJjbGllbnROYW1lIjoiTXlBcHAiLCJjbGllbnRFbWFpbCI6ImpvaG4uZG9lQGV4YW1wbGUuY29tIiwicmVmZXJlclJlc3RyaWN0aW9ucyI6WyJodHRwczpcL1wvd3d3LmV4YW1wbGUuY29tIiwiaHR0cHM6XC9cL3d3dy5teWFwcC5jb20iXSwiY3VzdG9tRmllbGRzIjp7ImZpZWxkMSI6InZhbHVlMSIsImZpZWxkMiI6InZhbHVlMiJ9LCJ1c2FnZUxpbWl0IjoxMDAwMCwiaXBSZXN0cmljdGlvbnMiOlsiMTkyLjE2OC4wLjEiLCIxOTIuMTY4LjAuMFwvMjQiXSwiYWxsb3dlZEVuZHBvaW50cyI6WyJcL2FwaVwvdjFcL3VzZXJzIiwiXC9hcGlcL3YxXC9vcmRlcnMiXSwiY2xpZW50VHlwZSI6Ik1vYmlsZSIsInVzZXJBZ2VudFJlc3RyaWN0aW9ucyI6WyJNb3ppbGxhXC81LjAiLCJDaHJvbWVcLzkxLjAuNDQ3Mi4xMjQiXSwiY2xpZW50RGVzY3JpcHRpb24iOiJNeUFwcCBpcyBhIG1vYmlsZSBhcHAgdGhhdCBhbGxvd3MgdXNlcnMgdG8gb3JkZXIgZm9vZCBvbmxpbmUuIiwic2NvcGUiOiJyZWFkIHdyaXRlIiwiYXBpS2V5VmFsaWRpdHkiOjI0LCJhcGlLZXlPd25lciI6IkpvaG4gRG9lIiwiZXhwaXJhdGlvbkRhdGUiOiIyMDI3LTA1LTMxVDEyOjAwIn0=.t1kk5lris7CayWUw1sBVyw==.rwWO4q/7ACHg49Y7/zsbJMxH+6FdgAwdYpay3JZvDjI=",
  "client_type": "mobile",
  "allowed_endpoint": [
    "/api/v1/users",
    "/api/v1/orders"
  ],
  "ip_address": "192.168.1.1",
  "referer_list": [
    "https://www.example.com",
    "https://www.another-example.com"
  ],
  "user_agents": [
    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36 Edge/16.16299",
    "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:54.0) Gecko/20100101 Firefox/54.0"
  ],
  "custom_fields": {
    "order_id": "12345",
    "coupon_code": "SAVE10"
  }
}
```

### Response

```json
{
    "valid": true,
    "client_type": "mobile",
    "client_name": "MyApp",
    "api_key_owner": "John Doe",
    "allowed_endpoints": [
        "/api/v1/users",
        "/api/v1/orders"
    ],
    "scope": "read",
    "expiration_date": "2023-12-31T23:59:59",
    "usage_limit": 1000,
    "ip_restrictions": [
        "[]"
    ],
    "referer_restrictions": [
        "https://www.example.com",
        "https://www.another-example.com"
    ],
    "user_agent_restrictions": [
        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36 Edge/16.16299",
        "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:54.0) Gecko/20100101 Firefox/54.0"
    ],
    "custom_fields": {
        "order_id": "12345",
        "coupon_code": "SAVE10"
    }
}
```
