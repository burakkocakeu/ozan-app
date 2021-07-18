package com.burakkocak.casestudies.exchangeservice.service;

import com.burakkocak.casestudies.exchangeservice.entity.Currency;
import com.burakkocak.casestudies.exchangeservice.exception.RestServiceException;
import com.burakkocak.casestudies.exchangeservice.repository.CurrencyRepository;
import com.burakkocak.casestudies.exchangeservice.resource.CurrencyPair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

/**
 * @author Burak KOCAK
 * @date 7/11/2021
 */
@ExtendWith(MockitoExtension.class)
class CurrencyServiceTest {

    @Mock
    private CurrencyRepository currencyRepository;

    private CurrencyService currencyService;

    @BeforeEach
    void setUp() {
        currencyService = new CurrencyService(currencyRepository);
    }

    @Test
    void getExchangeRatesByCurrencyPair() throws RestServiceException {
        // given
        CurrencyPair currencyPair = new CurrencyPair("USD", "TRY");

        List<Currency> returnCurrencyList = Arrays.asList(new Currency("USD", BigDecimal.valueOf(1)), new Currency("TRY", BigDecimal.valueOf(0.13)));

        // when
        // then
        given(currencyRepository.findByCurrencyIn(Arrays.asList(currencyPair.getFrom(), currencyPair.getTo())))
                .willReturn(Optional.of(returnCurrencyList));

        assertThat(currencyService.getExchangeRatesByCurrencyPair(currencyPair)).asList();

        // when
        // then
        given(currencyRepository.findByCurrencyIn(Arrays.asList(currencyPair.getFrom(), currencyPair.getTo())))
                .willReturn(Optional.of(new ArrayList<>()));

        assertThatThrownBy(() -> currencyService.getExchangeRatesByCurrencyPair(currencyPair))
                .isInstanceOf(RestServiceException.class)
                .hasMessageContaining("One or more currencies either invalid or could not be supported, currently!");
    }

    @Test
    void saveAll() throws RestServiceException {
        // given
        List<Currency> currencies = new ArrayList<>();

        currencies.add(new Currency("USD", BigDecimal.valueOf(1)));
        currencies.add(new Currency("GBP", BigDecimal.valueOf(1.33)));
        currencies.add(new Currency("EUR", BigDecimal.valueOf(1.21)));

        // when
        currencyService.saveAll(currencies);

        // then
        ArgumentCaptor<List<Currency>> currencyListArgumentCaptor = ArgumentCaptor.forClass(List.class);

        verify(currencyRepository).saveAll(currencyListArgumentCaptor.capture());

        List<Currency> capturedCurrencyList = currencyListArgumentCaptor.getValue();

        assertThat(capturedCurrencyList).isEqualTo(currencies);

        // when
        // then
        assertThatThrownBy(() -> currencyService.saveAll(null))
                .isInstanceOf(RestServiceException.class)
                .hasMessageContaining("Saving null is not permitted!");

        verify(currencyRepository, times(1)).saveAll(any());
    }

    @Test
    void findAll() {
        // when
        List<Currency> currencies = currencyService.findAll();

        // then
        verify(currencyRepository).findAll();
        assertThat(currencies).asList();
    }
}