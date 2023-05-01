package com.auth.apikeyservice.util;

import com.auth.apikeyservice.exception.InvalidAPIKeyException;
import com.auth.apikeyservice.model.ApiKeyRequest;
import com.auth.apikeyservice.model.ApiKeyVerificationRequest;
import com.auth.apikeyservice.model.Client;
import com.nimbusds.jose.shaded.json.JSONObject;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.*;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;

import static com.auth.apikeyservice.util.Constant.*;

public class ApiKeyUtil {

    public static String generateApiKey(ApiKeyRequest request, String secretKey, int apiKeyValidity, int expirationInMonth, int usageLimit) throws NoSuchAlgorithmException, InvalidKeyException {
        // Build the payload as a JSON object
        JSONObject payloadObj = new JSONObject();
        payloadObj.put(CLIENT_TYPE, request.getClientType());
        payloadObj.put(CLIENT_NAME, request.getClientName());
        payloadObj.put(API_KEY_OWNER, request.getApiKeyOwner());
        payloadObj.put(ALLOWED_ENDPOINTS, request.getAllowedEndpoints());
        payloadObj.put(CLIENT_EMAIL, request.getClientEmail());
        payloadObj.put(CLIENT_DESCRIPTION, request.getClientDescription());
        payloadObj.put(SCOPE, request.getScope());
        payloadObj.put(IP_RESTRICTIONS, request.getIpRestrictions());
        payloadObj.put(REFERER_RESTRICTIONS, request.getRefererRestrictions());
        payloadObj.put(USER_AGENT_RESTRICTIONS, request.getUserAgentRestrictions());
        payloadObj.put(CUSTOM_FIELDS, request.getCustomFields());
        payloadObj.put(API_KEY_VALIDITY, apiKeyValidity);
        payloadObj.put(EXPIRATION_DATE, request.getExpirationDate().plusMonths(expirationInMonth).toString());
        payloadObj.put(USAGE_LIMIT, usageLimit);

        // Hash the payload using SHA-256
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] payloadHash = digest.digest(payloadObj.toString().getBytes(StandardCharsets.UTF_8));

        // Generate a random salt
        SecureRandom random = SecureRandom.getInstanceStrong();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        // Sign the payload hash and salt using the secret key
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        mac.init(secretKeySpec);
        byte[] signature = mac.doFinal(concatenateByteArrays(payloadHash, salt));

        // Return the API key as a Base64-encoded string
        String payload = Base64.getEncoder().encodeToString(payloadObj.toString().getBytes(StandardCharsets.UTF_8));
        String saltStr = Base64.getEncoder().encodeToString(salt);
        String signatureStr = Base64.getEncoder().encodeToString(signature);
        return payload + "." + saltStr + "." + signatureStr;
    }

    public static boolean verifyApiKey(ApiKeyVerificationRequest request, String secretKey) throws NoSuchAlgorithmException,
            InvalidKeyException {

        // Split the API key into its payload, salt, and signature parts
        String[] parts = request.getApiKey().split("\\.");
        if (parts.length != 3) {
            throw new InvalidAPIKeyException("Invalid API key format");
        }
        String payloadStr = parts[0];
        String saltStr = parts[1];
        String signatureStr = parts[2];

        // Decode the payload, salt, and signature
        byte[] payload = Base64.getDecoder().decode(payloadStr.getBytes());
        byte[] salt = Base64.getDecoder().decode(saltStr.getBytes());
        byte[] signature = Base64.getDecoder().decode(signatureStr.getBytes());

        // Hash the payload using SHA-256
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] payloadHash = digest.digest(payload);

        // Sign the payload hash and salt using the secret key
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        mac.init(secretKeySpec);
        byte[] expectedSignature = mac.doFinal(concatenateByteArrays(payloadHash, salt));

        // Check if the signature matches the expected signature
        if (!Arrays.equals(signature, expectedSignature)) {
            throw new InvalidAPIKeyException("Invalid API key signature");
        }

        Optional<Client> clientOpt =
                CsvReaderUtil.readClients().stream().filter(clientDetail -> clientDetail.getEmail().equalsIgnoreCase(request.getClientEmail())).findAny();

        if (clientOpt.isEmpty()) {
            throw new InvalidAPIKeyException("Invalid client details");
        }

        Client client = clientOpt.get();
        LocalDateTime expirationDateTime = client.getExpirationDate();

        // Check if the API key is still valid
        if (expirationDateTime.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("API key expired");
        }

        // Check if the API key has exceeded its usage limit
        int usageCount = getUsageCount(request.getApiKey());

        // If all checks pass, the API key is valid
        return usageCount < client.getUsageLimit();
    }

    private static int getUsageCount(String apiKey) {
        // TODO: Implement logic to retrieve the current usage count for the API key
        return 10;
    }


    private static byte[] concatenateByteArrays(byte[] a, byte[] b) {
        byte[] result = new byte[a.length + b.length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }

}
