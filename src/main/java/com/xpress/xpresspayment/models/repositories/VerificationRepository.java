package com.xpress.xpresspayment.models.repositories;

import com.xpress.xpresspayment.models.TokenType;
import com.xpress.xpresspayment.models.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerificationRepository extends JpaRepository<VerificationToken, Long> {

    void deleteByEmailAndTokenType(String email, TokenType tokenType);

    Optional<VerificationToken> findByTokenAndTokenType(String token, TokenType tokenType);

    boolean existsByToken(String res);
}

