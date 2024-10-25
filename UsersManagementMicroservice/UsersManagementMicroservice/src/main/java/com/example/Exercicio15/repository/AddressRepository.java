package com.example.Exercicio15.repository;

import com.example.Exercicio15.model.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<Address, UUID> {
    void deleteById(UUID id);
    Optional<Address> findById(UUID id);
    boolean existsById(UUID id);
    List<Address> findByUserAccountId(UUID userId);
    Page<Address> findAll(Pageable pageable);
}