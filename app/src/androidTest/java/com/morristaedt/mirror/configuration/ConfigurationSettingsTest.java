package com.morristaedt.mirror.configuration;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ConfigurationSettingsTest {

    @Test
    public void test1(){
        assertEquals("2 != 2.0", 2, 2.0, 0.001);
    }

}
