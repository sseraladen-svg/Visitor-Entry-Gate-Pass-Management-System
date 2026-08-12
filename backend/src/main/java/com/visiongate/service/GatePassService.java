package com.visiongate.service;

import com.visiongate.entity.GatePassEntity;
import com.visiongate.repository.GatePassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GatePassService {
    
    @Autowired
    private GatePassRepository gatePassRepository;
    
    public GatePassEntity createPass(String visitorName, String purpose) {
        long passCount = gatePassRepository.count();
        String passId = "PASS" + (passCount + 1);
        
        GatePassEntity pass = new GatePassEntity(
                passId,
                visitorName,
                purpose,
                "ACTIVE"
        );
        
        return gatePassRepository.save(pass);
    }
    
    public GatePassEntity verifyPass(String passId) {
        return gatePassRepository.findByPassId(passId);
    }
    
    public GatePassEntity findByPassId(String passId) {
        return gatePassRepository.findByPassId(passId);
    }
}