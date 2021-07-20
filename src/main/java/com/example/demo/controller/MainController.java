package com.example.demo.controller;

import com.example.demo.entity.Message;
import com.example.demo.service.MessageService;
import java.util.Collections;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;


@Controller
@AllArgsConstructor
@RequestMapping("/")
public class MainController {

    private final MessageService messageService;


    public String index() {
        return "index";
    }

    @GetMapping(path = "messages")
    public String viewListOfMessages(Model model) {
        model.addAttribute("messages", messageService.findAll());
        return "messages";
    }

    @GetMapping(path = "new")
    public String showAddForm() {
        return "new";
    }

    @PostMapping(path = "messages")
    public RedirectView addNewMessage(@ModelAttribute("message") Message message) {
        messageService.createMessage(message);
        return new RedirectView("/messages");
    }

   /* @GetMapping(path = "update")
    public String showUpdateForm(@RequestParam(name="id") Integer id, Model model) {
        model.addAttribute(messageService.findById(id));
        return "update";
    }

    @PostMapping(path = "messages")
    public RedirectView updateMessage(@ModelAttribute("message") Message message) {
        messageService.update(message.getId(), message);
        return new RedirectView("/messages");
    }*/

    @DeleteMapping(path = "messages")
    public RedirectView deleteMessage(@RequestParam(name="id") Integer id) {
        messageService.deleteById(id);
        return new RedirectView("/messages");
    }

    @ModelAttribute("message")
    public Message defaultMessage() {
        return new Message();
    }

}
