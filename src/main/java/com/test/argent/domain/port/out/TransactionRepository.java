package com.test.argent.domain.port.out;

import com.test.argent.domain.Transaction;

import java.util.List;

public interface TransactionRepository {

    Transaction save(Transaction transaction);

    List<Transaction> findByAccountId(String accountId);
}
