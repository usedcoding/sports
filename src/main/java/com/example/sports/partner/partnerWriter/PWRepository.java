package com.example.sports.partner.partnerWriter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PWRepository extends JpaRepository<PartnerWriter, Long> {
}
