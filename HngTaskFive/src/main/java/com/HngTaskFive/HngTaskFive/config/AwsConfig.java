package com.HngTaskFive.HngTaskFive.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsConfig {

//    @Value("${cloud.aws.credentials.access.key}")
//    private String accessKey;
//    @Value("${cloud.aws.credentials.secret.key}")
//    private String secretKey;
//    @Value("${cloud.aws.region.static}")
//    private String region;
    String accesskey="AKIAZMPD2F7Q7IM6SHDS";
    String secretkey="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";

    String region="eu-west-2";



   @Bean
    public AmazonS3 s3Client(){
        AWSCredentials credentials = new BasicAWSCredentials(accesskey,secretkey);
        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials)).withRegion(region).build();

    }
}
