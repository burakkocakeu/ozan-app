package com.burakkocak.casestudies.exchangeservice.entity;

import com.burakkocak.casestudies.exchangeservice.listener.EntityUpdateListener;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @author Burak KOCAK
 * @date 7/10/2021
 */
@Entity
@Table(name = "CURRENCY")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@EntityListeners(EntityUpdateListener.class)
public class Currency {

    @Id
    @Column(name = "CURRENCY")
    private String currency;

    @Column(name = "EXCHANGE_RATE")
    private BigDecimal exchangeRate;

    public Currency(String currency) {
        this.currency = currency;
    }
}
