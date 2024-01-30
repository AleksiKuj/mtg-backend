package com.aleksi.mtg.repository;

import com.aleksi.mtg.model.ShortCard;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ShortCardRepository extends MongoRepository<ShortCard, String> {
    List<ShortCard> findAll();
}
