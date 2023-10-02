package com.HngTaskFive.HngTaskFive.data.dto.response;

import com.HngTaskFive.HngTaskFive.data.entity.AuditModel;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Builder @Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class VideoResponse {
    private String fileName;
    private String fileId;
    private Long fileSize;
    private String fileUrl;
    private Timestamp createdAt;

}
