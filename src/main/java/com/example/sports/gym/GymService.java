package com.example.sports.gym;

import com.example.sports.post.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GymService {
    private final GymRepository gymRepository;
    public Page<Gym> getList(int page, String keyword) {
        Pageable pageable = PageRequest.of(page, 10);
        return this.gymRepository.findAllByKeyword(keyword, pageable);
    }

    public Gym getGym(Long id) {
        Optional<Gym> gym = this.gymRepository.findById(id);
        return gym.get();
    }
}
