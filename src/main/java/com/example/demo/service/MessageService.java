package com.example.demo.service;

import com.example.demo.entity.Message;
import com.example.demo.exception.NoSuchIndexInDataBaseException;
import com.example.demo.exception.NullFieldsException;
import com.example.demo.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {
    @Autowired
    private final MessageRepository messageRepository;

    public Message createMessage(Message message) {
        try {
            return messageRepository.save(message);
        } catch (ConstraintViolationException e) {
            throw new NullFieldsException("One or more fields are null");
        }
    }

    public List<Message> findAll() {
        return messageRepository.findAll();
    }

    public Message findById(Long id) {
        return messageRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        try {
            messageRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new NoSuchIndexInDataBaseException("No such index in database");
        }
    }

    public void delete(Message message) {
        messageRepository.delete(message);
    }

    public Message update(Long id, Message newMessage) {
        Message messageToUpdate = findById(id);
        if (messageToUpdate != null) {
            messageToUpdate.setId(id);
            messageToUpdate.setTitle(newMessage.getTitle());
            messageToUpdate.setBody(newMessage.getBody());
            try {
                return messageRepository.save(messageToUpdate);
            } catch (Exception e) {
                throw new NullFieldsException("One or more fields are null");
            }
        }
        return findById(id);
    }
}