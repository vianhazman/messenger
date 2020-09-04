package com.datastream.messenger.controller;

import com.datastream.messenger.model.Message;
import com.datastream.messenger.service.MessageService;
import com.datastream.messenger.service.RoomService;
import com.datastream.messenger.utils.KafkaConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@RestController
public class ChatController {

    @Autowired private KafkaTemplate<String, Message> kafkaTemplate;
    @Autowired private MessageService messageService;
    @Autowired private RoomService roomService;
    @Autowired SimpMessagingTemplate template;

    @CrossOrigin(origins = "*", maxAge = 3600)
    @PostMapping(value = "/api/send", consumes = "application/json", produces = "application/json")
    public void sendMessage(@RequestBody Message message) {
        Optional<String> chatId = roomService
                .getChatId(message.getSender(), message.getRecipient(), true);
        message.setChatId(chatId.get());
        message.setTimestamp(LocalDateTime.now().toString());

        try {
            Message saved = messageService.save(message);
            kafkaTemplate.send(KafkaConstants.KAFKA_TOPIC, message).get();
            System.out.println(message.toString());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/api/messages/{senderId}/{recipientId}/count")
    public ResponseEntity<Long> countNewMessages(
            @PathVariable String senderId,
            @PathVariable String recipientId) {

        return ResponseEntity
                .ok(messageService.countNewMessages(senderId, recipientId));
    }

    @GetMapping("/api/messages/{senderId}/{recipientId}")
    public ResponseEntity<?> findChatMessages ( @PathVariable String senderId,
                                                @PathVariable String recipientId) {
        return ResponseEntity
                .ok(messageService.findChatMessages(senderId, recipientId));
    }

    @GetMapping("/api/rooms/{senderId}")
    public ResponseEntity<?> findChatRooms ( @PathVariable String senderId ) {
        return ResponseEntity
                .ok(roomService.findRoomList(senderId));
    }

    @GetMapping("/api/messages/{id}")
    public ResponseEntity<?> findMessage ( @PathVariable String id) {
        return ResponseEntity
                .ok(messageService.findById(id));
    }

    @MessageMapping("/sendMessage")
    @SendTo("{receiver}/topic/group")
    public Message broadcastGroupMessage(@Payload Message message) {
        return message;
    }

}