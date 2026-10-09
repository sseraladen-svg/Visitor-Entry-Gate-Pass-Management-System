package com.visiongate;

import com.visiongate.entity.EntryLogEntity;
import com.visiongate.entity.GatePassEntity;
import com.visiongate.repository.EntryLogRepository;
import com.visiongate.repository.GatePassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
public class EntryLogController {

    @Autowired
    private EntryLogRepository entryLogRepository;

    @Autowired
    private GatePassRepository gatePassRepository;

    @PostMapping("/record-entry")
    public String recordEntry(@RequestParam String passId,
                              @RequestParam(required = false) String notes) {

        System.out.println("Recording entry for pass: " + passId);

        try {
            var passOpt = gatePassRepository.findByPassId(passId);
            if (passOpt.isPresent()) {
                GatePassEntity pass = passOpt.get();
                // Update pass status
                pass.setStatus("INSIDE");
                pass.setEntryTime(LocalDateTime.now());
                gatePassRepository.save(pass);

                // Create entry log
                EntryLogEntity log = new EntryLogEntity();
                log.setPassId(passId);
                log.setVisitorName(pass.getVisitorName());
                log.setAction("ENTRY");
                log.setNotes(notes);
                log.setTimestamp(LocalDateTime.now());
                entryLogRepository.save(log);

                System.out.println("Entry recorded successfully for: " + passId);
                return "redirect:/entry-recording.html?success=true";
            }

            System.out.println("Pass not found: " + passId);
            return "redirect:/entry-recording.html?error=Pass not found";
        } catch (Exception e) {
            System.out.println("Entry recording failed: " + e.getMessage());
            return "redirect:/entry-recording.html?error=Recording failed";
        }
    }

    @PostMapping("/record-exit")
    public String recordExit(@RequestParam String passId,
                             @RequestParam(required = false) String notes) {

        System.out.println("Recording exit for pass: " + passId);

        try {
            var passOpt = gatePassRepository.findByPassId(passId);
            if (passOpt.isPresent()) {
                GatePassEntity pass = passOpt.get();
                // Update pass status
                pass.setStatus("EXITED");
                pass.setExitTime(LocalDateTime.now());
                gatePassRepository.save(pass);

                // Create exit log
                EntryLogEntity log = new EntryLogEntity();
                log.setPassId(passId);
                log.setVisitorName(pass.getVisitorName());
                log.setAction("EXIT");
                log.setNotes(notes);
                log.setTimestamp(LocalDateTime.now());
                entryLogRepository.save(log);

                System.out.println("Exit recorded successfully for: " + passId);
                return "redirect:/exit-recording.html?success=true";
            }

            System.out.println("Pass not found: " + passId);
            return "redirect:/exit-recording.html?error=Pass not found";
        } catch (Exception e) {
            System.out.println("Exit recording failed: " + e.getMessage());
            return "redirect:/exit-recording.html?error=Recording failed";
        }
    }
}
