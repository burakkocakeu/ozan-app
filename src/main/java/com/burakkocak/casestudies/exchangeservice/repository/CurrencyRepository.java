package com.burakkocak.casestudies.exchangeservice.repository;

import com.burakkocak.casestudies.exchangeservice.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, String> {

    Optional<List<Currency>> findByCurrencyIn(List<String> currencyList);
}
