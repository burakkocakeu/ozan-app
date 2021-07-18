package com.burakkocak.casestudies.exchangeservice.controller;

import com.burakkocak.casestudies.exchangeservice.annotation.TrackExecutionTime;
import com.burakkocak.casestudies.exchangeservice.exception.RestServiceException;
import com.burakkocak.casestudies.exchangeservice.resource.ConversationList;
import com.burakkocak.casestudies.exchangeservice.resource.ConversationPair;
import com.burakkocak.casestudies.exchangeservice.resource.CurrencyPair;
import com.burakkocak.casestudies.exchangeservice.service.ConversationService;
import com.burakkocak.casestudies.exchangeservice.service.CurrencyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

/**
 * You will develop a simple foreign exchange application which is one of the most
 * common services used in financial applications. Requirements are as follows:
 * Functional Requirements:
 * 1. Exchange Rate API
 * ○ input: currency pair to retrieve the exchange rate
 * ○ output: exchange rate
 * 2. Conversion API:
 * ○ input: source amount, source currency, target currency
 * ○ output: amount in target currency, and transaction id.
 * 3. Conversion List API
 * ○ input: transaction id or transaction date (at least one of
 * the inputs shall be provided for each call)
 * ○ output: list of conversions filtered by the inputs and paging is
 * required
 * 4. The application shall use a service provider to retrieve
 * exchange rates and optionally for calculating amounts. (see
 * hints)
 * 5. In the case of an error, a specific code to the error and a
 * meaningful message shall be provided.
 */

@RestController
@RequestMapping("/")
public class ExchangeController {

    private CurrencyService currencyService;

    private ConversationService conversationService;

    public ExchangeController(CurrencyService currencyService, ConversationService conversationService) {
        this.currencyService = currencyService;
        this.conversationService = conversationService;
    }

    /**
     *
     * @param currencyPair Getting input as CurrencyPair then returning it by adding value(s): exchangeRate
     * @return
     * @throws Exception
     */
    @PostMapping("exchange")
    @TrackExecutionTime
    public ResponseEntity exchangeRate(@Validated @RequestBody CurrencyPair currencyPair) throws RestServiceException {

        currencyService.calculateExchangeRate(currencyPair);

        return new ResponseEntity(currencyPair, HttpStatus.OK);

    }

    /**
     *
     * @param conversationPair Getting input as ConversationPair then returning it by adding value(s): time, transactionId, convertedExchangeAmount
     * @return
     * @throws Exception
     */
    @PostMapping("convert")
    @TrackExecutionTime
    public ResponseEntity conversation(@Validated @RequestBody ConversationPair conversationPair) throws RestServiceException {

        CurrencyPair pair = new ObjectMapper().convertValue(conversationPair, CurrencyPair.class);

        currencyService.calculateExchangeRate(pair);

        Double exchangeRate = pair.getExchangeRate();

        conversationPair.setTime(LocalDateTime.now());
        conversationPair.setTransactionId(UUID.randomUUID());
        conversationPair.setConvertedExchangeAmount(conversationPair.getExchangeAmount() * exchangeRate);

        conversationService.save(conversationService.toEntity(conversationPair, exchangeRate));

        return new ResponseEntity(conversationPair, HttpStatus.OK);

    }

    /**
     *
     * @param list Getting input as ConversationList then returning paged Set<ConversationPair> by the following params:
     * @param page (IMPORTANT: page starts with zero), Eg: ?page=0&size=10 will deliver first page with 10 items.
     * @param size Number of items returned within the current page.
     * @return
     */
    @PostMapping("convert/list")
    @TrackExecutionTime
    public ResponseEntity conversationList(@RequestBody ConversationList list, @RequestParam Integer page, @RequestParam Integer size) {

        Set<ConversationPair> conversationPairList = conversationService.toDtoList(
                conversationService.findByTransactionIdInOrTimeIn(
                        page, size, list.getTransactionIdList(), list.getConversationTimeList()
                ).getContent()
        );

        return new ResponseEntity(conversationPairList, HttpStatus.OK);

    }

}
