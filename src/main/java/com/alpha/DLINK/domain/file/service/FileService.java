package com.alpha.DLINK.domain.file.service;


import com.alpha.DLINK.domain.file.domain.File;
import com.alpha.DLINK.domain.file.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class FileService {

    private final FileRepository fileRepository;

    // 파일 삭제.
    public void delete(File file) {
        fileRepository.delete(file);
    }
}