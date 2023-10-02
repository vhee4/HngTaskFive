package com.HngTaskFive.HngTaskFive.controller;

import com.HngTaskFive.HngTaskFive.data.dto.response.ApiResponse;
import com.HngTaskFive.HngTaskFive.data.dto.response.VideoResponse;
import com.HngTaskFive.HngTaskFive.service.VideoService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/video")
@AllArgsConstructor
public class VideoController {
    private final VideoService videoService;

    @Operation(summary = "Upload a video to the server",
            description = "Returns an ApiResponse Response entity containing successful message of the uploaded video/videos with details")
    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<VideoResponse>> uploadVideo(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        VideoResponse videoResponse = videoService.uploadFileInChunks(multipartFile);
        ApiResponse<VideoResponse> ar = new ApiResponse<>(HttpStatus.CREATED);
        ar.setStatusCode(HttpStatus.CREATED.value());
        ar.setData(videoResponse);
        ar.setMessage("Video successfully uploaded");
        return new ResponseEntity<>(ar, HttpStatus.CREATED);
    }

    @Operation(summary = "Get a video by its unique_id",
            description = "Returns an ApiResponse Response entity containing details of a video")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<VideoResponse>> getVideo(@PathVariable("id") Long id) {
        VideoResponse videoResponse = videoService.getVideoById(id);
        ApiResponse<VideoResponse> ar = new ApiResponse<>(HttpStatus.OK);
        ar.setData(videoResponse);
        ar.setStatusCode(HttpStatus.OK.value());
        ar.setMessage("Video successfully retrieved");
        return new ResponseEntity<>(ar, HttpStatus.OK);
    }

    @Operation(summary = "Get a video by its file name",
            description = "Returns an ApiResponse Response entity containing details of a video")
    @GetMapping("/filename")
    public ResponseEntity<ApiResponse<VideoResponse>> getVideo(@RequestParam("filename") String filename) {
        VideoResponse videoResponse = videoService.getByFileName(filename);
        ApiResponse<VideoResponse> ar = new ApiResponse<>(HttpStatus.OK);
        ar.setData(videoResponse);
        ar.setStatusCode(HttpStatus.OK.value());
        ar.setMessage("Video successfully retrieved");
        return new ResponseEntity<>(ar, HttpStatus.OK);
    }

    @Operation(summary = "Get all videos stored in the database",
            description = "Returns an ApiResponse Response entity containing a list of the videos in the database")
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<VideoResponse>>> getAllVideos() {
        List<VideoResponse> videoResponse = videoService.getAllVideos();
        ApiResponse<List<VideoResponse>> ar = new ApiResponse<>(HttpStatus.OK);
        ar.setStatusCode(HttpStatus.OK.value());
        ar.setData(videoResponse);
        ar.setMessage("Video successfully retrieved");
        return new ResponseEntity<>(ar, HttpStatus.OK);
    }

}
