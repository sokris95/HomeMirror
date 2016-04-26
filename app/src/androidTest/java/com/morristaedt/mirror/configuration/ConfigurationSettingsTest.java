package com.morristaedt.mirror.configuration;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.test.RenamingDelegatingContext;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ConfigurationSettingsTest {

    public interface SetData<T>{
        void set(ConfigurationSettings settings, T value);
    }

    public interface GetData<T>{
        T get(ConfigurationSettings settings);
    }

    Context context;

    @Before
    public void setUp() {
        //RenamingDelegatingContext prefixes the file/database names with test_ to prevent you from overwriting data that you may have in the same simulator.
        context = new RenamingDelegatingContext(InstrumentationRegistry.getInstrumentation().getTargetContext(), "test_");
    }

    /*
    Lambda expressions are not allowed before java 1.8
    java 1.8 is not supported before Android N
    therefore lambda expressions are not used here
    */

    @Test
    public void testIsCelsius(){
        Scenarios<Boolean> sc = new Scenarios<>(
                new GetData<Boolean>() {
                    @Override
                    public Boolean get(ConfigurationSettings settings) {
                        return settings.getIsCelsius();
                    }
                },
                new SetData<Boolean>() {
                    @Override
                    public void set(ConfigurationSettings settings, Boolean value){
                        settings.setIsCelsius(value);
                    }
                });

        sc.scenario1(false);
        sc.scenario1(true);
        sc.scenario2(false, true, false);
        sc.scenario2(false, false, true);
        sc.scenario2(true, false, false);
        sc.scenario2(true, false, true);
        sc.scenario3(true, false, true);
        sc.scenario3(false, true, true);
        sc.scenario3(true, false, true);
        sc.scenario3(true, true, false);
    }

    private class Scenarios<T> {
        private ConfigurationSettings configurationSettings;
        private GetData<T> getData;
        private SetData<T> setData;

        public Scenarios(GetData<T> getData, SetData<T> setData){
            this.getData=getData;
            this.setData=setData;
        }

        private void newSetting(){
            configurationSettings = new ConfigurationSettings(context);
        }
        private void set(T value){
            setData.set(configurationSettings, value);
        }
        private void check(T expected){
            assertEquals("Scenario failed, data was not saved properly",
                    expected, getData.get(configurationSettings));
        }

        public void scenario1(T value1) {
            newSetting();
            set(value1);
            newSetting();
            check(value1);
        }
        public void scenario2(T value1, T value2, T value3){
            newSetting();
            set(value1);
            newSetting();
            set(value2);
            newSetting();
            check(value2);
            set(value3);
            check(value3);
            newSetting();
            check(value3);
        }
        public void scenario3(T value1, T value2, T value3){
            newSetting();
            set(value1);
            set(value2);
            check(value2);
            newSetting();
            check(value2);
            check(value2);
            set(value1);
            check(value1);
            newSetting();
            set(value3);
            check(value3);
            check(value3);
            set(value2);
            newSetting();
            check(value2);
        }
    }
}
