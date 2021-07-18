package com.burakkocak.casestudies.exchangeservice.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PrePersist;

/**
 * @author Burak KOCAK
 * @date 7/10/2021
 */
@Slf4j
public class EntityUpdateListener {

    private ObjectMapper mapper = new ObjectMapper();

    @PrePersist
    private void preUpdateListener(Object o) throws JsonProcessingException {
        log.debug("Entity on 'Pre-persist': " + mapper.writeValueAsString(o));
    }

    @PostPersist
    private void postUpdateListener(Object o) throws JsonProcessingException {
        log.debug("Entity on 'Post-persist': " + mapper.writeValueAsString(o));
    }

    @PostLoad
    private void postLoadListener(Object o) throws JsonProcessingException {
        log.debug("Entity on 'Load': " + mapper.writeValueAsString(o));
    }

}
