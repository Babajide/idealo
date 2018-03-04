package de.idealo.strategy;

import de.idealo.PricingRule;

import java.math.BigDecimal;

public class SpecialPricingImpl implements Pricing {


    @Override
    public BigDecimal calculate(int quantity, PricingRule rule) {
        return BigDecimal.valueOf(quantity * rule.getSpecialPrice().getPrice());


    }
}
