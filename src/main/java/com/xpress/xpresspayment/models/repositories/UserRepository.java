package com.xpress.xpresspayment.models.repositories;

import com.xpress.xpresspayment.models.AppUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository< AppUser,Long> {

    Optional<AppUser> findByEmail(String email);

    boolean existsByEmail(String email);
    Page<AppUser> findByEnabledFalse(Pageable pageable);
}
