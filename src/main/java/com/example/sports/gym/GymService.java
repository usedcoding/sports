package com.example.sports.gym;

import com.example.sports.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GymService {
    private final GymRepository gymRepository;

    @Value("${custom.genFileDirPath}")
    private String genFileDirPath;

    public Page<Gym> getList(int page, String keyword) {
        Pageable pageable = PageRequest.of(page, 10);
        return this.gymRepository.findAllByKeyword(keyword, pageable);
    }

    public Gym getGym(Long id) {
        Optional<Gym> gym = this.gymRepository.findById(id);
        return gym.get();
    }

    public void create(String title, String phoneNUm, String time, String price, String address, Member author, MultipartFile thumbnail) {
        Gym gym = new Gym();
        gym.setTitle(title);
        gym.setPhoneNum(phoneNUm);
        gym.setTime(time);
        gym.setPrice(price);
        gym.setAddress(address);
        gym.setAuthor(author);
        String thumbnailRelPath = UUID.randomUUID() + ".jpg";
        File thumbnailFile = new File(genFileDirPath + "/" + thumbnailRelPath);

        try {
            thumbnail.transferTo(thumbnailFile);
        } catch (IOException e) {
            throw  new RuntimeException(e);
        }

        gym.setCreateDate(LocalDateTime.now());
        gym.setThumbnailImg(thumbnailRelPath);

        this.gymRepository.save(gym);
    }

    public void modify(Gym gym, String title, String phoneNUm, String time, String price, String address) {
        gym.setTitle(title);
        gym.setPhoneNum(phoneNUm);
        gym.setTime(time);
        gym.setPrice(price);
        gym.setAddress(address);
        gym.setModifyDate(LocalDateTime.now());

        this.gymRepository.save(gym);
    }

    public void delete(Gym gym) {
        this.gymRepository.delete(gym);
    }
}
