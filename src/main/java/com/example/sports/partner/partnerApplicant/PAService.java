package com.example.sports.partner.partnerApplicant;

import com.example.sports.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PAService {
    private final PARepository pwRepository;

    public PartnerApplicant getPartnerApplicant(Long id) {
        Optional<PartnerApplicant> partnerApplicant = this.pwRepository.findById(id);
        return partnerApplicant.get();
    }

    public void create(String content, Member author) {
        PartnerApplicant partnerApplicant = new PartnerApplicant();
        partnerApplicant.setContent(content);
        partnerApplicant.setAuthor(author);
        partnerApplicant.setCreateDate(LocalDate.now());

        this.pwRepository.save(partnerApplicant);
    }

    public void delete(PartnerApplicant partnerApplicant) {
        this.pwRepository.delete(partnerApplicant);
    }

    public void modify(String content, PartnerApplicant partnerApplicant) {
        partnerApplicant.setContent(content);
        partnerApplicant.setModifyDate(LocalDate.now());

        this.pwRepository.save(partnerApplicant);
    }
}
