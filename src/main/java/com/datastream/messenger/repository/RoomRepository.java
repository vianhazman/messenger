package com.datastream.messenger.repository;

import com.datastream.messenger.model.Message;
import com.datastream.messenger.model.Room;
import com.datastream.messenger.model.Status;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends MongoRepository<Room, String> {
    Optional<Room> findBySenderAndRecipient(String sender, String recipient);
}