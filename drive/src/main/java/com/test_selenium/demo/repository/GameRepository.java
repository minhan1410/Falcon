package com.test_selenium.demo.repository;

import com.test_selenium.demo.model.Game;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends MongoRepository<Game,String> {
}
