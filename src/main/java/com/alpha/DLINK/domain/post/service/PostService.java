package com.alpha.DLINK.domain.post.service;


import com.alpha.DLINK.domain.file.domain.File;
import com.alpha.DLINK.domain.likeHistory.domain.LikeHistory;
import com.alpha.DLINK.domain.likeHistory.repository.LikeHistoryRepository;
import com.alpha.DLINK.domain.member.entity.Member;
import com.alpha.DLINK.domain.post.domain.Post;
import com.alpha.DLINK.domain.post.repository.PostRepository;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final PostRepository postRepository;
    private final LikeHistoryRepository likeHistoryRepository;

    // 게시글 생성 로직
    @Transactional
    public void create(String title,
                       String content,
                       Member member,
                       List<MultipartFile> files) {

        try {
            Post post = Post.create(title, content);

            for (MultipartFile file : files) {
                String fileName = file.getOriginalFilename();
                String fileUrl = "https://" +
                        bucket + "/test/" + fileName;

                ObjectMetadata metadata = new ObjectMetadata();
                metadata.setContentType(file.getContentType());
                metadata.setContentLength(file.getSize());

                amazonS3.putObject(bucket, fileName, file.getInputStream(), metadata);

                File fileAttr = File.create(fileUrl, post);
            }

            LikeHistory likeHistory = LikeHistory.create(member, post);

            likeHistoryRepository.save(likeHistory);
            postRepository.save(post);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 사용자별 게시글 조회

    // 게시글 업데이트
    @Transactional
    public void update(Post post, String title, String content) {
        post.setTitle(title);
        post.setContent(content);
    }

    // 게시글 삭제
    @Transactional
    public void delete(Post post) {



        postRepository.delete(post);
    }
}