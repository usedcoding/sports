package com.example.sports.partner.partnerWriter;

import com.example.sports.member.Member;
import com.example.sports.partner.partnerPost.PartnerPost;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PWService {
    private final PWRepository pcRepository;

    public PartnerWriter getPartnerWriter(Long id) {
        Optional<PartnerWriter> partnerWriter = this.pcRepository.findById(id);
        return partnerWriter.get();
    }

    public void create(PartnerPost partnerPost, String content, Member author) {
        PartnerWriter partnerWriter = new PartnerWriter();
        partnerWriter.setContent(content);
        partnerWriter.setPartnerPost(partnerPost);
        partnerWriter.setAuthor(author);
        partnerWriter.setCreateDate(LocalDate.now());

        this.pcRepository.save(partnerWriter);
    }

    public void delete(PartnerWriter partnerWriter) {
        this.pcRepository.delete(partnerWriter);
    }

    public void modify(PartnerWriter partnerWriter, String content) {
        partnerWriter.setContent(content);
        partnerWriter.setModifyDate(LocalDate.now());

        this.pcRepository.save(partnerWriter);
    }
}
