import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;


public class LRUCacheTest {
    private static final Integer KEY_1 = 1;
    private static final Integer KEY_2 = 2;
    private static final Integer KEY_3 = 3;
    private static final Integer VALUE_1 = 4;
    private static final Integer VALUE_2 = 5;
    private static final Integer VALUE_3 = 6;
    private static final int CAPACITY = 2;

    @Test(expected = AssertionError.class)
    public void constructor_capacityLessThanZero_throwsAssertionError() {
        LRUCache<Integer, Integer> lruCache = new LRUCache<>(-1);
    }

    @Test
    public void get_elementDoesNotExist_returnsNull() {
        LRUCache<Integer, Integer> lruCache = new LRUCache<>(CAPACITY);

        assertNull(lruCache.get(KEY_1));
    }

    @Test
    public void put_validInput_putsCorrectly() {
        LRUCache<Integer, Integer> lruCache = new LRUCache<>(CAPACITY);

        lruCache.put(KEY_1, VALUE_1);

        Integer actualValue = lruCache.get(KEY_1);

        assertEquals(VALUE_1, actualValue);
    }

    @Test
    public void put_capacityExceeded_firstElementsThrottled() {
        LRUCache<Integer, Integer> lruCache = new LRUCache<>(CAPACITY);

        lruCache.put(KEY_1, VALUE_1);
        lruCache.put(KEY_2, VALUE_2);
        lruCache.put(KEY_3, VALUE_3);

        Integer actualValue1 = lruCache.get(KEY_1);
        Integer actualValue2 = lruCache.get(KEY_2);
        Integer actualValue3 = lruCache.get(KEY_3);

        assertNull(actualValue1);
        assertEquals(VALUE_2, actualValue2);
        assertEquals(VALUE_3, actualValue3);
    }

    @Test
    public void size_oneElement_sizeOne() {
        LRUCache<Integer, Integer> lruCache = new LRUCache<>(CAPACITY);

        lruCache.put(KEY_1, VALUE_1);

        assertEquals(1, lruCache.size());
    }
}
