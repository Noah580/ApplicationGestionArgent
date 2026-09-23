package com.test.argent.domain;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Invariant : le solde n'est jamais négatif (pas de découvert autorisé).
 */
public class Account {

    private final String id;
    private final String name;
    private BigDecimal balance;

    public Account(String id, String name, BigDecimal initialBalance) {
        requireNotBlank(name);
        requireNotNegative(initialBalance);
        this.id = id;
        this.name = name;
        this.balance = initialBalance;
    }

    public void credit(BigDecimal amount) {
        requirePositive(amount);
        balance = balance.add(amount);
    }

    public void debit(BigDecimal amount) {
        requirePositive(amount);
        if (!canAfford(amount)) {
            throw new InsufficientBalanceException(id, balance, amount);
        }
        balance = balance.subtract(amount);
    }

    public boolean canAfford(BigDecimal amount) {
        return balance.compareTo(amount) >= 0;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    private static void requireNotBlank(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Le nom du compte est obligatoire");
        }
    }

    private static void requireNotNegative(BigDecimal initialBalance) {
        Objects.requireNonNull(initialBalance, "Le solde initial est obligatoire");
        if (initialBalance.signum() < 0) {
            throw new IllegalArgumentException("Le solde initial ne peut pas être négatif");
        }
    }

    private static void requirePositive(BigDecimal amount) {
        Objects.requireNonNull(amount, "Le montant est obligatoire");
        if (amount.signum() <= 0) {
            throw new IllegalArgumentException("Le montant doit être strictement positif");
        }
    }
}
