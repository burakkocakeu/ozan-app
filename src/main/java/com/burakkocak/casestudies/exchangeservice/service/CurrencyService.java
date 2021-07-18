package com.burakkocak.casestudies.exchangeservice.service;

import com.burakkocak.casestudies.exchangeservice.entity.Currency;
import com.burakkocak.casestudies.exchangeservice.exception.RestServiceErrorEnum;
import com.burakkocak.casestudies.exchangeservice.exception.RestServiceException;
import com.burakkocak.casestudies.exchangeservice.repository.CurrencyRepository;
import com.burakkocak.casestudies.exchangeservice.resource.CurrencyPair;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Burak KOCAK
 * @date 7/10/2021
 *
 * a. ConfigurableBeanFactory.SCOPE_PROTOTYPE = A bean with prototype scope will return a different instance every time it is requested from the container.
 * b. ScopedProxyMode.TARGET_CLASS = If there is no active request. Spring will create a proxy to be injected as a dependency,
 *    and instantiate the target bean when it is needed in a request.
 */
@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CurrencyService {

    private CurrencyRepository currencyRepository;

    public CurrencyService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    public List<Currency> getExchangeRatesByCurrencyPair(CurrencyPair pair) throws RestServiceException {
        Optional<List<Currency>> currencies = currencyRepository.findByCurrencyIn(Arrays.asList(pair.getFrom(), pair.getTo()));

        if (!currencies.isPresent() || currencies.get().size() < 2) {
            throw new RestServiceException(RestServiceErrorEnum.UNKNOWN_CURRENCY);
        }

        return currencies.get();
    }

    public void saveAll(List<Currency> currencies) throws RestServiceException {
        if (currencies == null) {
            throw new RestServiceException(RestServiceErrorEnum.NULL_OBJECT);
        }
        currencyRepository.saveAll(currencies);
    }

    public List<Currency> findAll() {
        return currencyRepository.findAll();
    }

    public void calculateExchangeRate(CurrencyPair currencyPair) throws RestServiceException {

        List<Currency> currencies = getExchangeRatesByCurrencyPair(currencyPair);

        AtomicReference<Double> atomicFrom = new AtomicReference<>();
        AtomicReference<Double> atomicTo = new AtomicReference<>();
        currencies.forEach(c -> {
            if (c.getCurrency().equals(currencyPair.getFrom())) {
                atomicFrom.set(c.getExchangeRate().doubleValue());
            } else {
                atomicTo.set(c.getExchangeRate().doubleValue());
            }
        });

        currencyPair.setExchangeRate(atomicFrom.get() / atomicTo.get());

    }
}
