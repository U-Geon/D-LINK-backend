//package com.alpha.DLINK.setting.S3;
//
//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.model.ObjectMetadata;
//import com.amazonaws.services.s3.model.PutObjectRequest;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.net.URL;
//
///**
// * s3 파일 업로드 예시
// */
//
//@RestController
//@RequiredArgsConstructor
//public class FileUploadController {
//
//    private final AmazonS3 amazonS3;
//
//    @Value("${cloud.aws.s3.bucket}")
//    private String bucket;
//
//    @PostMapping
//    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
//        try {
//            String fileName = file.getOriginalFilename();
//            String fileUrl = "https://" + bucket + "/post_image/" + fileName;
//            ObjectMetadata metadata = new ObjectMetadata();
//            metadata.setContentType(file.getContentType());
//            metadata.setContentLength(file.getSize());
//
//            PutObjectRequest request = new PutObjectRequest(bucket, "post_image/" + fileName, file.getInputStream(), metadata);
//            amazonS3.putObject(request);
//            String url = amazonS3.getUrl(bucket, "post_image/" + fileName).toString();
//
//            return ResponseEntity.ok(url);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
//}
