package com.visiongate;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class GatePassController {

    private List<GatePass> passes = new ArrayList<>();

    @PostMapping("/create-pass")
    public String createPass(@RequestParam String visitorName,
                             @RequestParam String purpose) {

        String passId = "PASS" + (passes.size() + 1);

        GatePass pass = new GatePass(
                passId,
                visitorName,
                purpose,
                "ACTIVE"
        );

        passes.add(pass);

        System.out.println("Pass created: " + passId + " for " + visitorName);
        return "redirect:/create-pass.html?success=true";
    }

    @GetMapping("/verify-pass")
    public String verifyPass(@RequestParam String passId) {

        for (GatePass pass : passes) {

            if (pass.getPassId().equals(passId)) {
                String result = "Valid Pass - Visitor: " + pass.getVisitorName() + ", Purpose: " + pass.getPurpose();
                System.out.println("Pass verified: " + result);
                return "redirect:/verify-pass.html?result=" + java.net.URLEncoder.encode(result, java.nio.charset.StandardCharsets.UTF_8);
            }
        }

        System.out.println("Invalid pass attempt: " + passId);
        return "redirect:/verify-pass.html?result=Invalid Pass - No matching pass found";
    }
}