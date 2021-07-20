package com.example.demo.service;

import com.example.demo.entity.Message;
import com.example.demo.exception.MessageDataBaseException;
import com.example.demo.repository.MessageRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MessageService {

    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message createOrUpdateMessage(Message message) {
        try {
            return messageRepository.save(message);
        } catch (Exception e) {
            log.error(String.format("createMessage. exception saving message title=%s, body=%s", message.getTitle(),
                    message.getBody()), e);
            throw new MessageDataBaseException(
                    String.format("Message wasn't saved into database: title=%s, body=%s", message.getTitle(),
                            message.getBody()));
        }
    }

    public List<Message> findAll() {
        return messageRepository.findAllSorted();
    }

    public Optional findById(Integer id) {
        return messageRepository.findById(id);
    }

    public void deleteById(Integer id) {
        try {
            messageRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new NoSuchElementException("No such index in database");
        }
    }

    public void delete(Message message) {
        messageRepository.delete(message);
    }
}
