package com.test.argent.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Le montant est toujours positif : c'est le type qui donne le sens du mouvement.
 */
public record Transaction(
        String id,
        BigDecimal amount,
        TransactionType type,
        String category,
        LocalDate date,
        String accountId) {

    public Transaction {
        requirePositive(amount);
        Objects.requireNonNull(type, "Le type est obligatoire");
        requireNotBlank(category, "La catégorie est obligatoire");
        Objects.requireNonNull(date, "La date est obligatoire");
        requireNotBlank(accountId, "Le compte associé est obligatoire");
    }

    public boolean isExpense() {
        return type == TransactionType.EXPENSE;
    }

    public boolean belongsTo(Account account) {
        return accountId.equals(account.getId());
    }

    private static void requirePositive(BigDecimal amount) {
        Objects.requireNonNull(amount, "Le montant est obligatoire");
        if (amount.signum() <= 0) {
            throw new IllegalArgumentException("Le montant doit être strictement positif");
        }
    }

    private static void requireNotBlank(String value, String errorMessage) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(errorMessage);
        }
    }
}
