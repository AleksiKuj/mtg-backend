package com.aleksi.mtg.repository;

import com.aleksi.mtg.model.Card;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.Optional;

public interface FullCardRepository extends MongoRepository<Card, String> {
    Optional<Card> findByDate(Date date);
}
