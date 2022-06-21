package com.gabup.gabup.controller;

import com.gabup.gabup.model.Attachment;
import com.gabup.gabup.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AdminController {

    @Autowired
    AttachmentService attachmentService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        List<Attachment> attachments = attachmentService.findAll();
        model.addAttribute("attachments", attachments);
        return "dashboard.html";
    }

    @GetMapping("/")
    public String home() {
        return ("redirect:/dashboard");
    }
}
