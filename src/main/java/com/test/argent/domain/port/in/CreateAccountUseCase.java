package com.test.argent.domain.port.in;

import com.test.argent.domain.Account;

import java.math.BigDecimal;

public interface CreateAccountUseCase {

    Account createAccount(CreateAccountCommand command);

    record CreateAccountCommand(String name, BigDecimal initialBalance) {
    }
}
