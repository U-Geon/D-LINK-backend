package com.alpha.DLINK.service;


import com.alpha.DLINK.entity.File;
import com.alpha.DLINK.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class FileService {

    private final FileRepository fileRepository;

    public File create() {
        File file = new File();

        return file;
    }
}
