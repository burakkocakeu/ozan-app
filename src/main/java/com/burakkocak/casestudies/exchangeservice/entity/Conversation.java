package com.burakkocak.casestudies.exchangeservice.entity;

import com.burakkocak.casestudies.exchangeservice.listener.EntityUpdateListener;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * @author Burak KOCAK
 * @date 7/10/2021
 */
@Entity
@Table(name = "CONVERSATION_HISTORY")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@EntityListeners(EntityUpdateListener.class)
public class Conversation {

    @Id
    @Column(name = "ID")
    private UUID transactionId;

    @Column(name = "SOURCE_CURRENCY")
    private String from;

    @Column(name = "TARGET_CURRENCY")
    private String to;

    @Column(name = "EXCHANGE_RATE")
    private BigDecimal exchangeRate;

    @Column(name = "EXCHANGE_AMOUNT")
    private BigDecimal exchangeAmount;

    @Column(name = "CONVERTED_EXCHANGE_AMOUNT")
    private BigDecimal convertedExchangeAmount;

    @Column(name = "DATE")
    private String time;

}
