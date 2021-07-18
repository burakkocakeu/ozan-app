package com.burakkocak.casestudies.exchangeservice.service;

import com.burakkocak.casestudies.exchangeservice.entity.Conversation;
import com.burakkocak.casestudies.exchangeservice.repository.ConversationRepository;
import com.burakkocak.casestudies.exchangeservice.resource.ConversationPair;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

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
public class ConversationService {

    private ConversationRepository conversationRepository;

    public ConversationService(ConversationRepository conversationRepository) {
        this.conversationRepository = conversationRepository;
    }

    public Conversation toEntity(ConversationPair dto, Double exchangeRate) {
        Conversation conversation = new Conversation();
        conversation.setConvertedExchangeAmount(BigDecimal.valueOf(dto.getConvertedExchangeAmount()));
        conversation.setExchangeAmount(BigDecimal.valueOf(dto.getExchangeAmount()));

        if (exchangeRate != null) {
            conversation.setExchangeRate(BigDecimal.valueOf(exchangeRate));
        }
        conversation.setFrom(dto.getFrom());
        conversation.setTo(dto.getTo());
        conversation.setTransactionId(dto.getTransactionId());
        conversation.setTime(dto.getTime().toString());

        return conversation;
    }

    public ConversationPair toDto(Conversation conversation) {
        ConversationPair conversationPair = new ConversationPair();
        conversationPair.setFrom(conversation.getFrom());
        conversationPair.setTo(conversation.getTo());
        conversationPair.setExchangeAmount(conversation.getExchangeAmount().doubleValue());
        conversationPair.setConvertedExchangeAmount(conversation.getConvertedExchangeAmount().doubleValue());
        conversationPair.setTransactionId(conversation.getTransactionId());

        return conversationPair;
    }

    public Set<ConversationPair> toDtoList(List<Conversation> conversationList) {
        Set<ConversationPair> list = new HashSet<>();

        conversationList.forEach(c -> list.add(toDto(c)));

        return list;
    }

    public void save(Conversation conversation) {
        conversationRepository.save(conversation);
    }

    public List<Conversation> findAll() {
        return conversationRepository.findAll();
    }

    public Page<Conversation> findByTransactionIdInOrTimeIn(Integer page, Integer size,
                                                            List<UUID> transactionIdList,
                                                            List<LocalDateTime> timeList) {
        if (transactionIdList == null) {
            transactionIdList = new ArrayList<>();
        }

        if (timeList == null) {
            timeList = new ArrayList<>();
        }

        Pageable pageable = PageRequest.of(page, size);

        return conversationRepository.findByTransactionIdInOrTimeIn(transactionIdList, timeList, pageable).get();
    }

}
