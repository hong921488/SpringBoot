package gov.vghtc.ebedsidecardspring.patient.service;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import gov.vghtc.ebedsidecardspring.patient.repository.cpoe.NurseBoardNativeRepository;

@Service
public class NurseBoardNativeService {

    @Autowired
    NurseBoardNativeRepository nativeSqlRepository;

    public List<Map<String, Object>> getHcaseno(String hhisnum) {
        return nativeSqlRepository.getHcaseno(hhisnum);
    }
    
    public List<Map<String, Object>> getEwsScore(String hcaseno) {
        return nativeSqlRepository.getEwsScore(hcaseno);
    }

    public List<Map<String, Object>> getBedInfo(String hcaseno) {
        return nativeSqlRepository.getBedInfo(hcaseno);
    }

    public Map<String, Object> getNursingNoteData(String hcaseno) {
        return nativeSqlRepository.getNursingNoteData(hcaseno);
    }

    

    public String getFallRisk(String hcaseno) {
        return nativeSqlRepository.getFallRisk(hcaseno);
    }

    public Map<String, Object> getNurePaperConfig(String hcaseno) {
        return nativeSqlRepository.getNurePaperConfig(hcaseno);
    }

    public List<Map<String, Object>> getNursShiftTube(String hcaseno) {
        return nativeSqlRepository.getNursShiftTube(hcaseno);
    }

    public Map<String, Object> getPatAdmDoctor(String hcaseno) {
        return nativeSqlRepository.getPatAdmDoctor(hcaseno);
    }
    

    public List<Map<String, Object>> getNursClassDetail(String hcaseno) {
        return nativeSqlRepository.getNursClassDetail(hcaseno);
    }
    
    
    public List<Map<String, Object>>  getDnrAllCount(String hcaseno) {

        return nativeSqlRepository.getDnrAllCount(hcaseno);
    }
   

    
    public List<Map<String, Object>> getEpaperwifi(String hcaseno) {
        return nativeSqlRepository.getEpaperwifi(hcaseno);
    }
}