package com.serinautoupload.controller;


import com.serinautoupload.service.OEEKitService;
import jakarta.annotation.Resource;
import jakarta.mail.MessagingException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/OEEKit")
@CrossOrigin
public class OEEKitController {

    @Resource
    OEEKitService oeeKitService;

    @Scheduled(fixedRate = 30 * 60 * 1000)
    @PostMapping("/autoUpload")
    public void uploadOEEKit() throws MessagingException {
        oeeKitService.uploadOEEKitA145();
        oeeKitService.uploadOEEKitA155();
    }
}
