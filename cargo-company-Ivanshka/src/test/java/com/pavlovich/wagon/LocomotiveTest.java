package com.pavlovich.wagon;

import com.pavlovich.domain.user.Machinist;
import com.pavlovich.domain.wagon.Locomotive;
import org.junit.Before;
import org.junit.Test;

public class LocomotiveTest {
    private Locomotive locomotive;

    @Before
    public void before(){
        locomotive = WagonSample.getLocomotive();
    }

    @Test(expected = NullPointerException.class)
    public void setNullMachinistTest(){
        locomotive.setMachinist(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setInvalidMachinistTest() {
        Machinist machinist = WagonSample.getMachinist(false);
        locomotive.setMachinist(machinist);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorTest(){
        new Locomotive(WagonSample.DEFAULT_WAGON_NUMBER, WagonSample.getMachinist(false));
    }
}
