package com.example.Exercicio15.repository;

import com.example.Exercicio15.model.UserAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, UUID> {
    Page<UserAccount> findByNameContainingIgnoreCase(String name, Pageable pageable);
    Optional<UserAccount> findById(UUID id);
    Page<UserAccount> findAll(Pageable pageable);
    void deleteById(UUID id);
    boolean existsById(UUID id);
    boolean existsByEmailAndIdNot(String email, UUID userAccountId);
    boolean existsByNifAndIdNot(int nif, UUID userAccountId);
    boolean existsByPhoneNumberAndIdNot(int phoneNumber, UUID userAccountId);
    boolean existsByEmail(String email);
    boolean existsByNif(int nif);
    boolean existsByPhoneNumber(int phoneNumber);
}