package com.alpha.DLINK.domain.file;

import com.alpha.DLINK.domain.file.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FileRepository extends JpaRepository<File, Long> {

    List<File> findByPostId(Long post_id);
}