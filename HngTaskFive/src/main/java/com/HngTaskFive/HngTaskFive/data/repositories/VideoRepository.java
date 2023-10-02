package com.HngTaskFive.HngTaskFive.data.repositories;

import com.HngTaskFive.HngTaskFive.data.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public interface VideoRepository extends JpaRepository<Video, String> {
    Optional<Video> findById(Long id);
    Optional<Video> findByFileName(String fileName);
}

