package com.auth.apikeyservice.repository;

import com.auth.apikeyservice.entity.ApiKeyVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiKeyVerificationRepository extends JpaRepository<ApiKeyVerification, Long> {
}
