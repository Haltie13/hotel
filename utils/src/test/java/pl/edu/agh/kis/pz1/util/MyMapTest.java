package pl.edu.agh.kis.pz1.util;

import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

class MyMapTest {

    @org.junit.jupiter.api.Test
    void put() {
        final MyMap<String, Integer> map = new MyMap<>();
        final String KEY = "apple";
        final Integer VALUE = 5;
        assertNull(map.put(KEY, VALUE), "Expected null as map is empty");
        assertTrue(map.keys().contains(KEY), "Expected key contains apple");
        assertTrue(map.containsValue(VALUE), "Expected value contains 10");
        assertEquals(map.keys().indexOf(KEY), map.values().indexOf(VALUE), "Expected key contains apple");
    }

    @org.junit.jupiter.api.Test
    void get() {
        final MyMap<String, Integer> map = new MyMap<>();

        assertNull(map.get("null"), "Expected null as map is empty");

        for (int i = 0; i<10; i++){
            map.put(String.valueOf(i), i);
        }
        for (int i = 0; i<10; i++){
            assertEquals(map.get(String.valueOf(i)), i);
        }
    }

    @org.junit.jupiter.api.Test
    void size() {
        final MyMap<String, Integer> map = new MyMap<>();
        assertEquals(0, map.size());
        for (int i = 0; i<10; i++){
            map.put(String.valueOf(i), i);
            assertEquals(map.size(), i+1);
        }
        assertEquals( 10, map.size());
    }

    @org.junit.jupiter.api.Test
    void values() {
        final MyMap<String, Integer> map = new MyMap<>();
        final String[] KEYS = {"apple", "banana", "cherry", "date", "elderberry", "fig", "grape", "honeydew", "kiwi", "lemon"};
        final Integer[] VALUES = {10, 20, 30, 40, 50, 60, 70, 80, 90, 100};
        for (int i=0; i<VALUES.length; i++){
            map.put(KEYS[i], VALUES[i]);
        }
        List<Integer> mapValues = map.values();
        Collections.sort(mapValues);
        final List<Integer> VALUES_LIST = new ArrayList<>(Arrays.asList(VALUES));
        assertEquals(mapValues, VALUES_LIST);
    }

    @org.junit.jupiter.api.Test
    void keys() {
        final MyMap<String, Integer> map = new MyMap<>();
        final String[] KEYS = {"apple", "banana", "cherry", "date", "elderberry", "fig", "grape", "honeydew", "kiwi", "lemon"};
        final Integer[] VALUES = {10, 20, 30, 40, 50, 60, 70, 80, 90, 100};
        for (int i=0; i<VALUES.length; i++){
            map.put(KEYS[i], VALUES[i]);
        }
        List<String> mapKeys = map.keys();
        Collections.sort(mapKeys);
        final List<String> KEYS_LIST = new ArrayList<>(Arrays.asList(KEYS));
        assertEquals(mapKeys, KEYS_LIST);
    }



    @org.junit.jupiter.api.Test
    void isEmpty() {
        final MyMap<String, Integer> map = new MyMap<>();
        assertTrue(map.isEmpty(), "Expected empty map is empty");
    }
}