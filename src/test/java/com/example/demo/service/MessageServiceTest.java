package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.demo.DemoApplication;
import com.example.demo.entity.Message;
import com.example.demo.exception.MessageDataBaseException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;
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
        assertEquals(0, JdbcTestUtils.countRowsInTable(jdbc, "message"));
        messageService.createOrUpdateMessage(message);
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbc, "message"));
    }

    @Test
    void createMessageWithNullFields() {
        Message message = new Message();
        assertThrows(MessageDataBaseException.class, () -> messageService.createOrUpdateMessage(message), "One or more fields are null");
        message.setTitle("some title");
        assertThrows(MessageDataBaseException.class, () -> messageService.createOrUpdateMessage(message), "One or more fields are null");
        message.setTitle(null);
        message.setBody("some body");
        assertThrows(MessageDataBaseException.class, () -> messageService.createOrUpdateMessage(message), "One or more fields are null");
    }

    @Test
    void findAll_happyPath() {
        Message message1 = getMessage();
        Message message2 = getMessage();
        Message message3 = getMessage();
        assertEquals(0, JdbcTestUtils.countRowsInTable(jdbc, "message"));
        messageService.createOrUpdateMessage(message1);
        messageService.createOrUpdateMessage(message2);
        messageService.createOrUpdateMessage(message3);
        assertEquals(3, JdbcTestUtils.countRowsInTable(jdbc, "message"));
        List<Message> allMessages = messageService.findAll();
        assertEquals(3, allMessages.size());
        assertTrue(allMessages.contains(message1));
        assertTrue(allMessages.contains(message2));
        assertTrue(allMessages.contains(message3));
    }

    @Test
    void findById_happyPath() {
        Message message = getMessage();
        messageService.createOrUpdateMessage(message);
        assertEquals(message, messageService.findById(1).get());
    }

    @Test
    void findById_WithNotExistingId_shouldReturnNewEmptyMessage() {
        assertThrows(NoSuchElementException.class, () -> messageService.findById(1000).get(), "No such index in database");
        assertEquals(false, messageService.findById(1000).isPresent());
    }

    @Test
    void deleteById_happyPath() {
        Message message = getMessage();
        assertEquals(0, JdbcTestUtils.countRowsInTable(jdbc, "message"));
        messageService.createOrUpdateMessage(message);
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbc, "message"));
        messageService.deleteById(1);
        assertEquals(0, JdbcTestUtils.countRowsInTable(jdbc, "message"));
    }

    @Test
    void deleveteById_WithNotExistingId() {
        assertThrows(NoSuchElementException.class, () -> messageService.deleteById(1000), "No such index in database");
        assertThrows(NoSuchElementException.class, () -> messageService.deleteById(-1), "No such index in database");
    }

    @Test
    void delete_happyPath() {
        Message message = getMessage();
        assertEquals(0, JdbcTestUtils.countRowsInTable(jdbc, "message"));
        messageService.createOrUpdateMessage(message);
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbc, "message"));
        messageService.delete(message);
        assertEquals(0, JdbcTestUtils.countRowsInTable(jdbc, "message"));
    }

    @Test
    void delete_withNotExistingMessage() {
        Message message = getMessage();
        assertEquals(0, JdbcTestUtils.countRowsInTable(jdbc, "message"));
        messageService.delete(message);
        assertEquals(0, JdbcTestUtils.countRowsInTable(jdbc, "message"));
    }

    @Test
    void update_happyPath() {
        Message message = getMessage();
        assertEquals(0, JdbcTestUtils.countRowsInTable(jdbc, "message"));
        messageService.createOrUpdateMessage(message);
        message.setBody("New edited body");
        messageService.createOrUpdateMessage(message);
        assertEquals(message, messageService.findById(1).get());
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbc, "message"));
    }

    @Test
    void update_withNoSuchIndex() {
        Message message = getMessage();
        assertEquals(0, JdbcTestUtils.countRowsInTable(jdbc, "message"));
        messageService.createOrUpdateMessage(message);
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbc, "message"));
    }

    @Test
    void update_withNullFields() {
        Message message = getMessage();
        Message messageToUpdate = new Message();
        messageService.createOrUpdateMessage(message);
        assertThrows(MessageDataBaseException.class, () -> messageService.createOrUpdateMessage(messageToUpdate));
        messageToUpdate.setTitle("some title");
        assertThrows(MessageDataBaseException.class, () -> messageService.createOrUpdateMessage(messageToUpdate));
        messageToUpdate.setTitle(null);
        messageToUpdate.setBody("some body");
        assertThrows(MessageDataBaseException.class, () -> messageService.createOrUpdateMessage( messageToUpdate));
    }

    private Message getMessage() {
        Message message = new Message();
        message.setTitle("Title");
        message.setBody("Body body body body body body body body body body body body");
        return message;
    }
}
