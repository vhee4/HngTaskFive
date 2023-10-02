package com.HngTaskFive.HngTaskFive.service;

import com.HngTaskFive.HngTaskFive.data.dto.response.VideoResponse;
import com.HngTaskFive.HngTaskFive.data.entity.Video;
import com.HngTaskFive.HngTaskFive.data.repositories.VideoRepository;
import com.HngTaskFive.HngTaskFive.exceptions.NotFoundException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class VideoServiceImpl implements VideoService {
    @Autowired
    private VideoRepository videoRepository;
    @Autowired
    private AmazonS3 s3Client;

    //    @Value("${application.bucket.name}")
//    private String bucketName;
    String bucketName = "hngvideostorage";

//    @Override
//    public VideoResponse uploadFile(MultipartFile multipartFile) throws IOException {
//        File fileObj = convertMultiPartFileToFile(multipartFile);
//        String fileName = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
//        PutObjectResult s3Object = s3Client.putObject(new PutObjectRequest(bucketName, fileName, fileObj));
//        String objectUrl = s3Client.getUrl(bucketName, fileName).toString();
//        fileObj.delete();
//        Video video = Video.builder()
//                .fileSize(s3Object.getMetadata().getContentLength())
//                .fileUrl(objectUrl)
//                .fileName(fileName)
//                .build();
//        videoRepository.save(video);
//        return mapVideoToVideoResponse(video);
//    }

    public VideoResponse uploadFileInChunks(MultipartFile multipartFile) throws IOException {
        String objectKey = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
        InitiateMultipartUploadRequest initRequest = new InitiateMultipartUploadRequest(bucketName, objectKey);
        InitiateMultipartUploadResult initResponse = s3Client.initiateMultipartUpload(initRequest);
        String uploadId = initResponse.getUploadId();
        String objectUrl = s3Client.getUrl(bucketName, objectKey).toString();

        long partSize = 5 * 1024 * 1024;

        Video video = null;
        try (InputStream input = multipartFile.getInputStream()) {
            byte[] buffer = new byte[(int) partSize];
            int bytesRead;
            int partNumber = 1;

            while ((bytesRead = input.read(buffer)) > 0) {
                UploadPartRequest uploadRequest = new UploadPartRequest()
                        .withBucketName(bucketName)
                        .withKey(objectKey)
                        .withUploadId(uploadId)
                        .withPartNumber(partNumber)
                        .withPartSize(bytesRead)
                        .withInputStream(new ByteArrayInputStream(buffer));

                UploadPartResult uploadPartResponse = s3Client.uploadPart(uploadRequest);

                String eTag = uploadPartResponse.getETag();

                partNumber++;

                List<PartETag> partETags = new ArrayList<>();

                CompleteMultipartUploadRequest completeRequest = new CompleteMultipartUploadRequest()
                        .withBucketName(bucketName)
                        .withKey(objectKey)
                        .withUploadId(uploadId)
                        .withPartETags(partETags);

                CompleteMultipartUploadResult completeResponse = s3Client.completeMultipartUpload(completeRequest);

                video = Video.builder()
                        .fileSize(multipartFile.getSize())
                        .fileUrl(objectUrl)
                        .fileName(objectKey)
                        .build();
                videoRepository.save(video);
            }
        }
        assert video != null;
        return mapVideoToVideoResponse(video);
    }

    @Override
    public VideoResponse getVideoById(Long id) {
        Video retrievedVideo = videoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Video not found"));
        return mapVideoToVideoResponse(retrievedVideo);
    }

    @Override
    public List<VideoResponse> getAllVideos() {
        return videoRepository.findAll()
                .stream()
                .map(this::mapVideoToVideoResponse).toList();
    }


    @Override
    public VideoResponse getByFileName(String fileName) {
        Video video = videoRepository.findByFileName(fileName)
                .orElseThrow(() -> new NotFoundException("Video not found"));
        return mapVideoToVideoResponse(video);
    }

    private VideoResponse mapVideoToVideoResponse(Video video) {
        return VideoResponse.builder()
                .fileUrl(video.getFileUrl())
                .fileId(String.valueOf(video.getId()))
                .fileName(video.getFileName())
                .fileSize(video.getFileSize())
                .createdAt(video.getCreatedAt())
                .build();
    }

    private File convertMultiPartFileToFile(MultipartFile file) throws FileNotFoundException {
        File converetedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(converetedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return converetedFile;
    }

}
