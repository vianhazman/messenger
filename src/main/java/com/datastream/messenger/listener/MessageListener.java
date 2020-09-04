package com.datastream.messenger.listener;

import com.datastream.messenger.model.Message;
import com.datastream.messenger.utils.KafkaConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageListener {
    @Autowired
    SimpMessagingTemplate template;

    @KafkaListener(
            topics = KafkaConstants.KAFKA_TOPIC,
            groupId = KafkaConstants.GROUP_ID
    )
    public void listen(Message message) {
        System.out.println("sending via kafka listener..");
        template.convertAndSend("/topic/group/"+message.getRecipient(), message);
    }
}
