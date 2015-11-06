import java.util.ArrayList;

public class Node {
	int depth;
	Node parent, leftchild, rightchild, upchild, downchild;
	ArrayList<Node> children = new ArrayList<Node>();
	State nodestate;
	
	public Node(Node parent, int depth, char direction) {
		this.depth = depth;
		this.parent = parent;
		nodestate = new State(parent.getState(), direction);
		createChildren();
	}
	
	public State getState() {
		return nodestate;
	}
	
	public void createChildren() {
		leftchild = new Node(this, depth + 1, 'L');
	}
}
