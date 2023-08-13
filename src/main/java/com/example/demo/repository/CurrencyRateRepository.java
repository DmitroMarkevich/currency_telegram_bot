package com.example.demo.repository;

import com.example.demo.model.CurrencyRate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRateRepository extends MongoRepository<CurrencyRate, Long> {
}
