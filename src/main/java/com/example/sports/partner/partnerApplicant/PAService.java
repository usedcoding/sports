package com.example.sports.partner.partnerApplicant;

import com.example.sports.member.Member;
import com.example.sports.partner.partnerPost.PartnerPost;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PAService {
    private final PARepository paRepository;

    public List<PartnerApplicant> getList() {
        return this.paRepository.findAll();
    }

    public PartnerApplicant getPartnerApplicant(Long id) {
        Optional<PartnerApplicant> partnerApplicant = this.paRepository.findById(id);
        return partnerApplicant.get();
    }

    public void create(PartnerPost partnerPost, String content, Member author) {
        PartnerApplicant partnerApplicant = new PartnerApplicant();
        partnerApplicant.setContent(content);
        partnerApplicant.setAuthor(author);
        partnerApplicant.setPartnerPost(partnerPost);
        partnerApplicant.setCreateDate(LocalDateTime.now());

        this.paRepository.save(partnerApplicant);
    }

    public void delete(PartnerApplicant partnerApplicant) {
        this.paRepository.delete(partnerApplicant);
    }

}
