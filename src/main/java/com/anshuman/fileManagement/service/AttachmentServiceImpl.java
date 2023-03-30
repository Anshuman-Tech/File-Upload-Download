package com.anshuman.fileManagement.service;

import com.anshuman.fileManagement.entity.Attachment;
import com.anshuman.fileManagement.respository.AttachmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AttachmentServiceImpl implements AttachmentService{

    //constructor-based dependency injection
    private AttachmentRepository attachmentRepository;

    public AttachmentServiceImpl(AttachmentRepository attachmentRepository){
        this.attachmentRepository = attachmentRepository;
    }

    @Override
    public Attachment saveAttachment(MultipartFile file) throws Exception{
        //get the file name of the passed file
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try{
            // checks for any invalid character in the file name
            if(fileName.contains("..")){
                throw new Exception("FileName contains invalid path sequence");
            }
            Attachment attachment = new Attachment(fileName,file.getContentType(),file.getBytes());
            return attachmentRepository.save(attachment);
        }catch(Exception e){
            throw new Exception("Could not save File: " + fileName);
        }
    }

    @Override
    public Attachment getAttachment(String fileId) throws Exception{
        return attachmentRepository.findById(fileId)
                .orElseThrow(()->new Exception("File not found with fileId" + fileId));
    }
}
