package com.visiongate;

import com.visiongate.entity.GatePassEntity;
import com.visiongate.repository.GatePassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class GatePassController {

    @Autowired
    private GatePassRepository gatePassRepository;

    @PostMapping("/create-pass")
    public String createPass(@RequestParam String visitorName,
                             @RequestParam String purpose) {

        // Generate unique pass ID
        long passCount = gatePassRepository.count();
        String passId = "PASS" + (passCount + 1);

        GatePassEntity pass = new GatePassEntity(
                passId,
                visitorName,
                purpose,
                "ACTIVE"
        );

        gatePassRepository.save(pass);

        System.out.println("Pass created: " + passId + " for " + visitorName);
        return "redirect:/create-pass.html?success=true";
    }

    @GetMapping("/verify-pass")
    public String verifyPass(@RequestParam String passId) {

        GatePassEntity pass = gatePassRepository.findByPassId(passId);

        if (pass != null && "ACTIVE".equals(pass.getStatus())) {
            String result = "Valid Pass - Visitor: " + pass.getVisitorName() + ", Purpose: " + pass.getPurpose();
            System.out.println("Pass verified: " + result);
            return "redirect:/verify-pass.html?result=" + java.net.URLEncoder.encode(result, java.nio.charset.StandardCharsets.UTF_8);
        }

        System.out.println("Invalid pass attempt: " + passId);
        return "redirect:/verify-pass.html?result=Invalid Pass - No matching pass found";
    }
}