package com.datastream.messenger.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
public class Message {
    private String id;
    private String chatId;
    private String sender;
    private String recipient;
    private String content;
    private Status status;
    private String timestamp;
}
