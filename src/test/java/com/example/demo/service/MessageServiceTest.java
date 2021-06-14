package com.example.demo.service;

import com.example.demo.DemoApplication;
import com.example.demo.entity.Message;
import com.example.demo.exception.NoSuchIndexInDataBaseException;
import com.example.demo.exception.NullFieldsException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.jdbc.JdbcTestUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = DemoApplication.class
)
class MessageServiceTest {

    @Autowired
    private JdbcTemplate jdbc;

    private static final String CREATE_MESSAGES_SQL_SCRIPT = "scripts/create/messages_create.sql";
    private static final String DROP_MESSAGES_SQL_SCRIPT = "scripts/drop/messages_drop.sql";

    @BeforeEach
    void setUp() {
        try (Connection connection = jdbc.getDataSource().getConnection()) {
            ScriptUtils.executeSqlScript(connection, new ClassPathResource(CREATE_MESSAGES_SQL_SCRIPT));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @AfterEach
    void tearDown() {
        try (Connection connection = jdbc.getDataSource().getConnection()) {
            ScriptUtils.executeSqlScript(connection, new ClassPathResource(DROP_MESSAGES_SQL_SCRIPT));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Autowired
    private MessageService messageService;

    @Test
    void createMessage_happyPath() {
        Message message = getMessage();
        assertEquals(0, JdbcTestUtils.countRowsInTable(jdbc, "messages"));
        messageService.createMessage(message);
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbc, "messages"));
    }

    @Test
    void createMessageWithNullFields() {
        Message message = new Message();
        assertThrows(NullFieldsException.class, () -> messageService.createMessage(message), "One or more fields are null");
        message.setTitle("some title");
        assertThrows(NullFieldsException.class, () -> messageService.createMessage(message), "One or more fields are null");
        message.setTitle(null);
        message.setBody("some body");
        assertThrows(NullFieldsException.class, () -> messageService.createMessage(message), "One or more fields are null");
    }

    @Test
    void findAll_happyPath() {
        Message message1 = getMessage();
        Message message2 = getMessage();
        Message message3 = getMessage();
        assertEquals(0, JdbcTestUtils.countRowsInTable(jdbc, "messages"));
        messageService.createMessage(message1);
        messageService.createMessage(message2);
        messageService.createMessage(message3);
        assertEquals(3, JdbcTestUtils.countRowsInTable(jdbc, "messages"));
        List<Message> allMessages = messageService.findAll();
        assertEquals(3, allMessages.size());
        assertTrue(allMessages.contains(message1));
        assertTrue(allMessages.contains(message2));
        assertTrue(allMessages.contains(message3));
    }

    @Test
    void findById_happyPath() {
        Message message = getMessage();
        messageService.createMessage(message);
        assertEquals(message, messageService.findById(1L));
    }

    @Test
    void findById_WithNotExistingId_shouldReturnNewEmptyMessage() {
        assertEquals(null, messageService.findById(1000L));
    }

    @Test
    void deleteById_happyPath() {
        Message message = getMessage();
        assertEquals(0, JdbcTestUtils.countRowsInTable(jdbc, "messages"));
        messageService.createMessage(message);
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbc, "messages"));
        messageService.deleteById(1L);
        assertEquals(0, JdbcTestUtils.countRowsInTable(jdbc, "messages"));
    }

    @Test
    void deleveteById_WithNotExistingId() {
        assertThrows(NoSuchIndexInDataBaseException.class, () -> messageService.deleteById(1000L), "No such index in database");
        assertThrows(NoSuchIndexInDataBaseException.class, () -> messageService.deleteById(-1L), "No such index in database");
    }

    @Test
    void delete_happyPath() {
        Message message = getMessage();
        assertEquals(0, JdbcTestUtils.countRowsInTable(jdbc, "messages"));
        messageService.createMessage(message);
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbc, "messages"));
        messageService.delete(message);
        assertEquals(0, JdbcTestUtils.countRowsInTable(jdbc, "messages"));
    }

    @Test
    void delete_withNotExistingMessage() {
        Message message = getMessage();
        assertEquals(0, JdbcTestUtils.countRowsInTable(jdbc, "messages"));
        messageService.delete(message);
        assertEquals(0, JdbcTestUtils.countRowsInTable(jdbc, "messages"));
    }

    @Test
    void update_happyPath() {
        Message message = getMessage();
        assertEquals(0, JdbcTestUtils.countRowsInTable(jdbc, "messages"));
        messageService.createMessage(message);
        message.setBody("New edited body");
        messageService.update(1L, message);
        assertEquals(message, messageService.findById(1L));
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbc, "messages"));
    }

    @Test
    void update_withNoSuchIndex() {
        Message message = getMessage();
        assertEquals(0, JdbcTestUtils.countRowsInTable(jdbc, "messages"));
        messageService.update(-1L, message);
        assertEquals(0, JdbcTestUtils.countRowsInTable(jdbc, "messages"));
    }

    @Test
    void update_withNullFields() {
        Message message = getMessage();
        Message messageToUpdate = new Message();
        messageService.createMessage(message);
        assertThrows(NullFieldsException.class, () -> messageService.update(1L, messageToUpdate), "One or more fields are null");
        messageToUpdate.setTitle("some title");
        assertThrows(NullFieldsException.class, () -> messageService.update(1L, messageToUpdate), "One or more fields are null");
        messageToUpdate.setTitle(null);
        messageToUpdate.setBody("some body");
        assertThrows(NullFieldsException.class, () -> messageService.update(1L, messageToUpdate), "One or more fields are null");
    }

    private Message getMessage() {
        Message message = new Message();
        message.setTitle("Title");
        message.setBody("Body body body body body body body body body body body body");
        return message;
    }
}