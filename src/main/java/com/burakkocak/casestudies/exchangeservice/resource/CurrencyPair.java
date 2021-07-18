package com.burakkocak.casestudies.exchangeservice.resource;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL) // Do NOT include null properties.
@JsonIgnoreProperties(ignoreUnknown = true) // Ignore unknown properties while mapping. Eg: 'convertedExchangeAmount' field will be ignored while mapping from ConversationPair.
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyPair {

    @NotEmpty
    @Size(min = 2, max = 6)
    private String from;

    @NotEmpty
    @Size(min = 2, max = 6)
    private String to;

    /**
     * Only Exchange Rate API response fulfills this field.
     */
    @Nullable
    private Double exchangeRate;

    public CurrencyPair(String from, String to) {
        this.from = from;
        this.to = to;
    }
}
