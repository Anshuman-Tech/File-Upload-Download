package com.anshuman.fileManagement.service;

import com.anshuman.fileManagement.entity.Attachment;
import org.springframework.web.multipart.MultipartFile;

public interface AttachmentService {

    Attachment saveAttachment(MultipartFile file) throws Exception;

    Attachment getAttachment(String fileId) throws Exception;
}
