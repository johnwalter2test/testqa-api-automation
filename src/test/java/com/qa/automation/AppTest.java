package com.qa.automation;

import org.testng.Assert;
import org.testng.annotations.Test;

public class AppTest {

    @Test
    public void testGetGreeting() {
        String g = App.getGreeting();
        Assert.assertEquals(g, "Hello, Maven!");
    }
}
