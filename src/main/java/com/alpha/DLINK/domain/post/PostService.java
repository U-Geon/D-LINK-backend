package com.alpha.DLINK.domain.post;


import com.alpha.DLINK.domain.file.File;
import com.alpha.DLINK.domain.member.entity.Member;
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
@Transactional
public class PostService {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final PostRepository postRepository;

    // 게시글 생성 로직
    public void create(String title,
                       String content,
                       Member member,
                       List<MultipartFile> files) {

        try {
            Post post = Post.create(title, content);
            post.setMember(member);

            for (MultipartFile file : files) {
                String fileName = file.getOriginalFilename();
                String fileUrl = "https://" +
                        bucket + "/test/" + fileName;

                ObjectMetadata metadata = new ObjectMetadata();
                metadata.setContentType(file.getContentType());
                metadata.setContentLength(file.getSize());

                amazonS3.putObject(bucket, fileName, file.getInputStream(), metadata);

                File fileAttr = File.create(fileUrl);
                fileAttr.setPost(post);
            }
            postRepository.save(post);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 사용자별 게시글 조회
    @Transactional(readOnly = true)
    public List<Post> getPostsByMemberId(Long memberId) {
        return postRepository.findByMemberId(memberId);
    }

    // 게시글 업데이트
    public void update(Post post, String title, String content) {
        post.setTitle(title);
        post.setContent(content);
    }

    // 게시글 삭제
    public void delete(Post post) {
        postRepository.delete(post);
    }
}