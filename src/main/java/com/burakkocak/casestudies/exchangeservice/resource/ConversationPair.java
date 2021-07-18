package com.burakkocak.casestudies.exchangeservice.resource;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL) // Do NOT include null properties.
@AllArgsConstructor
@NoArgsConstructor
public class ConversationPair {

    @NotEmpty
    @Size(min = 2, max = 6)
    private String from;

    @NotEmpty
    @Size(min = 2, max = 6)
    private String to;

    @NotNull
    private Double exchangeAmount;

    /**
     * Only Conversion API response fulfills this field.
     */
    @Nullable
    private Double convertedExchangeAmount;

    /**
     * Generated on persist!
     */
    @Nullable
    private UUID transactionId;

    /**
     * Generated on persist!
     */
    @Nullable
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime time;

    public ConversationPair(String from, String to, Double exchangeAmount) {
        this.from = from;
        this.to = to;
        this.exchangeAmount = exchangeAmount;
    }
    
}
