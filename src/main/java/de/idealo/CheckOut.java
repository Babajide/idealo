package de.idealo;

import de.idealo.strategy.NormalPricingImpl;
import de.idealo.strategy.SpecialPricingImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class CheckOut {

    private Map<String, Integer> order;
    private List<PricingRule> rules;

    public CheckOut(List<PricingRule> rules) {
        this.rules = rules;
        this.order = new HashMap();
    }

    public void scan(String sku) {
        //Build the order data structure for easy manipulation, here I have used map to store product and frequency
        if (order.get(sku) == null) {
            order.put(sku, 1); //first time item is added
        } else {
            order.put(sku, Integer.valueOf(order.get(sku) + 1)); //increment count by 1
        }


    }

    public int total() {
        //No valid item scanned.
        if (order.isEmpty()) {
            return 0;
        }
        //examine order, apply special pricing if available
        int amt = 0;
        for (String key : order.keySet()) {
            for (PricingRule pricingRule : rules) {
                if (isDiscounted(key, order.get(key))) {
                    int multiple = order.get(key) / pricingRule.getSpecialPrice().getQuantity();
                    int modulusRemainder = order.get(key) % pricingRule.getSpecialPrice().getQuantity();
                    amt += specialPricing(multiple, pricingRule);
                    if (modulusRemainder > 0) {
                        amt += normalPricing(modulusRemainder, pricingRule);
                    }
                    break;
                } else {
                    if (key.equals(pricingRule.getSku())) {
                        amt += normalPricing(order.get(key), pricingRule);
                        break;

                    }
                }
            }

        }
        return amt;
    }

    private final int normalPricing(int quantity, PricingRule rule) {
        return new NormalPricingImpl().calculate(quantity, rule).intValue();
    }

    private final int specialPricing(int quantity, PricingRule rule) {
        return new SpecialPricingImpl().calculate(quantity, rule).intValue();
    }

    private boolean isDiscounted(final String sku, final int quantity) {
        Predicate<PricingRule> pricingRulePredicate = p ->
                (p.getSpecialPrice() != null
                        && quantity >= p.getSpecialPrice().getQuantity())
                        && sku.equals(p.getSku());
        return rules.stream().anyMatch(pricingRulePredicate::test);
    }
}
