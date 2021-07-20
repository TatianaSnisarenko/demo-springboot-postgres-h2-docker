package com.example.demo;

import com.example.demo.entity.Message;
import com.example.demo.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class DemoApplication {
    @Autowired
    private MessageService messageService;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    private void testJpaMethods() {

		/*Message message1 = new Message();
		message1.setTitle("First title");
		message1.setBody("First body");

		Message message2 = new Message();
		message2.setTitle("Second title");
		message2.setBody("Second body");

		Message message3 = new Message();
		message3.setTitle("Third title");
		message3.setBody("Third body");

		Message message4 = new Message();
		message4.setTitle("Fourth title");
		message4.setBody("Fourth body");

		Message createdMessage1 = messageService.createMessage(message1);
		Message createdMessage2 = messageService.createMessage(message2);
		Message createdMessage3 = messageService.createMessage(message3);
		Message createdMessage4 = messageService.createMessage(message4);

		System.out.println();
		messageService.findAll().forEach(System.out::println);
		System.out.println();
		System.out.println("Message with id=1: " + messageService.findById(1L));
		System.out.println();
		messageService.delete(message4);

		Message newMessage1 = new Message();
		newMessage1.setTitle("New First title");
		newMessage1.setBody("New First body");

		messageService.update(createdMessage1.getId(), newMessage1);
		System.out.println();
		messageService.findAll().forEach(System.out::println);*/
    }
}
