package com.gabup.gabup.controller;

import com.gabup.gabup.model.Attachment;
import com.gabup.gabup.model.ResponseData;
import com.gabup.gabup.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;


@RestController
public class AttachmentController {

    @Autowired
    private ResourceLoader resourceLoader;

    private AttachmentService attachmentService;

    public AttachmentController(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    @PostMapping("/upload")
    public ResponseEntity<Object> uploadFile(@RequestParam("file")MultipartFile file, HttpServletRequest request) {

        Attachment attachment = null;
        String downloadURl = "";
        try {
            attachment = attachmentService.saveAttachment(file,  request.getRemoteAddr());

            downloadURl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/download/")
                    .path(attachment.getId())
                    .toUriString();

            return ResponseEntity.ok(new ResponseData(attachment.getFileName(),
                    downloadURl,
                    file.getContentType(),
                    file.getSize()));
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/script/{fileId}")
    public ResponseEntity<Resource> downloadMainScript(@PathVariable String fileId) {
        if(!fileId.equalsIgnoreCase("main") && !fileId.equalsIgnoreCase("scripts"))
            return ResponseEntity.notFound().build();

        try {
            String path = "/static/" + fileId.toLowerCase() + (fileId.equalsIgnoreCase("main") ? ".ps1" : ".zip");

            Resource resource = resourceLoader.getResource("classpath:"+path);

            return  ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("application/octet-stream"))
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + resource.getFilename()
                                    + "\"")
                    .contentLength(resource.contentLength())
                    .body(resource);
        }catch (Exception ex){
            ex.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) {
        Attachment attachment = null;
        try {
            attachment = attachmentService.getAttachment(fileId);
            return  ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(attachment.getFileType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + attachment.getId()+"-"+ attachment.getFileName())
                    .body(new ByteArrayResource(attachment.getData()));
        }catch (Exception ex){
            return ResponseEntity.notFound().build();
        }
    }



}
