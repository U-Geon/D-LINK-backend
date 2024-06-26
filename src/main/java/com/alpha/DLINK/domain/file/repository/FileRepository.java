package com.alpha.DLINK.domain.file.repository;

import com.alpha.DLINK.domain.file.domain.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<File, Long> {

    List<File> findByPostId(Long post_id);
}