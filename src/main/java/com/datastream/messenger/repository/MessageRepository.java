package com.datastream.messenger.repository;

import com.datastream.messenger.model.Message;
import com.datastream.messenger.model.Status;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MessageRepository
        extends MongoRepository<Message, String> {

    long countBySenderAndRecipientAndStatus(
            String sender, String recipient, Status status);

    List<Message> findByChatId(String chatId);
}
