package com.aleksi.mtg;

import org.SwaggerCodeGenExample.model.SearchCardsResponseContentInner;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface ShortCardRepository extends MongoRepository<ShortCard, String> {
    List<SearchCardsResponseContentInner> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
