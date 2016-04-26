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

    public class Pair<T, V>{
        public T first;
        public V second;
        public Pair(T first, V second){
            this.first = first;
            this.second = second;
        }
        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (!Pair.class.isAssignableFrom(obj.getClass())) {
                return false;
            }
            final Pair other = (Pair) obj;
            if ((this.first == null) ? (other.first != null) : !this.first.equals(other.first)){
                return false;
            }
            if ((this.second == null) ? (other.second != null) : !this.second.equals(other.second)){
                return false;
            }
            return true;
        }
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

    private void testBoolean(GetData<Boolean> getData, SetData<Boolean> setData){
        Scenarios<Boolean> sc = new Scenarios<>(getData, setData);

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

    @Test
    public void testIsCelsius(){
        testBoolean(
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
    }

    @Test
    public void testShowBikingHint(){
        testBoolean(
                new GetData<Boolean>() {
                    @Override
                    public Boolean get(ConfigurationSettings settings) {
                        return settings.showBikingHint();
                    }
                },
                new SetData<Boolean>() {
                    @Override
                    public void set(ConfigurationSettings settings, Boolean value){
                        settings.setShowBikingHint(value);
                    }
                });
    }

    @Test
    public void testShowMoodDetection(){
        testBoolean(
                new GetData<Boolean>() {
                    @Override
                    public Boolean get(ConfigurationSettings settings) {
                        return settings.showMoodDetection();
                    }
                },
                new SetData<Boolean>() {
                    @Override
                    public void set(ConfigurationSettings settings, Boolean value){
                        settings.setShowMoodDetection(value);
                    }
                });
    }

    @Test
    public void testShowNextCalendarEvent(){
        testBoolean(
                new GetData<Boolean>() {
                    @Override
                    public Boolean get(ConfigurationSettings settings) {
                        return settings.showNextCalendarEvent();
                    }
                },
                new SetData<Boolean>() {
                    @Override
                    public void set(ConfigurationSettings settings, Boolean value){
                        settings.setShowNextCalendarEvent(value);
                    }
                });
    }

    @Test
    public void testShowNewsHeadline(){
        testBoolean(
                new GetData<Boolean>() {
                    @Override
                    public Boolean get(ConfigurationSettings settings) {
                        return settings.showNewsHeadline();
                    }
                },
                new SetData<Boolean>() {
                    @Override
                    public void set(ConfigurationSettings settings, Boolean value){
                        settings.setShowNewsHeadline(value);
                    }
                });
    }

    @Test
    public void testXKCDPreference(){
        Scenarios<Pair<Boolean, Boolean>> sc = new Scenarios<>(
                new GetData<Pair<Boolean, Boolean>>() {
                    @Override
                    public Pair<Boolean, Boolean> get(ConfigurationSettings settings) {
                        return new Pair<>(
                                settings.showXKCD(), settings.invertXKCD());
                    }
                },
                new SetData<Pair<Boolean, Boolean>>() {
                    @Override
                    public void set(ConfigurationSettings settings, Pair<Boolean, Boolean> value){
                        settings.setXKCDPreference(value.first, value.second);
                    }
                });

        sc.scenario1(new Pair<>(true, true));
        sc.scenario1(new Pair<>(true, false));
        sc.scenario1(new Pair<>(false, true));
        sc.scenario1(new Pair<>(false, false));

        sc.scenario2(new Pair<>(true, false),
                new Pair<>(false, true),
                new Pair<>(true, false));
        sc.scenario2(new Pair<>(true, true),
                new Pair<>(false, true),
                new Pair<>(false, false));
        sc.scenario2(new Pair<>(false, false),
                new Pair<>(false, true),
                new Pair<>(true, false));
        sc.scenario2(new Pair<>(true, false),
                new Pair<>(false, false),
                new Pair<>(true, true));

        sc.scenario3(new Pair<>(true, false),
                new Pair<>(false, true),
                new Pair<>(true, false));
        sc.scenario3(new Pair<>(true, true),
                new Pair<>(false, true),
                new Pair<>(false, false));
        sc.scenario3(new Pair<>(false, false),
                new Pair<>(false, true),
                new Pair<>(true, false));
        sc.scenario3(new Pair<>(true, false),
                new Pair<>(false, false),
                new Pair<>(true, true));
    }

    @Test
    public void testLatLon(){
        Scenarios<Pair<String, String>> sc = new Scenarios<>(
                new GetData<Pair<String, String>>() {
                    @Override
                    public Pair<String, String> get(ConfigurationSettings settings) {
                        return new Pair<String, String>(
                                settings.getLatitude(), settings.getLongitude());
                    }
                },
                new SetData<Pair<String, String>>() {
                    @Override
                    public void set(ConfigurationSettings settings, Pair<String, String> value){
                        settings.setLatLon(value.first, value.second);
                    }
                });

        sc.scenario1(new Pair<>("0", "0"));
        sc.scenario1(new Pair<>("10", "-10.2"));
        sc.scenario1(new Pair<>("-2.54", "60.0"));

        sc.scenario1(new Pair<>("40.93250", "80.70463"));
        sc.scenario1(new Pair<>("1.72060", "-104.95282"));
        sc.scenario1(new Pair<>("26.87761", "47.93233"));

        sc.scenario2(new Pair<>("42.41434", "-16.24200"),
                new Pair<>("28.16147", "-23.70242"),
                new Pair<>("-61.85165", "-22.67880"));
        sc.scenario2(new Pair<>("74.32061", "-61.20003"),
                new Pair<>("-22.27689", "-100.10861"),
                new Pair<>("-4.36025", "-90.68043"));
        sc.scenario2(new Pair<>("26.32571", "-139.40015"),
                new Pair<>("59.16843", "93.28736"),
                new Pair<>("-3.96659", "-10.17476"));

        sc.scenario3(new Pair<>("42.41434", "-16.24200"),
                new Pair<>("28.16147", "-23.70242"),
                new Pair<>("-61.85165", "-22.67880"));
        sc.scenario3(new Pair<>("74.32061", "-61.20003"),
                new Pair<>("-22.27689", "-100.10861"),
                new Pair<>("-4.36025", "-90.68043"));
        sc.scenario3(new Pair<>("26.32571", "-139.40015"),
                new Pair<>("59.16843", "93.28736"),
                new Pair<>("-3.96659", "-10.17476"));
    }

    @Test
    public void testStockTickerSymbol(){
        Scenarios<String> sc = new Scenarios<>(
                new GetData<String>() {
                    @Override
                    public String get(ConfigurationSettings settings) {
                        return settings.getStockTickerSymbol();
                    }
                },
                new SetData<String>() {
                    @Override
                    public void set(ConfigurationSettings settings, String value){
                        settings.setStockTickerSymbol(value);
                    }
                });

        sc.scenario1("GO, EOR, SYY");
        sc.scenario1("SIOE");
        sc.scenario1("SUE,SUU,SYY");
        sc.scenario1("dkiei93>>dh");

        sc.scenario2("dfjue9-xos;",
                "dskie00w\'\'l",
                "dke9wpwl");
        sc.scenario2("DUIE, DUE, SYYS",
                "SID,DY, DYY",
                "SDUW, XHYI, DCD");
        sc.scenario2("DUEUIW, HDY",
                "DUI",
                "DDE,DHYD");

        sc.scenario3("dfjue9-xos;",
                "dskie00w\'\'l",
                "dke9wpwl");
        sc.scenario3("DUIE, DUE, SYYS",
                "SID,DY, DYY",
                "SDUW, XHYI, DCD");
        sc.scenario3("DUEUIW, HDY",
                "DUI",
                "DDE,DHYD");
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
