package gov.vghtc.ebedsidecardspring.patient.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gov.vghtc.ebedsidecardspring.patient.service.ImageUploadService;

import java.util.Map;

@RestController
@RequestMapping(value = "/api")
public class ImageUploadController {

    @Autowired
    private ImageUploadService imageUploadService;

    @PostMapping("/saveImageUrl")
    public ResponseEntity<String> saveImageUrl(@RequestBody Map<String, String> requestData) {
        String bgImageUrl = requestData.get("bg_image_url");
        String labelId = requestData.get("label_id");
        // 调用service保存图片URL到数据库
        imageUploadService.saveImageUrl(bgImageUrl, labelId);

        return ResponseEntity.ok("Image URLs saved successfully");
    }
}
