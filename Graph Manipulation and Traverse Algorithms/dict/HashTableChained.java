/* HashTableChained.java */

package dict;
import list.*;

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
  private int buckets;
  private int size;
  private int collisions;
  private double loadFactor;


  /**
   *  Construct a new empty hash table intended to hold roughly sizeEstimate
   *  entries.  (The precise number of buckets is up to you, but we recommend
   *  you use a prime number, and shoot for a load factor between 0.5 and 1.)
   **/

  public HashTableChained(int sizeEstimate) {
    this.buckets = sizeEstimate;
    while (!isPrime(buckets)) {
      buckets++;
    }
    this.table = new DList[buckets];
    size = 0;
  }

  /**
    * isPrime determines whether a number n is a prime number.
    * @param n is an integer that we want to determine is prime or not
    * @return is a boolean that is true if n is prime and false otherwise.
  */
  private boolean isPrime(int n) {
    int divisor = 2;
    while (divisor == 2) {
      if (n%divisor == 0) {
        return false;
      }
      divisor++;
    }
    return true;
  }
  /**
   *  Construct a new empty hash table with a default size.  Say, a prime in
   *  the neighborhood of 100.
   **/

  public HashTableChained() {
    buckets = 101;
    table = new DList[buckets];
    size = 0;
  }

  /**
   *  Converts a hash code in the range Integer.MIN_VALUE...Integer.MAX_VALUE
   *  to a value in the range 0...(size of hash table) - 1.
   *
   *  This function should have package protection (so we can test it), and
   *  should be used by insert, find, and remove.
   **/

  int compFunction(int code) {
    int p = buckets*buckets*buckets;
    while (!isPrime(p)) {
      p++;
    }
    int value = (((buckets-code)*code+(buckets+code))%p) % buckets;
    if (value<0) {
      value = value + buckets;
    }
    return value;
  }

  /**
    * getCollisions returns the number of collisions that have occured in "this" hash table.
    * @return an integer representing the number of collisions
  */
  public int getCollisions() {
    return collisions;
  }

  /**
    * loadFactor tells you the loadFactor of "this" hashTable which is the number of entries
    * inserted in the hash table as a percentage of the number of "buckets" to be hashed to.
    * @return is a double that represents the load factor as a decimal value.
  */
  public double loadFactor() {
    return (double) size/ (double) buckets;
  }

  /**
    * resize doubles the size of the hash table when the load factor is too large in order
    * to reduce the load factor so that constant time operations are maintained.
  */
  private void resize() {
    if (loadFactor()>=1) {
      DList[] newTable = new DList[2*buckets];
      DList[] oldTable = table;
      table = newTable;
      buckets = buckets*2;
      try {
        for (int x = 0; x<oldTable.length; x++) {
          if (oldTable[x] != null) {
            DListNode temp = (DListNode) oldTable[x].front();
            while (temp.item() !=null){
              Entry entry = (Entry) temp.item();
              this.insert(entry.key(), entry.value());
              temp = (DListNode) temp.next();
            }
          }
        }
      } catch (InvalidNodeException e) {}
    }
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
    int count = 0;
    for (int x = 0; x<buckets; x++) {
      if (table[x] != null) {
        return false;
      }
    }
    return true;
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
    int hashVal = key.hashCode();
    int index = compFunction(hashVal);
    if (table[index] == null) {
      table[index] = new DList();
    } else {
      collisions++;
    }
    Entry entry = new Entry();
    entry.key = key;
    entry.value = value;
    table[index].insertBack(entry);
    size++;
    if (this.loadFactor()>=1) {
      this.resize();
    }
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
    try {
      int hashVal = key.hashCode();
      int index = compFunction(hashVal);
      if (table[index] != null) {
        DListNode temp = (DListNode) table[index].front();
        if (temp.item() != null) {
          while ((!((((Entry) temp.item()).key()).equals(key)))) {
            temp = (DListNode) temp.next();
            if (temp.item() == null) {
              return null;
            }
          }
        return (Entry) temp.item();
        }
      }
    } catch (InvalidNodeException e) {}
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
    Entry entry = find(key);
    if (entry == null) {
      return null;
    } else {
      try {
        int index = compFunction(key.hashCode());
        if (table[index].front().next() != null) {
          collisions--;
        }
        DListNode temp = (DListNode) table[index].front();
        while ((!((((Entry) temp.item()).key()).equals(key)))) {
          temp = (DListNode) temp.next();
          if (temp.item() == null) {
            return null;
          }
        }
        temp.remove();
        size--;
      } catch (InvalidNodeException e) {}
    }
    return entry;
  }

  /**
   *  Remove all entries from the dictionary.
   */
  public void makeEmpty() {
    for (int x = 0; x<buckets; x++) {
      if (table[x] != null) {
        table[x] = null;
        collisions = 0;
        size = 0;
      }
    }
  }

}
