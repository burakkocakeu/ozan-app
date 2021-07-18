package com.burakkocak.casestudies.exchangeservice.resource;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * @author Burak KOCAK
 * @date 7/11/2021
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConversationList {

    private List<UUID> transactionIdList;

    private List<LocalDateTime> conversationTimeList;

    public ConversationList(List<UUID> transactionIdList) {
        this.transactionIdList = transactionIdList;
    }
}
