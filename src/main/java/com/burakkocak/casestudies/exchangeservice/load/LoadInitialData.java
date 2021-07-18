package com.burakkocak.casestudies.exchangeservice.load;

import com.burakkocak.casestudies.exchangeservice.entity.Currency;
import com.burakkocak.casestudies.exchangeservice.resource.ConversationList;
import com.burakkocak.casestudies.exchangeservice.resource.ConversationPair;
import com.burakkocak.casestudies.exchangeservice.resource.CurrencyPair;
import com.burakkocak.casestudies.exchangeservice.service.ConversationService;
import com.burakkocak.casestudies.exchangeservice.service.CurrencyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriUtils;

import java.math.BigDecimal;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Component
@Slf4j
public class LoadInitialData implements CommandLineRunner {

    @Value("${server.port}")
    private Integer port;

    private CurrencyService currencyService;

    private ConversationService conversationService;

    private ObjectMapper mapper = new ObjectMapper();

    public LoadInitialData(CurrencyService currencyService, ConversationService conversationService) {
        this.currencyService = currencyService;
        this.conversationService = conversationService;
    }

    /**
     * QUICK DEMONSTRATION PURPOSE ONLY!
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
        String testUrl = "http://localhost:" + port;
        List<UUID> conversationTransactionIdList = new ArrayList<>();

        List<Currency> currencies = new ArrayList<>();
        currencies.add(new Currency("USD", BigDecimal.valueOf(1)));
        currencies.add(new Currency("TRY", BigDecimal.valueOf(0.13)));
        currencies.add(new Currency("EUR", BigDecimal.valueOf(1.12)));
        currencies.add(new Currency("GBP", BigDecimal.valueOf(1.29)));

        currencyService.saveAll(currencies);

        log.debug("testingExchangeRateApi():");
        testingExchangeRateApi(testUrl);

        log.debug("testingConversationApi():");
        testingConversationApi(testUrl, conversationTransactionIdList);

        log.debug("testingConversationListApi():");
        testingConversationListApi(testUrl, conversationTransactionIdList);

        log.debug("*************************************");
        log.debug("Listing currency database:");
        currencyService.findAll();

        log.debug("*************************************");
        log.debug("Listing conversation database:");
        conversationService.findAll();
    }

    private void testingExchangeRateApi(String testUrl) {
        try {
            RestTemplate rest = new RestTemplate();
            log.debug("*************************************");

            ResponseEntity responseEntity = rest.exchange(new RequestEntity(
                    new CurrencyPair("USD", "TRY"),
                    HttpMethod.POST,
                    new URI(UriUtils.decode(testUrl + "/exchange", "UTF-8"))), String.class);
            log.debug("Response received:" + responseEntity);
            log.debug("*************************************");

            responseEntity = rest.exchange(new RequestEntity(
                    new CurrencyPair("USD", "GBP"),
                    HttpMethod.POST,
                    new URI(UriUtils.decode(testUrl + "/exchange", "UTF-8"))), String.class);
            log.debug("Response received:" + responseEntity);
            log.debug("*************************************");

            responseEntity = rest.exchange(new RequestEntity(
                    new CurrencyPair("EUR", "GBP"),
                    HttpMethod.POST,
                    new URI(UriUtils.decode(testUrl + "/exchange", "UTF-8"))), String.class);
            log.debug("Response received:" + responseEntity);
            log.debug("*************************************");

            responseEntity = rest.exchange(new RequestEntity(
                    new CurrencyPair("EU", "GBP"),
                    HttpMethod.POST,
                    new URI(UriUtils.decode(testUrl + "/exchange", "UTF-8"))), String.class);
            log.debug("Response received:" + responseEntity);
        } catch (HttpClientErrorException e) {
            log.warn("    -   Suppressing: " + e.getClass().getSimpleName() + ", " + e.getMessage());
        } catch (HttpServerErrorException e) {
            log.warn("    -   Suppressing: " + e.getClass().getSimpleName() + ", " + e.getMessage());
        } catch (Exception e) {
            log.warn("    -   Suppressing: " + e.getClass().getSimpleName() + ", " + e.getMessage());
        }
    }

    private void testingConversationApi(String testUrl, List<UUID> conversationTransactionIdList) {
        try {
            RestTemplate rest = new RestTemplate();
            log.debug("*************************************");

            ResponseEntity responseEntity = rest.exchange(new RequestEntity(
                    new ConversationPair("USD", "TRY", 260D),
                    HttpMethod.POST,
                    new URI(UriUtils.decode(testUrl + "/convert", "UTF-8"))), String.class);
            conversationTransactionIdList.add(mapper.readValue(responseEntity.getBody().toString(), ConversationPair.class).getTransactionId());
            log.debug("Response received:" + responseEntity);
            log.debug("*************************************");

            responseEntity = rest.exchange(new RequestEntity(
                    new ConversationPair("USD", "GBP", 15.32D),
                    HttpMethod.POST,
                    new URI(UriUtils.decode(testUrl + "/convert", "UTF-8"))), String.class);
            conversationTransactionIdList.add(mapper.readValue(responseEntity.getBody().toString(), ConversationPair.class).getTransactionId());
            log.debug("Response received:" + responseEntity);
            log.debug("*************************************");

            responseEntity = rest.exchange(new RequestEntity(
                    new ConversationPair("EUR", "GBP", 48.99D),
                    HttpMethod.POST,
                    new URI(UriUtils.decode(testUrl + "/convert", "UTF-8"))), String.class);
            conversationTransactionIdList.add(mapper.readValue(responseEntity.getBody().toString(), ConversationPair.class).getTransactionId());
            log.debug("Response received:" + responseEntity);
        } catch (HttpClientErrorException e) {
            log.warn("    -   Suppressing: " + e.getClass().getSimpleName() + ", " + e.getMessage());
        } catch (HttpServerErrorException e) {
            log.warn("    -   Suppressing: " + e.getClass().getSimpleName() + ", " + e.getMessage());
        } catch (Exception e) {
            log.warn("    -   Suppressing: " + e.getClass().getSimpleName() + ", " + e.getMessage());
        }
    }

    private void testingConversationListApi(String testUrl, List<UUID> conversationTransactionIdList) {
        try {
            RestTemplate rest = new RestTemplate();
            log.debug("*************************************");

            ResponseEntity responseEntity = rest.exchange(new RequestEntity(
                    new ConversationList(conversationTransactionIdList),
                    HttpMethod.POST,
                    new URI(UriUtils.decode(testUrl + "/convert/list?page=0&size=3", "UTF-8"))), String.class);
            log.debug("Response received:" + responseEntity);
            log.debug("*************************************");

            conversationTransactionIdList.remove(0);
            responseEntity = rest.exchange(new RequestEntity(
                    new ConversationList(conversationTransactionIdList),
                    HttpMethod.POST,
                    new URI(UriUtils.decode(testUrl + "/convert/list?page=1&size=1", "UTF-8"))), String.class);
            log.debug("Response received:" + responseEntity);
            log.debug("*************************************");

            responseEntity = rest.exchange(new RequestEntity(
                    new ConversationList(Arrays.asList(UUID.randomUUID())),
                    HttpMethod.POST,
                    new URI(UriUtils.decode(testUrl + "/convert/list?page=0&size=2", "UTF-8"))), String.class);
            log.debug("Response received:" + responseEntity);
        } catch (HttpClientErrorException e) {
            log.warn("    -   Suppressing: " + e.getClass().getSimpleName() + ", " + e.getMessage());
        } catch (HttpServerErrorException e) {
            log.warn("    -   Suppressing: " + e.getClass().getSimpleName() + ", " + e.getMessage());
        } catch (Exception e) {
            log.warn("    -   Suppressing: " + e.getClass().getSimpleName() + ", " + e.getMessage());
        }
    }

}
