package com.burakkocak.casestudies.exchangeservice.service;

import com.burakkocak.casestudies.exchangeservice.entity.Conversation;
import com.burakkocak.casestudies.exchangeservice.repository.ConversationRepository;
import com.burakkocak.casestudies.exchangeservice.resource.ConversationPair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/**
 * @author Burak KOCAK
 * @date 7/11/2021
 */
@ExtendWith(MockitoExtension.class)
class ConversationServiceTest {

    @Mock
    private ConversationRepository conversationRepository;

    private ConversationService conversationService;

    @BeforeEach
    void setUp() {
        conversationService = new ConversationService(conversationRepository);
    }

    @Test
    void toEntity() {
        conversationService.toEntity(getConversationPair(), 5D);
    }

    @Test
    void toDto() {
        conversationService.toDto(getConversation());
    }

    @Test
    void toDtoList() {
        conversationService.toDtoList(Arrays.asList(getConversation()));
    }

    @Test
    void save() {
        // given
        Conversation conversation = getConversation();

        // when
        conversationService.save(conversation);

        // then
        ArgumentCaptor<Conversation> conversationArgumentCaptor =
                ArgumentCaptor.forClass(Conversation.class);

        verify(conversationRepository)
                .save(conversationArgumentCaptor.capture());

        Conversation capturedConversation = conversationArgumentCaptor.getValue();

        assertThat(capturedConversation).isEqualTo(conversation);
    }

    @Test
    void findAll() {
        // when
        List<Conversation> conversations = conversationService.findAll();

        // then
        verify(conversationRepository).findAll();
        assertThat(conversations).asList();
    }

    @Test
    void findByTransactionIdInOrTimeIn() {
        // given
        List<Conversation> conversationList = Arrays.asList(getConversation());

        // when
        given(conversationRepository.findByTransactionIdInOrTimeIn(anyList(), anyList(), any()))
                .willReturn(Optional.of(new PageImpl<>(conversationList)));

        Page<Conversation> conversations = conversationService.findByTransactionIdInOrTimeIn(0, 1, null,null);

        // then
        verify(conversationRepository).findByTransactionIdInOrTimeIn(new ArrayList<>(), new ArrayList<>(), PageRequest.of(0, 1));
        assertThat(conversations.getContent()).isEqualTo(conversationList);
    }

    private ConversationPair getConversationPair() {
        ConversationPair conversationPair = new ConversationPair(
                "USD", "TRY",
                Double.valueOf(7),
                Double.valueOf(35),
                UUID.randomUUID(),
                LocalDateTime.now()
        );
        return conversationPair;
    }

    private Conversation getConversation() {
        Conversation conversation = new Conversation(
                UUID.randomUUID(), "USD", "TRY",
                BigDecimal.valueOf(7),
                BigDecimal.valueOf(5),
                BigDecimal.valueOf(35),
                LocalDateTime.now().toString()
        );
        return conversation;
    }

}