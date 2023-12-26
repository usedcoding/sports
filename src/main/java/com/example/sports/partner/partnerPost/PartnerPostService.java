package com.example.sports.partner.partnerPost;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PartnerPostService {
    private final PartnerPostRepository partnerPostRepository;

    public void create(String title, String content) {
        PartnerPost partnerPost = new PartnerPost();
        partnerPost.setTitle(title);
        partnerPost.setContent(content);
        partnerPost.setCreateDate(LocalDate.now());

        this.partnerPostRepository.save(partnerPost);
    }

    public List<PartnerPost> getList() {
        return this.partnerPostRepository.findAll();
    }

    public PartnerPost getPartnerPost(Long id) {
        Optional<PartnerPost> partnerPost = this.partnerPostRepository.findById(id);
        return partnerPost.get();
    }

    public void delete(PartnerPost partnerPost) {
        this.partnerPostRepository.delete(partnerPost);
    }

    public void modify(PartnerPost partnerPost, String content) {
        partnerPost.setContent(content);
        partnerPost.setModifyDate(LocalDate.now());

        this.partnerPostRepository.save(partnerPost);

    }
}
