package com.test.argent.domain;

import java.math.BigDecimal;

public class InsufficientBalanceException extends RuntimeException {

    public InsufficientBalanceException(String accountId, BigDecimal balance, BigDecimal requested) {
        super("Solde insuffisant sur le compte %s : solde %s, dépense demandée %s"
                .formatted(accountId, balance, requested));
    }
}
