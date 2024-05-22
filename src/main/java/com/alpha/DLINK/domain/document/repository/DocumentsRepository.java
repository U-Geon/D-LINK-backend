package com.alpha.DLINK.domain.document.repository;

import com.alpha.DLINK.domain.document.domain.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentsRepository extends JpaRepository<Document, Long> {

}
