package com.alpha.DLINK.setting.S3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class S3FileService {

    private final AmazonS3 amazonS3;

    @Value("${s3.bucket}")
    private String bucket;

    public void deletePostImageFile(String dir, String filename) {
        try {
            DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucket,  dir + "/" + filename);
            amazonS3.deleteObject(deleteObjectRequest);

            System.out.println("File deleted successfully.");
        } catch (Exception e) {
            System.err.println("Error deleting file from S3: " + e.getMessage());
        }
    }

    public String createPostImageFile(String dir, MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        String fileUrl = "https://" + bucket + "/" + dir + "/" + fileName;
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());

        amazonS3.putObject(bucket, dir + "/" + fileName, file.getInputStream(), metadata);

        return fileUrl;
    }
}
