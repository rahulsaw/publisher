package com.natwest.publisher.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.natwest.publisher.enums.Currency;
import com.natwest.publisher.enums.Type;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;

import static com.natwest.publisher.Constant.CANNOT_BE_EMPTY;

/*
created by Rahul sawaria on 03/04/22
*/
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Transaction implements Serializable {

    @Serial
    private static final long serialVersionUID = -5686268268617670712L;

    @NotNull(message = CANNOT_BE_EMPTY)
    private Long accountNumber;

    @NotNull(message = CANNOT_BE_EMPTY)
    private Type type;

    @NotNull(message = CANNOT_BE_EMPTY)
    private Integer amount;

    @NotNull(message = CANNOT_BE_EMPTY)
    private Currency currency;

    @NotNull(message = CANNOT_BE_EMPTY)
    private Long accountFrom;

}
