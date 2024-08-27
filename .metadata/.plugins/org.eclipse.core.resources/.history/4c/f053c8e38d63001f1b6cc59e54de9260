package gov.vghtc.ebedsidecardspring.patient.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.vghtc.ebedsidecardspring.patient.repository.cpoe.ImageUploadRepository;

@Service
public class ImageUploadService {

    @Autowired
    private ImageUploadRepository imageUploadRepository;

    public void saveImageUrl(String bgImageUrl, String labelId) {
        imageUploadRepository.updateEpaperStatus(bgImageUrl, labelId);
    }
}
