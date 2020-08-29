package com.datastream.messenger.service;

import com.datastream.messenger.exception.ResourceNotFoundException;
import com.datastream.messenger.model.Message;
import com.datastream.messenger.model.Room;
import com.datastream.messenger.model.Status;
import com.datastream.messenger.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {
    @Autowired
    private MessageRepository repository;
    @Autowired
    private RoomService chatRoomService;
    @Autowired
    private MongoOperations mongoOperations;

    public Message save(Message chatMessage) {
        chatMessage.setStatus(Status.RECEIVED);
        repository.save(chatMessage);
        return chatMessage;
    }

    public long countNewMessages(String sender, String recipient) {
        return repository.countBySenderAndRecipientAndStatus(
                sender, recipient, Status.RECEIVED);
    }

    public List<Message> findChatMessages(String senderId, String recipientId) {
        Optional<String> chatId = chatRoomService.getChatId(senderId, recipientId, false);

        List<Message> messages =
                chatId.map(cId -> repository.findByChatId(cId)).orElse(new ArrayList<>());

        if(messages.size() > 0) {
            updateStatuses(senderId, recipientId, Status.DELIVERED);
        }

        return messages;
    }

    public Message findById(String id) {
        return repository
                .findById(id)
                .map(chatMessage -> {
                    chatMessage.setStatus(Status.DELIVERED);
                    return repository.save(chatMessage);
                })
                .orElseThrow(() ->
                        new ResourceNotFoundException("can't find message (" + id + ")"));
    }

    public void updateStatuses(String senderId, String recipientId, Status status) {
        Query query = new Query(
                Criteria
                        .where("sender").is(senderId)
                        .and("recipient").is(recipientId));
        Update update = Update.update("status", status);
        mongoOperations.updateMulti(query, update, Message.class);
    }
}
