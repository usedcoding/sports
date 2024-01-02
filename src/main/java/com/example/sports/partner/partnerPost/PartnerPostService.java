package com.example.sports.partner.partnerPost;

import com.example.sports.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PartnerPostService {
    private final PartnerPostRepository partnerPostRepository;

    public void create(String title, String content, Member member) {
        PartnerPost partnerPost = new PartnerPost();
        partnerPost.setTitle(title);
        partnerPost.setContent(content);
        partnerPost.setAuthor(member);
        partnerPost.setCreateDate(LocalDateTime.now());

        this.partnerPostRepository.save(partnerPost);
    }


    public Page<PartnerPost> getList(int page, String keyword) {
        Pageable pageable = PageRequest.of(page, 10);
        return this.partnerPostRepository.findAllByKeyword(keyword, pageable);
     }

    public PartnerPost getPartnerPost(Long id) {
        Optional<PartnerPost> partnerPost = this.partnerPostRepository.findById(id);
        return partnerPost.get();
    }

    public void delete(PartnerPost partnerPost) {
        this.partnerPostRepository.delete(partnerPost);
    }

    public void modify(PartnerPost partnerPost, String title, String content) {
        partnerPost.setTitle(title);
        partnerPost.setContent(content);
        partnerPost.setModifyDate(LocalDateTime.now());

        this.partnerPostRepository.save(partnerPost);

    }
}
