package com.example.demo.controller;

import com.example.demo.entity.Message;
import com.example.demo.service.MessageService;
import java.util.NoSuchElementException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Slf4j
@Controller
@AllArgsConstructor
@RequestMapping("/")
public class MainController {

    private final MessageService messageService;

    @GetMapping()
    public String index() {
        log.info("index .");
        return "index";
    }

    @GetMapping(path = "messages")
    public String viewListOfMessages(Model model) {
        log.info("viewListOfMessages .");
        model.addAttribute("messages", messageService.findAll());
        return "messages";
    }

    @GetMapping(path = "messages/form/add")
    public String showAddForm() throws Exception {
        log.debug("showAddForm .");
        return "addForm";
    }

    @PostMapping(path = "messages")
    public RedirectView addNewMessage(@ModelAttribute("message") Message message) {
        log.debug("addNewMessage .");
        messageService.createOrUpdateMessage(message);
        return new RedirectView("/messages");
    }

    @GetMapping(path = "messages/form/update")
    public String showUpdateForm(@RequestParam(name = "id") Integer id, Model model) {
        log.info("showUpdateForm .");
        try {
            model.addAttribute(messageService.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("No such index in database")));
        } catch (Throwable throwable) {
            log.error("showUpdateForm . ", throwable.getMessage());
        }
        return "updateForm";
    }

    @GetMapping(path = "messages/delete")
    public RedirectView deleteMessage(@RequestParam(name = "id") Integer id) {
        log.info("deleteMessage .");
        messageService.deleteById(id);
        return new RedirectView("/messages");
    }

    @ModelAttribute("message")
    public Message defaultMessage() {
        return new Message();
    }

}
