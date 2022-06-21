package com.gabup.gabup.service;

import com.gabup.gabup.dao.AttachmentRepository;
import com.gabup.gabup.model.Attachment;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AttachmentService{

    private AttachmentRepository attachmentRepository;

    public AttachmentService(AttachmentRepository attachmentRepository) {
        this.attachmentRepository = attachmentRepository;
    }

    public Attachment saveAttachment(MultipartFile file, String remoteIpAddress) throws Exception {
       String fileName = StringUtils.cleanPath(file.getOriginalFilename());
       try {
            if(fileName.contains("..")) {
                throw  new Exception("Filename contains invalid path sequence"
                + fileName);
            }

            Attachment attachment
                    = new Attachment(fileName,
                    file.getContentType(),
                    file.getBytes(),
                     remoteIpAddress
                    ,LocalDateTime.now()
                    );
            return attachmentRepository.save(attachment);

       } catch (Exception e) {
            throw new Exception("Could not save File: " + fileName);
       }
    }

    public Attachment getAttachment(String fileId) throws Exception {
        return attachmentRepository
                .findById(fileId)
                .orElseThrow(
                        () -> new Exception("File not found with Id: " + fileId));
    }


    public List<Attachment> findAll() {
       return attachmentRepository.findAll();
    }
}
