package de.viadee.cleancode.spacestation.modules.supply;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class CoffeeMakerTest {

    @Autowired
    CoffeeMaker coffeeMaker;

    @Test
    public void makeCoffeeTest(){
        Coffee coffee = coffeeMaker.makeCoffee(CoffeeType.COLD_BREW, true);
        Assert.assertEquals(1, coffee.getSugar());
    }

}
