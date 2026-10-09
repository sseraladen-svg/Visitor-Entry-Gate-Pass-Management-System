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
    public String createPass(@RequestParam Long visitorId,
                             @RequestParam String visitorName,
                             @RequestParam String visitorEmail,
                             @RequestParam String visitorPhone,
                             @RequestParam String hostName,
                             @RequestParam String hostEmail,
                             @RequestParam String purpose,
                             @RequestParam String visitDate,
                             @RequestParam String visitTime) {

        GatePassEntity pass = gatePassService.createPass(visitorId, visitorName, visitorEmail, 
                                                          visitorPhone, hostName, hostEmail, 
                                                          purpose, visitDate, visitTime);
        System.out.println("Pass created: " + pass.getPassId() + " for " + visitorName);
        
        // Redirect with all pass details for display
        return "redirect:/visitor-dashboard.html?success=true&passId=" + pass.getPassId();
    }

    @GetMapping("/verify-pass")
    public String verifyPass(@RequestParam String passId) {

        GatePassEntity pass = gatePassService.verifyPass(passId);

        if (pass != null && ("APPROVED".equals(pass.getStatus()) || "INSIDE".equals(pass.getStatus()))) {
            String result = "Valid Pass - Visitor: " + pass.getVisitorName() + ", Purpose: " + pass.getPurpose();
            System.out.println("Pass verified: " + result);
            return "redirect:/verify-pass.html?result=" + java.net.URLEncoder.encode(result, java.nio.charset.StandardCharsets.UTF_8);
        }

        System.out.println("Invalid pass attempt: " + passId);
        return "redirect:/verify-pass.html?result=Invalid Pass - No matching pass found";
    }
}