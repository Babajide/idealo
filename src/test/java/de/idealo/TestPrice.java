package de.idealo;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestPrice {

    @Before
    public  void  init(){
        PricingRule aRule =  PricingRule.builder()
                .sku("A")
                .unitPrice(40)
                .specialPrice(SpecialPrice.builder()
                        .sku("A")
                        .quantity(3)
                        .price(100).build()).build();

        PricingRule bRule =  PricingRule.builder()
                .sku("B")
                .unitPrice(50)
                .specialPrice(SpecialPrice.builder()
                        .sku("B")
                        .quantity(2)
                        .price(80).build()).build();

        PricingRule cRule =  PricingRule.builder()
                .sku("C")
                .unitPrice(25).build();

        PricingRule dRule =  PricingRule.builder()
                .sku("D")
                .unitPrice(20).build();
        ;

        when(ruleService.fetch()).thenReturn( Arrays.asList(aRule,bRule, cRule, dRule));

    }
    PricingRuleService ruleService = mock(PricingRuleService.class);


    public int calculatePrice(String goods) {
        CheckOut co = new CheckOut(ruleService.fetch());
        for(int i=0; i<goods.length(); i++) {
            co.scan(String.valueOf(goods.charAt(i)));
        }
        return co.total();
    }
    @Test
    public void totals() {
        assertEquals(0, calculatePrice(""));
        assertEquals(40, calculatePrice("A"));
        assertEquals(90, calculatePrice("AB"));
        assertEquals(135, calculatePrice("CDBA"));
        assertEquals(80, calculatePrice("AA"));
        assertEquals(100, calculatePrice("AAA"));
        assertEquals(140, calculatePrice("AAAA"));
        assertEquals(180, calculatePrice("AAAAA"));
        assertEquals(200, calculatePrice("AAAAAA"));
        assertEquals(150, calculatePrice("AAAB"));
        assertEquals(180, calculatePrice("AAABB"));
        assertEquals(200, calculatePrice("AAABBD"));
        assertEquals(200, calculatePrice("DABABA"));
    }
    @Test
    public void incremental() {
        CheckOut co = new CheckOut(ruleService.fetch());
        assertEquals(0, co.total());
        co.scan("A"); assertEquals(40, co.total());
        co.scan("B"); assertEquals(90, co.total());
        co.scan("A"); assertEquals(130, co.total());
        co.scan("A"); assertEquals(150, co.total());
        co.scan("B"); assertEquals(180, co.total());
    }
}