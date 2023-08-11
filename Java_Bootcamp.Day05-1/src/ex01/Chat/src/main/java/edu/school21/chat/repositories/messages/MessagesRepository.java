package edu.school21.chat.repositories.messages;

import edu.school21.chat.models.Message;

import java.util.Optional;

public interface MessagesRepository {

    Optional<Message> findById(Long id);
}
