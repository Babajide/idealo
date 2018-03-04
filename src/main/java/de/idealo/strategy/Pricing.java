package de.idealo.strategy;

import de.idealo.PricingRule;

import java.math.BigDecimal;

public interface Pricing {

    BigDecimal calculate(int quantity, PricingRule rule);
}
