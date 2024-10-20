import java.util.*;

public class HashMapImp {

    static class HashMap<K, V> { /* generic */
        private class Node {
            K key;
            V value;

            public Node(K key, V value) {
                this.key = key;
                this.value = value;
            }
        }

        private int n; /* n-node */
        private int N; /* N-buckets */
        private LinkedList<Node>[] buckets; /* N-bucket.length */

        @SuppressWarnings("unchecked") // Removed semicolon here
        public HashMap() {
            this.N = 4;
            this.buckets = new LinkedList[4];
            for (int i = 0; i < 4; i++) {
                this.buckets[i] = new LinkedList<>();
            }
        }

        private int hashFunction(K key) {
            int bi = key.hashCode();
            return Math.abs(bi) % N;
        }

        public int searchLL(K key, int bi) {
            LinkedList<Node> ll = buckets[bi];
            for (int di = 0; di < ll.size(); di++) {
                if (ll.get(di).key.equals(key)) {
                    return di;
                }
            }
            return -1;
        }

        @SuppressWarnings("unchecked")
        private void rehash() {
            LinkedList<Node>[] oldBucket = buckets;
            buckets = new LinkedList[2 * N];
            N = 2 * N;

            for (int i = 0; i < N; i++) {
                buckets[i] = new LinkedList<>();
            }

            for (int bi = 0; bi < oldBucket.length; bi++) {
                LinkedList<Node> ll = oldBucket[bi];
                for (int di = 0; di < ll.size(); di++) {
                    Node node = ll.get(di);
                    put(node.key, node.value);
                }
            }
        }

        public void put(K key, V value) {
            int bi = hashFunction(key);
            int di = searchLL(key, bi);

            // di == -1 means key does not exist, else it exists
            if (di == -1) {
                buckets[bi].add(new Node(key, value));
                n++;
            } else {
                Node node = buckets[bi].get(di);
                node.value = value;
            }

            double lambda = (double) n / N;
            if (lambda > 2.0) {
                // rehashing if lambda > 2.0
                rehash();
            }
        }

        public boolean containsKey(K key) {
            int bi = hashFunction(key);
            int di = searchLL(key, bi);

            // di == -1 means key does not exist, else it exists
            return di != -1;
        }

        public V remove(K key) {
            int bi = hashFunction(key);
            int di = searchLL(key, bi);

            // di == -1 means key does not exist, else it exists
            if (di == -1) {
                return null;
            } else {
                Node node = buckets[bi].remove(di);
                n--;
                return node.value;
            }
        }

        public V get(K key) {
            int bi = hashFunction(key);
            int di = searchLL(key, bi);

            // di == -1 means key does not exist, else it exists
            if (di == -1) {
                return null;
            } else {
                Node node = buckets[bi].get(di);
                return node.value;
            }
        }

        public ArrayList<K> keySet() {
            ArrayList<K> keys = new ArrayList<>();
            for (int bi = 0; bi < buckets.length; bi++) {
                LinkedList<Node> ll = buckets[bi];
                for (int di = 0; di < ll.size(); di++) {
                    Node node = ll.get(di);
                    keys.add(node.key);
                }
            }
            return keys;
        }

        public boolean isEmpty() {
            return n == 0;
        }

    }

    public static void main(String args[]) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("India", 190);
        map.put("China", 200);
        map.put("US", 50);

        ArrayList<String> keys = map.keySet();
        for (int i = 0; i < keys.size(); i++) {
            System.out.println(keys.get(i) + " " + map.get(keys.get(i)));
        }
    }
}
