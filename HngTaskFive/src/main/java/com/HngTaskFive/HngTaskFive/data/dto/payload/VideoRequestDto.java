package com.HngTaskFive.HngTaskFive.data.dto.payload;

import lombok.*;

import java.time.LocalDateTime;

@Setter @Getter @NoArgsConstructor @AllArgsConstructor @Builder
public class VideoRequestDto {
    private String fileName;
}
