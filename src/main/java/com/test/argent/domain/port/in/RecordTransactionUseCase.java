package com.test.argent.domain.port.in;

import com.test.argent.domain.Transaction;
import com.test.argent.domain.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface RecordTransactionUseCase {

    Transaction recordTransaction(RecordTransactionCommand command);

    record RecordTransactionCommand(
            String accountId,
            BigDecimal amount,
            TransactionType type,
            String category,
            LocalDate date) {
    }
}
