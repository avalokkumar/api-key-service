package com.auth.apikeyservice.service;

import com.auth.apikeyservice.model.*;
import com.auth.apikeyservice.repository.ApiKeyRepository;
import com.auth.apikeyservice.repository.ApiKeyVerificationRepository;
import com.auth.apikeyservice.util.ApiKeyUtil;
import com.auth.apikeyservice.util.CsvReaderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class ApiKeyServiceImpl implements ApiKeyService {

    private static final String CLIENTS_FILE_PATH = "classpath:data/clients.csv";
    private static final String DELIMITER = ",";

    @Value("${api.key.secret:'s3cret'}")
    private String apiKeySecret;

    @Value("${api.key.validity:24}")
    private int apiKeyValidity;

    @Value("${api.key.expiration:48}")
    private int expirationInMonth;

    @Value("${api.key.usage.limit:10000}")
    private int usageLimit;

    @Autowired
    private ApiKeyRepository apiKeyRepository;

    @Autowired
    private ApiKeyVerificationRepository apiKeyVerificationRepository;

    @Override
    public ApiKeyResponse generateApiKey(ApiKeyRequest apiKeyRequest) throws NoSuchAlgorithmException, InvalidKeyException {

        // Read the client details from the clients file
        List<Client> clients = CsvReaderUtil.readClients();

        // Check if the client details exist in the file
        boolean isValidClient =
                clients.stream().anyMatch(client -> client.getEmail().equalsIgnoreCase(apiKeyRequest.getClientEmail()));

        if (clients.isEmpty() || !isValidClient) {
            throw new IllegalArgumentException("Invalid client details");
        }

        // Generate and return the API key response
        String apiKey = ApiKeyUtil.generateApiKey(apiKeyRequest, apiKeySecret, apiKeyValidity, expirationInMonth
                , usageLimit);

        return ApiKeyResponse.builder()
                .apiKey(apiKey)
                .clientType(apiKeyRequest.getClientType())
                .clientName(apiKeyRequest.getClientName())
                .apiKeyOwner(apiKeyRequest.getApiKeyOwner())
                .apiKeyValidity(apiKeyValidity)
                .allowedEndpoints(apiKeyRequest.getAllowedEndpoints())
                .scope(apiKeyRequest.getScope()) //comma separated values
                .expirationDate(LocalDateTime.now().plus(expirationInMonth, ChronoUnit.MONTHS))
                .usageLimit(usageLimit)
                .ipRestrictions(apiKeyRequest.getIpRestrictions())
                .refererRestrictions(apiKeyRequest.getRefererRestrictions())
                .userAgentRestrictions(apiKeyRequest.getUserAgentRestrictions())
                .customFields(apiKeyRequest.getCustomFields())
                .build();
    }

    @Override
    public ApiKeyVerificationResponse verifyApiKey(ApiKeyVerificationRequest apiKeyVerificationRequest) throws NoSuchAlgorithmException, InvalidKeyException {
        ApiKeyVerificationResponse.ApiKeyVerificationResponseBuilder responseBuilder = ApiKeyVerificationResponse.builder();

        // Validate the API key against the database or other storage mechanism
        boolean validApiKey = ApiKeyUtil.verifyApiKey(apiKeyVerificationRequest, apiKeySecret);

        // Check if the API key is valid
        if (!validApiKey) {
            responseBuilder.valid(false);
            return responseBuilder.build();
        }
        responseBuilder.valid(true);
        responseBuilder.clientType(apiKeyVerificationRequest.getClientType());

        // Read the client details from the clients file
        List<Client> clients = CsvReaderUtil.readClients();

        Optional<Client> clientOpt =
                clients.stream().filter(clientDetail -> clientDetail.getEmail().equalsIgnoreCase(apiKeyVerificationRequest.getClientEmail()))
                        .findFirst();

        // Retrieve the client details from the database or other storage mechanism
        if (clients.isEmpty() || clientOpt.isEmpty()) {
            throw new IllegalArgumentException("Invalid client details");
        }

        Client client = clientOpt.get();
        responseBuilder.clientName(client.getName())
                .apiKeyOwner(client.getOwner())
                .allowedEndpoints(apiKeyVerificationRequest.getAllowedEndpoint())
                .scope(client.getScope())
                .expirationDate(client.getExpirationDate())
                .usageLimit(client.getUsageLimit())
                .ipRestrictions(client.getIpRestrictions())
                .refererRestrictions(apiKeyVerificationRequest.getRefererList())
                .userAgentRestrictions(apiKeyVerificationRequest.getUserAgents())
                .customFields(apiKeyVerificationRequest.getCustomFields());

        return responseBuilder.build();
    }

    private Client getClient(String clientEmail) {

        return null;
    }

   /* private List<String> getClientsFromFile() {
        Resource resource = resourceLoader.getResource(CLIENTS_FILE_PATH);
        try {
            InputStream inputStream = resource.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            List<String> clients = reader.lines().collect(Collectors.toList());
            reader.close();
            inputStream.close();
            return clients;
        } catch (IOException e) {
            throw new RuntimeException("Failed to read clients file");
        }
    }*/
}
