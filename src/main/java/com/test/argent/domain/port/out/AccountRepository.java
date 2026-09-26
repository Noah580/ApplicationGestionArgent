package com.test.argent.domain.port.out;

import com.test.argent.domain.Account;

import java.util.Optional;

public interface AccountRepository {

    Optional<Account> findById(String id);

    Account save(Account account);
}
