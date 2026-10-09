package com.visiongate.service;

import com.visiongate.entity.GatePassEntity;
import com.visiongate.repository.GatePassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Service
public class GatePassService {
    
    @Autowired
    private GatePassRepository gatePassRepository;
    
    public GatePassEntity createPass(Long visitorId, String visitorName, String visitorEmail, 
                                     String visitorPhone, String hostName, String hostEmail, 
                                     String purpose, String visitDate, String visitTime) {
        long passCount = gatePassRepository.count();
        String passId = "PASS" + (passCount + 1);
        
        GatePassEntity pass = new GatePassEntity(
                passId,
                visitorId,
                visitorName,
                visitorEmail,
                visitorPhone,
                hostName,
                hostEmail,
                purpose,
                visitDate,
                visitTime
        );
        
        return gatePassRepository.save(pass);
    }
    
    public GatePassEntity verifyPass(String passId) {
        Optional<GatePassEntity> pass = gatePassRepository.findByPassId(passId);
        return pass.orElse(null);
    }
    
    public GatePassEntity findByPassId(String passId) {
        Optional<GatePassEntity> pass = gatePassRepository.findByPassId(passId);
        return pass.orElse(null);
    }
}