/* HashTableChained.java */

package dict;
import list.DList;
import list.InvalidNodeException;
import list.ListNode;
/**
 *  HashTableChained implements a Dictionary as a hash table with chaining.
 *  All objects used as keys must have a valid hashCode() method, which is
 *  used to determine which bucket of the hash table an entry is stored in.
 *  Each object's hashCode() is presumed to return an int between
 *  Integer.MIN_VALUE and Integer.MAX_VALUE.  The HashTableChained class
 *  implements only the compression function, which maps the hash code to
 *  a bucket in the table's range.
 *
 *  DO NOT CHANGE ANY PROTOTYPES IN THIS FILE.
 **/

public class HashTableChained implements Dictionary {

  /**
   *  Place any data fields here.
     **/
    protected DList[] table;
    protected int buckets;
    protected int size;


  /** 
   *  Construct a new empty hash table intended to hold roughly sizeEstimate
   *  entries.  (The precise number of buckets is up to you, but we recommend
   *  you use a prime number, and shoot for a load factor between 0.5 and 1.)
   **/

  public HashTableChained(int sizeEstimate) {
    buckets = Math.abs(sizeEstimate) + Math.abs(sizeEstimate) / 4;
    while (!isPrime(buckets)) {
      buckets++;
    }
    size = 0;
    initBuckets();  
  }

  /** 
   *  Construct a new empty hash table with a default size.  Say, a prime in
   *  the neighborhood of 100.
   **/

  public HashTableChained() {
    buckets = 101;
    size = 0;
    initBuckets();  
  }

  /**
   *  Converts a hash code in the range Integer.MIN_VALUE...Integer.MAX_VALUE
   *  to a value in the range 0...(size of hash table) - 1.
   *
   *  This function should have package protection (so we can test it), and
   *  should be used by insert, find, and remove.
   **/

  int compFunction(int code) {
    long comp = ((127 * code + 65993) % 1999993) % buckets;
    if (comp < 0) {
      comp = buckets;
    }
    return (int) comp;
  }

  /** 
   *  Returns the number of entries stored in the dictionary.  Entries with
   *  the same key (or even the same key and value) each still count as
   *  a separate entry.
   *  @return number of entries in the dictionary.
   **/

  public int size() {
    return size;
  }

  /** 
   *  Tests if the dictionary is empty.
   *
   *  @return true if the dictionary has no entries; false otherwise.
   **/

  public boolean isEmpty() {
    if(size == 0) {
      return true;
    }
    return false;
  }

  /**
   *  Create a new Entry object referencing the input key and associated value,
   *  and insert the entry into the dictionary.  Return a reference to the new
   *  entry.  Multiple entries with the same key (or even the same key and
   *  value) can coexist in the dictionary.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the key by which the entry can be retrieved.
   *  @param value an arbitrary object.
   *  @return an entry containing the key and value.
   **/

  public Entry insert(Object key, Object value) {
    if (key == null) {
      return null;
    }
    int bucket = compFunction(key.hashCode());
    Entry entry = new Entry();
    entry.key = key;
    entry.value = value;
    table[bucket].insertFront(entry);
    size++;
    return entry;
    }

  /** 
   *  Search for an entry with the specified key.  If such an entry is found,
   *  return it; otherwise return null.  If several entries have the specified
   *  key, choose one arbitrarily and return it.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the search key.
   *  @return an entry containing the key and an associated value, or null if
   *          no entry contains the specified key.
   **/

  public Entry find(Object key) {
    int bucket = compFunction(key.hashCode());
    ListNode node = table[bucket].front();
    while (node.isValidNode()) {
      try {
        Entry entry = (Entry) node.item();
        if (entry != null && key.equals(entry.key)) {
          return entry;
        }
        node = node.next();
      } catch (InvalidNodeException ex) {
        System.out.println("fail in find func");
      }
    }
    return null;
  }

  /** 
   *  Remove an entry with the specified key.  If such an entry is found,
   *  remove it from the table and return it; otherwise return null.
   *  If several entries have the specified key, choose one arbitrarily, then
   *  remove and return it.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the search key.
   *  @return an entry containing the key and an associated value, or null if
   *          no entry contains the specified key.
   */

  public Entry remove(Object key) {
    int bucket = compFunction(key.hashCode());
    ListNode node = table[bucket].front();
    while (node.isValidNode()) {
      try {
        Entry entry = (Entry) node.item();
        if (entry != null && key.equals(entry.key)) {
          node.remove();
          size--;
          return entry;
        }
        node = node.next();
      } catch (InvalidNodeException ex) {
          System.out.println("this is an error remove func");
      }
    }
    return null;
  }

  /**
   *  Remove all entries from the dictionary.
   */
  public void makeEmpty() {
    size = 0;
    initBuckets();  
  }
  private static boolean isPrime(long n) {
    if ((n % 2) == 0) {
      return false;
    }
    long start = 2;
    long end = (long) Math.ceil(Math.sqrt(n));
    for (long i = start; i < end; i++) {
      if (n % i == 0) {
        return false;
      }
    }
    return true;
  }
  private void initBuckets() {
    table = new DList[buckets];
    for (int i = 0; i < buckets; i++) {
      table[i] = new DList();
    }
  }
  public void countColl() {
    int collisions = 0;
    int longestChain = -1;
    int sumLengths = 0;
    for (int i = 0; i < buckets; i++) {
      int len = table[i].length();
      if (len > longestChain){
        longestChain = len;
      }
      sumLengths += len;
      if (len > 1) {
        collisions += len - 1;
      }
    }
    System.out.println("The number of collisions is" + collisions);
  }
}