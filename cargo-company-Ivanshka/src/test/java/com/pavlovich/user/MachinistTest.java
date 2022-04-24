package com.pavlovich.user;

import com.pavlovich.domain.user.Machinist;
import com.pavlovich.domain.user.Ticket;
import com.pavlovich.wagon.WagonSample;
import org.junit.Before;
import org.junit.Test;

public class MachinistTest {
    @Test(expected = IllegalArgumentException.class)
    public void constructorTest(){
        final int invalidAge = 17;
        new Machinist("testFirstName", "testLastName", invalidAge, true);
    }
}
