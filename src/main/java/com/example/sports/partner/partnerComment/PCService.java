package com.example.sports.partner.partnerComment;

import com.example.sports.member.Member;
import com.example.sports.partner.partnerPost.PartnerPost;
import com.example.sports.post.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PCService {
    private final PCRepository pcRepository;

    public PartnerComment getPartnerComment(Long id) {
        Optional<PartnerComment> partnerComment = this.pcRepository.findById(id);
        return partnerComment.get();
    }

    public void create(PartnerPost partnerPost, String content, Member author) {
        PartnerComment partnerComment = new PartnerComment();
        partnerComment.setContent(content);
        partnerComment.setPartnerPost(partnerPost);
        partnerComment.setAuthor(author);
        partnerComment.setCreateDate(LocalDate.now());

        this.pcRepository.save(partnerComment);
    }

    public void delete(PartnerComment partnerComment) {
        this.pcRepository.delete(partnerComment);
    }

    public void modify(PartnerComment partnerComment, String content) {
        partnerComment.setContent(content);
        partnerComment.setModifyDate(LocalDate.now());

        this.pcRepository.save(partnerComment);
    }
}
