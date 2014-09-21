/*LockDListNode*/
package list;
public class LockDListNode extends list.DListNode {
	public LockDListNode(Object i, DListNode p, DListNode n) {
		super(i, p, n);
	}
	protected boolean _locked = false;
}
