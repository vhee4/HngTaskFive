package com.HngTaskFive.HngTaskFive.service;

import com.HngTaskFive.HngTaskFive.data.dto.response.VideoResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface VideoService {
    VideoResponse uploadFileInChunks(MultipartFile multipartFile) throws IOException;

    VideoResponse getByFileName(String fileName);

    List<VideoResponse> getAllVideos();

    VideoResponse getVideoById(Long id);
}
