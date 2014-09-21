/*LockDList*/
package list;
public class LockDList extends DList {
 
    @Override
    protected DListNode newNode(Object item, DListNode prev, DListNode next) {
        return new LockDListNode(item, prev, next);
    }
 
    public void lockNode(DListNode node) {
        ((LockDListNode) node)._locked = true;
    }
 
    public void deLockNode(DListNode node) {
        ((LockDListNode) node)._locked = false;
    }
 
    @Override
    public void remove(DListNode node) {
        if (((LockDListNode) node)._locked || node == null)
            return;
    }
        super.remove(node);
    }
}