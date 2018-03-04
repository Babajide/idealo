package de.idealo;

import lombok.Builder;
import lombok.Data;
@Builder
@Data
public class SpecialPrice {

    private int quantity;
    private Integer price;
    private String sku;
}
