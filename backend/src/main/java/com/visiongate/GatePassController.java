package com.visiongate;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
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

        return "Pass created successfully. Pass ID: " + passId;
    }

    @GetMapping("/verify-pass")
    public String verifyPass(@RequestParam String passId) {

        for (GatePass pass : passes) {

            if (pass.getPassId().equals(passId)) {
                return "Valid Pass - Visitor: " + pass.getVisitorName();
            }
        }

        return "Invalid Pass";
    }
}