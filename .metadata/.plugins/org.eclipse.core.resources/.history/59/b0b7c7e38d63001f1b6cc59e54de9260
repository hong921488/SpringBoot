package gov.vghtc.ebedsidecardspring.patient.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import gov.vghtc.ebedsidecardspring.patient.service.NurseBoardNativeService;

@Controller
@RequestMapping(value = "/api")
@ResponseBody
public class NurseBoardNativeController {

    @Autowired
    private NurseBoardNativeService NurseBoardNativeService;



    @GetMapping("/getHcaseno")
    public ResponseEntity<List<Map<String, Object>>> getHcaseno(@RequestParam String hhisnum) {
        return ResponseEntity.ok(NurseBoardNativeService.getHcaseno(hhisnum));
    }
    @GetMapping("/getPatientData")
    public ResponseEntity<Map<String, Object>> getPatientData(@RequestParam String hcaseno) {
        Map<String, Object> response = new HashMap<>();

        response.put("ewsScore", NurseBoardNativeService.getEwsScore(hcaseno));
        response.put("bedInfo", NurseBoardNativeService.getBedInfo(hcaseno));
        response.put("nursingNoteData", NurseBoardNativeService.getNursingNoteData(hcaseno));
        response.put("fallRisk", NurseBoardNativeService.getFallRisk(hcaseno));
        response.put("nurePaperConfig", NurseBoardNativeService.getNurePaperConfig(hcaseno));
        response.put("nursShiftTube", NurseBoardNativeService.getNursShiftTube(hcaseno));
        response.put("patAdmDoctor", NurseBoardNativeService.getPatAdmDoctor(hcaseno));
        response.put("dnrallCount", NurseBoardNativeService.getDnrAllCount(hcaseno));
        response.put("nursClassDetail", NurseBoardNativeService.getNursClassDetail(hcaseno));
        response.put("epaperwifi", NurseBoardNativeService.getEpaperwifi(hcaseno));

        return ResponseEntity.ok(response);
    }
    
}