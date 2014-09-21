
/* ListSorts.java */

import list.*;
import java.util.Random;
public class ListSorts {

  private final static int SORTSIZE = 1000000;

  /**
   *  makeQueueOfQueues() makes a queue of queues, each containing one item
   *  of q.  Upon completion of this method, q is empty.
   *  @param q is a LinkedQueue of objects.
   *  @return a LinkedQueue containing LinkedQueue objects, each of which
   *    contains one object from q.
   **/
  public static LinkedQueue makeQueueOfQueues(LinkedQueue q) {
    LinkedQueue mq = new LinkedQueue();
    while(q.size() > 0) {
      try{
        LinkedQueue ad = new LinkedQueue();
        ad.enqueue(q.dequeue());
        mq.enqueue(ad);
      } catch (QueueEmptyException e) {
        System.out.println("you are kidding me.");
      }
    }
    return mq;
  }

  /**
   *  mergeSortedQueues() merges two sorted queues into a third.  On completion
   *  of this method, q1 and q2 are empty, and their items have been merged
   *  into the returned queue.
   *  @param q1 is LinkedQueue of Comparable objects, sorted from smallest 
   *    to largest.
   *  @param q2 is LinkedQueue of Comparable objects, sorted from smallest 
   *    to largest.
   *  @return a LinkedQueue containing all the Comparable objects from q1 
   *   and q2 (and nothing else), sorted from smallest to largest.
   **/
  public static LinkedQueue mergeSortedQueues(LinkedQueue q1, LinkedQueue q2) {
    LinkedQueue mq = new LinkedQueue();
    Comparable qq1;
    Comparable qq2;
    Comparable l = null;
    LinkedQueue temp = null;
    while(temp == null || temp.size() > 0){
      try{
        if(l == null){
          qq1 = (Comparable) q1.dequeue();
          qq2 = (Comparable) q2.dequeue();
        }else{
          if(temp == q1){
            qq1 = (Comparable) temp.dequeue();
            qq2 = l;
          } else {
            qq1 = l;
            qq2 = (Comparable) temp.dequeue();
          }
        }
        if((qq1.compareTo(qq2) <= 0)) {
            mq.enqueue(qq1);
            l = qq2;
            temp = q1; 
          } else {
            mq.enqueue(qq2);
            l = qq1;
            temp = q2;
          }
      } catch(QueueEmptyException e){
        System.out.println("what happened?");
      }
    }
    if(l != null){
      mq.enqueue(l);
    }
    if(q1.size() == 0) {
     mq.append(q2);
    } else {
     mq.append(q1);
    }
    return mq;
  }

  /**
   *  partition() partitions qIn using the pivot item.  On completion of
   *  this method, qIn is empty, and its items have been moved to qSmall,
   *  qEquals, and qLarge, according to their relationship to the pivot.
   *  @param qIn is a LinkedQueue of Comparable objects.
   *  @param pivot is a Comparable item used for partitioning.
   *  @param qSmall is a LinkedQueue, in which all items less than pivot
   *    will be enqueued.
   *  @param qEquals is a LinkedQueue, in which all items equal to the pivot
   *    will be enqueued.
   *  @param qLarge is a LinkedQueue, in which all items greater than pivot
   *    will be enqueued.  
   **/   
  public static void partition(LinkedQueue qIn, Comparable pivot, 
                               LinkedQueue qSmall, LinkedQueue qEquals, 
                               LinkedQueue qLarge) {
    while (qIn.size() > 0) {
      try {
        Comparable temp = (Comparable) qIn.dequeue();
        if (temp.compareTo(pivot) < 0) {
          qSmall.enqueue(temp);
        } else if (temp.compareTo(pivot) == 0) {
          qEquals.enqueue(temp);
        } else {
          qLarge.enqueue(temp);
        } 
      } catch (QueueEmptyException e) {
        System.out.println("Managed to throw a QueueEmptyException despite having size > 0. What the hell.");
      }
    }
    if (qLarge.size() > 1) {
      quickSort(qLarge);
    }
    if (qSmall.size() > 1) {
      quickSort(qSmall);
    }
    
    qIn.append(qSmall);
    qIn.append(qEquals);
    qIn.append(qLarge);
  }

  /**
   *  mergeSort() sorts q from smallest to largest using mergesort.
   *  @param q is a LinkedQueue of Comparable objects.
   **/
  public static void mergeSort(LinkedQueue q) {
    LinkedQueue nq = makeQueueOfQueues(q);
    while(1 < nq.size()){
      try{
        LinkedQueue q1 = (LinkedQueue) nq.dequeue();
        LinkedQueue q2 = (LinkedQueue) nq.dequeue();
        nq.enqueue(mergeSortedQueues(q1, q2));
      }catch(QueueEmptyException e){
        System.out.println("buttt why?/");
      }
    }
    try{
      q.append((LinkedQueue) nq.dequeue());
    } catch (QueueEmptyException e) {
      System.out.println("its the last one");
    }
  }

  /**
   *  quickSort() sorts q from smallest to largest using quicksort.
   *  @param q is a LinkedQueue of Comparable objects.
   **/
  public static void quickSort(LinkedQueue q) {
    if (q.size() > 0) {
      Random p = new Random();
      int w = p.nextInt((q.size() - 1) + 1);
      Comparable pivot = (Comparable) q.nth(w);
      partition(q, pivot, new LinkedQueue(), new LinkedQueue(), new LinkedQueue());
    }
  }

  /**
   *  makeRandom() builds a LinkedQueue of the indicated size containing
   *  Integer items.  The items are randomly chosen between 0 and size - 1.
   *  @param size is the size of the resulting LinkedQueue.
   **/
  public static LinkedQueue makeRandom(int size) {
    LinkedQueue q = new LinkedQueue();
    for (int i = 0; i < size; i++) {
      q.enqueue(new Integer((int) (size * Math.random())));
    }
    return q;
  }

  /**
   *  main() performs some tests on mergesort and quicksort.  Feel free to add
   *  more tests of your own to make sure your algorithms works on boundary
   *  cases.  Your test code will not be graded.
   **/
  public static void main(String [] args) {

    LinkedQueue q = makeRandom(10);
    System.out.println(q.toString());
    mergeSort(q);
    System.out.println(q.toString());

    q = makeRandom(10);
    System.out.println(q.toString());
    quickSort(q);
    System.out.println(q.toString());


    Timer stopWatch = new Timer();
    q = makeRandom(SORTSIZE);
    stopWatch.start();
    mergeSort(q);
    stopWatch.stop();
    System.out.println("Mergesort time, " + SORTSIZE + " Integers:  " +
                       stopWatch.elapsed() + " msec.");

    stopWatch.reset();
    q = makeRandom(SORTSIZE);
    stopWatch.start();
    quickSort(q);
    stopWatch.stop();
    System.out.println("Quicksort time, " + SORTSIZE + " Integers:  " +
                       stopWatch.elapsed() + " msec.");

  }

}
