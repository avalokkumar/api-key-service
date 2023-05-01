package com.auth.apikeyservice.util;

import com.auth.apikeyservice.model.Client;
import com.opencsv.CSVReader;

import java.io.FileReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CsvReaderUtil {

    public static List<Client> readClients() {
        String csvFile = "src/main/resources/data/clients.csv";
        List<Client> clients = null;
        try (CSVReader reader = new CSVReader(new FileReader(csvFile))) {
            List<String[]> lines = reader.readAll();

            // Skip the header line
            clients = lines.stream().skip(1).map(line -> {
                // Parse the values from the CSV line
                String name = line[0];
                String email = line[1];
                String owner = line[2];
                List<String> allowedEndpoints = Arrays.asList(line[3].split(", "));
                String scope = line[4];
                LocalDateTime expirationDate = LocalDateTime.parse(line[5], DateTimeFormatter.ISO_DATE_TIME);
                int usageLimit = Integer.parseInt(line[6]);
                List<String> ipRestrictions = Arrays.asList(line[7].split(", "));
                List<String> refererRestrictions = Arrays.asList(line[8].split(", "));
                List<String> userAgentRestrictions = Arrays.asList(line[9].split(", "));
                Map<String, Object> customFields = Arrays.stream(line[10].replaceAll("\\{", "").replaceAll("}", "").split(","))
                        .map(s -> s.split(":"))
                        .collect(Collectors.toMap(a -> a[0].trim(), a -> a[1].trim()));

                // Create and return a new Client object
                return Client.builder()
                        .name(name)
                        .email(email)
                        .owner(owner)
                        .allowedEndpoints(allowedEndpoints)
                        .scope(scope)
                        .expirationDate(expirationDate)
                        .usageLimit(usageLimit)
                        .ipRestrictions(ipRestrictions)
                        .refererRestrictions(refererRestrictions)
                        .userAgentRestrictions(userAgentRestrictions)
                        .customFields(customFields)
                        .build();
            }).collect(Collectors.toList());

            System.out.println(clients);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return clients;
    }

}
