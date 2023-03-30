package com.anshuman.fileManagement.controller;

import com.anshuman.fileManagement.entity.Attachment;
import com.anshuman.fileManagement.model.ResponseData;
import com.anshuman.fileManagement.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class AttachmentController {

    /*
    This annotation base dependency injection

    @Autowired
    private AttachmentService attachmentService;
    */

    //This is constructor based dependency injection
    private AttachmentService attachmentService;

    public AttachmentController(AttachmentService attachmentService){
        this.attachmentService = attachmentService;
    }

    /*
    Note:
    @RequestBody is used when we are passing value from the body in post method.
    @RequestParam() is used when we are passing value as form data. Used with post method.
    @PathVariable() is used when we have to get a record, and we pass the value in the url. Used with get method.
     */

    //upload file
    @PostMapping("/upload")
    public ResponseData uploadFile(@RequestParam("file") MultipartFile file) throws Exception{
        Attachment attachment = attachmentService.saveAttachment(file);
        String downloadURL = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/download/")
                    .path(attachment.getId())
                    .toUriString();

        return new ResponseData(attachment.getFileName(),
                downloadURL, file.getContentType(),
                file.getSize());
    }

    //download file
    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("fileId") String fileId) throws Exception{
        Attachment attachment = attachmentService.getAttachment(fileId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(attachment.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + attachment.getFileName() + "\"")
                                .body(new ByteArrayResource(attachment.getData()));
    }
}
