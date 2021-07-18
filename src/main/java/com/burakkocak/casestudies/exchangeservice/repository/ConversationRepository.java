package com.burakkocak.casestudies.exchangeservice.repository;

import com.burakkocak.casestudies.exchangeservice.entity.Conversation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Burak KOCAK
 * @date 7/10/2021
 */
public interface ConversationRepository extends JpaRepository<Conversation, UUID> {

    Optional<Page<Conversation>> findByTransactionIdInOrTimeIn(List<UUID> transactionIdList,
                                                               List<LocalDateTime> timeList,
                                                               Pageable pageable);

}
