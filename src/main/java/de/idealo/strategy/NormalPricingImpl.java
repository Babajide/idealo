package de.idealo.strategy;

import de.idealo.PricingRule;

import java.math.BigDecimal;

public class NormalPricingImpl implements Pricing {


    @Override
    public BigDecimal calculate(int quantity, PricingRule rule) {
        return  BigDecimal.valueOf(quantity * rule.getUnitPrice());

    }
}
