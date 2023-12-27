package com.example.sports.partner.partnerApplicant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PARepository extends JpaRepository<PartnerApplicant, Long> {
}
