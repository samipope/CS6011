package DNSResolver;

import java.util.HashMap;

public class DNSCache {
    private static HashMap<DNSQuestion, DNSRecord> cache = new HashMap<>();

    // Private constructor to prevent instantiation
    public DNSCache() {
        cache = new HashMap<>();
    }

    public static DNSRecord query(DNSQuestion question) {
        // check if the cache contains the question
        if (cache.containsKey(question)) {
            // if it does, check if the record is expired
            DNSRecord record = cache.get(question);
            // if the record is expired, remove it from the cache and return null
            if (record.isExpired()) {
                cache.remove(question);
                return null;
            }
            // if the record is not expired, return the record
            else {
                return record;
            }
        }
        else {
            // if the cache does not contain the question, return null
            return null;
        }
    }

    public static void insert(DNSQuestion question, DNSRecord record) {
        // only insert if the question is not already in the cache
        if (!cache.containsKey(question)) {
            cache.put(question, record);
        }
    }
}

