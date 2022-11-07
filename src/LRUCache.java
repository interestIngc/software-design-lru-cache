import java.util.HashMap;
import java.util.Map;

public class LRUCache<K, V> {
    private Node<K> first = null;
    private Node<K> last = null;
    private final Map<K, Node<K>> nodes = new HashMap<>();
    private final Map<K, V> cache = new HashMap<>();
    private final int capacity;

    public LRUCache(int capacity) {
        assert capacity > 0;

        this.capacity = capacity;
    }

    public int size() {
        return cache.size();
    }

    public V get(K key) {
        assert key != null;

        if (!cache.containsKey(key)) {
            return null;
        }

        updateOrder(key);
        return cache.get(key);
    }

    public void put(K key, V value) {
        assert key != null && value != null;

        if (cache.containsKey(key)) {
            updateOrder(key);
        } else {
            if (size() == capacity) {
                cache.remove(first.key);
                delete(first);
            }
            addLast(key);
        }

        assert size() < capacity;

        cache.put(key, value);
    }

    private void updateOrder(K key) {
        int size = size();

        delete(nodes.get(key));
        addLast(key);

        assert size() == size;
    }

    private void delete(Node<K> node) {
        nodes.remove(node.key);

        if (node.prev != null) {
            node.prev.setNext(node.next);
        } else {
            first = node.next;
        }
        if (node.next != null) {
            node.next.setPrev(node.prev);
        } else {
            last = node.prev;
        }
    }

    private void addLast(K key) {
        Node<K> initialLast = last;

        Node<K> node = new Node<>(key, last);
        if (last != null) {
            last.setNext(node);
        }
        if (first == null) {
            first = node;
        }
        last = node;

        nodes.put(key, last);

        assert first != null && last != null && initialLast != last;
    }

    private static class Node<K> {
        K key;
        Node<K> prev;
        Node<K> next = null;

        public Node(K key, Node<K> prev) {
            this.key = key;
            this.prev = prev;
        }

        public void setNext(Node<K> next) {
            this.next = next;
        }

        public void setPrev(Node<K> prev) {
            this.prev = prev;
        }
    }
}
