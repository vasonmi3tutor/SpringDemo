package com.vason.springdemo.record;

import com.vason.springdemo.account.User;
import com.vason.springdemo.account.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class RecordController {
    private final UserRepository userRepository;
    private final RecordRepository recordRepository;

    protected Long currentRecordId;

    public RecordController(UserRepository userRepository, RecordRepository recordRepository) {
        this.userRepository = userRepository;
        this.recordRepository = recordRepository;
    }

    @RequestMapping("/record")
    public String showContent(Model model, Principal principal){
        if(principal != null){
            // Get UserID
            model.addAttribute("username", principal.getName());
            User user = userRepository.findByUsername(principal.getName());
            // Select all query that has userID
            List<Record> records = recordRepository.findAllByUserIdSql(user.getId());
            model.addAttribute("records", records);
        }
        return "record.html";
    }

    @RequestMapping("/record/add")
    public String showAddContent(Model model, Principal principal){
        model.addAttribute("record", new Record());
        return "record-add.html";
    }

    @PostMapping("/record/add")
    public String doAddContent(@ModelAttribute("record") Record record, Principal principal){
        // Save current timestamp
        record.setTimestamp(LocalDateTime.now());
        // Get and save user ID
        User user = userRepository.findByUsername(principal.getName());
        if(user != null){
            record.setUser_id(user.getId());
        }
        recordRepository.save(record);
        return "redirect:/record/add?success";
    }

    @PostMapping("/record/edit/prepare")
    public String prepareEditContent(@RequestParam("id") Long recordId, RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("record_id", recordId);
        return "redirect:/record/edit";
    }

    @RequestMapping("/record/edit")
    public String showEditContent(Model model){
        currentRecordId = (Long)model.getAttribute("record_id");
        model.addAttribute("record", new Record());
        return "record-edit.html";
    }

    @PostMapping("record/edit")
    public String doEditContent(@ModelAttribute("record") Record record,Model model){
        Long recordId = (Long)model.getAttribute("record_id");
        int affectedRows = recordRepository.updateByRecordIdSql(currentRecordId, record.getValue_change(), record.getRemark());
        return "redirect:/record";
    }

    @PostMapping("/record/delete")
    public String doDeleteContent(@RequestParam("id") Long recordId, RedirectAttributes redirectAttributes){
        recordRepository.deleteByRecordIdSql(recordId);
        return "redirect:/record";
    }

}
