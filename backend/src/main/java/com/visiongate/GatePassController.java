package com.visiongate;

import com.visiongate.entity.GatePassEntity;
import com.visiongate.service.GatePassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class GatePassController {

    @Autowired
    private GatePassService gatePassService;

    @PostMapping("/create-pass")
    public String createPass(@RequestParam String visitorName,
                             @RequestParam String purpose) {

        GatePassEntity pass = gatePassService.createPass(visitorName, purpose);
        System.out.println("Pass created: " + pass.getPassId() + " for " + visitorName);
        
        // Redirect with all pass details for display
        return "redirect:/create-pass.html?success=true&passId=" + pass.getPassId() + 
               "&visitorName=" + java.net.URLEncoder.encode(visitorName, java.nio.charset.StandardCharsets.UTF_8) +
               "&purpose=" + java.net.URLEncoder.encode(purpose, java.nio.charset.StandardCharsets.UTF_8) +
               "&status=" + pass.getStatus();
    }

    @GetMapping("/verify-pass")
    public String verifyPass(@RequestParam String passId) {

        GatePassEntity pass = gatePassService.verifyPass(passId);

        if (pass != null && "ACTIVE".equals(pass.getStatus())) {
            String result = "Valid Pass - Visitor: " + pass.getVisitorName() + ", Purpose: " + pass.getPurpose();
            System.out.println("Pass verified: " + result);
            return "redirect:/verify-pass.html?result=" + java.net.URLEncoder.encode(result, java.nio.charset.StandardCharsets.UTF_8);
        }

        System.out.println("Invalid pass attempt: " + passId);
        return "redirect:/verify-pass.html?result=Invalid Pass - No matching pass found";
    }
}