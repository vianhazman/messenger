package com.datastream.messenger.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
public class Room {
    private String sender;
    private String recipient;
    private String id;
    private String chatId;
}
