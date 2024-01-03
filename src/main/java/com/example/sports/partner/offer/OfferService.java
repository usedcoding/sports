package com.example.sports.partner.offer;

import com.example.sports.member.Member;
import com.example.sports.partner.partnerPost.PartnerPost;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OfferService {
    private final OfferRepository offerRepository;

    public List<Offer> getList() {
        return this.offerRepository.findAll();
    }

    public Offer getOffer(Long id) {
        Optional<Offer> partnerApplicant = this.offerRepository.findById(id);
        return partnerApplicant.get();
    }

    public void create(PartnerPost partnerPost, String content, Member author) {
        Offer offer = new Offer();
        offer.setContent(content);
        offer.setAuthor(author);
        offer.setPartnerPost(partnerPost);
        offer.setCreateDate(LocalDateTime.now());

        this.offerRepository.save(offer);
    }

    public void delete(Offer offer) {
        this.offerRepository.delete(offer);
    }

}
