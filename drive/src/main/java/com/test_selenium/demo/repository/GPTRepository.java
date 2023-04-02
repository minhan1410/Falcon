package com.test_selenium.demo.repository;

import com.test_selenium.demo.model.AccountGPT;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GPTRepository extends MongoRepository<AccountGPT, String> {
}
