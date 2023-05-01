CREATE TABLE api_key (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    api_key VARCHAR(255) NOT NULL,
    client_type VARCHAR(255) NOT NULL,
    client_name VARCHAR(255),
    api_key_owner VARCHAR(255) NOT NULL,
    api_key_validity INT NOT NULL,
    allowed_endpoints JSON,
    client_email VARCHAR(255),
    client_description VARCHAR(255),
    scope VARCHAR(255),
    expiration_date TIMESTAMP,
    usage_limit INT,
    ip_restrictions JSON,
    referer_restrictions JSON,
    user_agent_restrictions JSON,
    custom_fields JSON,
    UNIQUE(api_key)
);

CREATE TABLE allowed_endpoints (
  api_key_id BIGINT NOT NULL,
  allowed_endpoint VARCHAR(255) NOT NULL,
  CONSTRAINT allowed_endpoints_pk PRIMARY KEY (api_key_id, allowed_endpoint),
  CONSTRAINT allowed_endpoints_fk FOREIGN KEY (api_key_id) REFERENCES api_key(id)
);

CREATE TABLE ip_restrictions (
  api_key_id BIGINT NOT NULL,
  ip_restriction VARCHAR(255) NOT NULL,
  CONSTRAINT ip_restrictions_pk PRIMARY KEY (api_key_id, ip_restriction),
  CONSTRAINT ip_restrictions_fk FOREIGN KEY (api_key_id) REFERENCES api_key(id)
);

CREATE TABLE referer_restrictions (
  api_key_id BIGINT NOT NULL,
  referer_restriction VARCHAR(255) NOT NULL,
  CONSTRAINT referer_restrictions_pk PRIMARY KEY (api_key_id, referer_restriction),
  CONSTRAINT referer_restrictions_fk FOREIGN KEY (api_key_id) REFERENCES api_key(id)
);

CREATE TABLE user_agent_restrictions (
  api_key_id BIGINT NOT NULL,
  user_agent_restriction VARCHAR(255) NOT NULL,
  CONSTRAINT user_agent_restrictions_pk PRIMARY KEY (api_key_id, user_agent_restriction),
  CONSTRAINT user_agent_restrictions_fk FOREIGN KEY (api_key_id) REFERENCES api_key(id)
);

CREATE TABLE custom_fields (
  api_key_id BIGINT NOT NULL,
  custom_field_key VARCHAR(255) NOT NULL,
  custom_field_value VARCHAR(255) NOT NULL,
  CONSTRAINT custom_fields_pk PRIMARY KEY (api_key_id, custom_field_key),
  CONSTRAINT custom_fields_fk FOREIGN KEY (api_key_id) REFERENCES api_key(id)
);


CREATE TABLE api_key_verification (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  client_id VARCHAR(255) NOT NULL,
  api_key VARCHAR(255) NOT NULL,
  timestamp BIGINT NOT NULL,
  signature VARCHAR(255) NOT NULL,
  valid_api_key BOOLEAN NOT NULL,
  api_key_owner VARCHAR(255),
  allowed_endpoints JSON DEFAULT NULL,
  ip_restrictions JSON,
  referer_restrictions JSON,
  user_agent_restrictions JSON,
  error_message VARCHAR(255)
);

CREATE TABLE allowed_endpoints_verification (
  api_key_verification_id BIGINT NOT NULL,
  allowed_endpoint VARCHAR(255) NOT NULL,
  CONSTRAINT allowed_endpoints_verification_pk PRIMARY KEY (api_key_verification_id, allowed_endpoint),
  CONSTRAINT allowed_endpoints_verification_fk FOREIGN KEY (api_key_verification_id) REFERENCES api_key_verification(id)
);

-- Sample insert queries for ApiKey entity

INSERT INTO api_key (id, api_key, client_type, client_name, api_key_owner, api_key_validity, allowed_endpoints, client_email, client_description, scope, expiration_date, usage_limit, ip_restrictions, referer_restrictions, user_agent_restrictions, custom_fields)
VALUES (1, 'abc123', 'web', 'My Web App', 'John Doe', 3600, '["/api/users", "/api/products"]', 'john.doe@example.com', 'A web app for managing products and users', 'read write', '2023-12-31 23:59:59', 1000, '["127.0.0.1", "192.168.0.1"]', '["example.com", "test.com"]', '["Firefox", "Chrome"]', '{"field1": "value1", "field2": "value2"}');

INSERT INTO api_key (id, api_key, client_type, client_name, api_key_owner, api_key_validity, allowed_endpoints, client_email, client_description, scope, expiration_date, usage_limit, ip_restrictions, referer_restrictions, user_agent_restrictions, custom_fields)
VALUES (2, 'xyz456', 'mobile', 'My Mobile App', 'Jane Smith', 86400, '["/api/orders"]', 'jane.smith@example.com', 'A mobile app for placing orders', 'read', '2023-06-30 23:59:59', 500, null, null, null, '{"field1": "value1", "field2": "value2"}');


INSERT INTO allowed_endpoints (api_key_id, allowed_endpoint)
VALUES
(1, 'GET /api/user'), (2, 'POST /api/user');

INSERT INTO ip_restrictions (api_key_id, ip_restriction)
VALUES
(1, '192.168.1.1'), (2, '192.168.1.2');

INSERT INTO referer_restrictions (api_key_id, referer_restriction)
VALUES
(1, 'example.com');

INSERT INTO user_agent_restrictions (api_key_id, user_agent_restriction)
VALUES
(1, 'Firefox'), (2, 'Chrome');

INSERT INTO custom_fields (api_key_id, custom_field_key, custom_field_value)
VALUES
(1, 'company_name', 'Acme Inc.'), (2, 'team_name', 'Product Team');


-- Sample insert queries for ApiKeyVerification entity

INSERT INTO api_key_verification (client_id, api_key, timestamp, signature, valid_api_key, api_key_owner, allowed_endpoints, ip_restrictions, referer_restrictions, user_agent_restrictions, error_message)
VALUES ('123456', 'abcdef', 1641271121, 'abcdef123456', true, 'John Smith', '{"endpoint1", "endpoint2"}', '{"192.168.0.1", "192.168.0.2"}', '{"example.com", "test.com"}', '{"Chrome", "Firefox"}', NULL),
       ('789012', 'ghijkl', 1641271123, 'ghijkl789012', false, NULL, NULL, NULL, NULL, NULL, 'Invalid API key'),
       ('345678', 'mnopqr', 1641271125, 'mnopqr345678', true, 'Jane Doe', '{"endpoint1", "endpoint3"}', NULL, '{"example.com", "test.com"}', NULL, NULL),
       ('901234', 'stuvwx', 1641271127, 'stuvwx901234', false, NULL, NULL, NULL, NULL, NULL, 'API key has expired');


INSERT INTO allowed_endpoints_verification (api_key_verification_id, allowed_endpoint)
VALUES (1, 'https://example.com/api/v1/users'),
       (1, 'https://example.com/api/v1/products'),
       (2, 'https://example.com/api/v1/users');
