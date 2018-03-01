package de.idealo;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PricingRule {

    private String sku;
    private int unitPrice;
    private SpecialPrice specialPrice;

}
