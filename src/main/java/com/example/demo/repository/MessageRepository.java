package com.example.demo.repository;

import com.example.demo.entity.Message;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MessageRepository extends JpaRepository<Message, Integer> {

    @Query(value = "SELECT id, title, body FROM message ORDER BY id", nativeQuery = true)
    public List<Message> findAllSorted();

}
