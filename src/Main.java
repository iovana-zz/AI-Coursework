import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Stack;
import java.util.HashMap;

public class Main {
	static int n = 3;
	char[][] matrix = new char[n][n];
	char tileA = 'A', tileB = 'B', tileC = 'C', tileO = 'O', tileN = 'N';
	//static HashMap<String, Integer> existingStates = new HashMap<String, Integer>();
	
	/*
	 * ArrayList<Node> discovered = new ArrayList<Node>();
	 */

/*	public static boolean addState(String m) {
		if (existingStates.containsKey(m)) {
			return false;
		}
		existingStates.put(m, 1);
		return true;
	}
	*/
	
	public void controlDepth(int difficulty, State startstate) {
		char lastcharacter = 'z';
		while(difficulty > 0) {
			ArrayList<Character> moves = new ArrayList<Character>();
			moves.addAll(Arrays.asList(new Character[]{'L','U','R','D'}));
			moves.remove(new Character(lastcharacter));
			int currentX = startstate.getAgentx();
			int currentY = startstate.getAgenty();
			
			if(currentX == 0) {
				moves.remove(new Character ('L'));
			}
			if(currentX == n-1) {
				moves.remove(new Character ('R'));
			}
			if(currentY == 0) {
				moves.remove(new Character ('U'));
			}
			if(currentY == n-1) {
				moves.remove(new Character ('D'));
			}
			int i = new Random().nextInt(moves.size());
			char direction = moves.get(i);
			startstate.moveDirection(direction);
	
			switch (direction) {
			case 'L':
				lastcharacter = 'R';
				break;
			case 'R':
				lastcharacter = 'L';
				break;
			case 'U':
				lastcharacter = 'D';
				break;
			case 'D':
				lastcharacter = 'U';
				break;
			default:
				break;
			}
			
			difficulty--;
		}
	}

	public void assign() {
		char[] tiles = new char[]{'A','B','C','D','E','F','G','H'};
		int counter = 0;
		matrix[2][2] = tileO;
		for (int j = 0; j < n; j++) {
			for (int i = 0; i < n; i++) {
				if(matrix[i][j] != tileO) {
					matrix[i][j] = tiles[counter];
					++counter;
				}
			}
		}
		
		State startstate = new State(matrix, 2, 2);
		controlDepth(4, startstate);
		print(startstate.getParentMatrix());
		Node startNode = new Node(startstate);
		/*
		addState(startstate.hashString);
		*/

		//breadthFirstSearch(startNode);
		//depthFirstSearch(startNode);
		iterativeDeepening(startNode);

	}

	public void aSearch(Node start, Node goal) {
		ArrayList<Node> closed, open;
		Map<Node, Integer> came_from;
		PriorityQueue<Node> queue;
		
	}

	public void reconstruct_path() {

	}

	public void breadthFirstSearch(Node start) {
		LinkedList<Node> queue = new LinkedList<Node>();
		queue.add(start);
		while (!queue.isEmpty()) {
			Node n = queue.remove();
			if (n.getState().checkGoalState()) {
				break;
			}
			ArrayList<Node> children = n.createChildren();
			for (Node child : children) {
				queue.add(child);
			}
		}

	}

	public void depthFirstSearch(Node start) {
		Stack<Node> s = new Stack<Node>();
		s.push(start);
		while (!s.isEmpty()) {
			Node n = s.pop();
			if (n.getState().checkGoalState()) {
				break;
			}
			if (!n.discovered) {
				n.discovered = true;
				ArrayList<Node> children = n.createChildren();
				for (Node child : children) {
					s.push(child);
				}
			}
		}
	}

	public Node iterativeDeepening(Node root) {
		for (int depth = 0; depth < 1000; depth++) {
			Node found = DLS(root, depth);
			if (found != null) {
				return found;
			}
		}
		return null;
	}

	public Node DLS(Node n, int depth) {
		if (depth == 0 && n.getState().checkGoalState()) {
			return n;
		} else if (depth > 0) {
			ArrayList<Node> children = n.createChildren();
			for (Node child : children) {
				Node found = DLS(child, depth - 1);
				if (found != null) {
					return found;
				}
			}
		}
		return null;
	}

	public static void print(char[][] amatrix) {
		for (int j = 0; j < n; j++) {
			for (int i = 0; i < n; i++) {
				System.out.print(amatrix[i][j]);
			}
			System.out.println();
		}
		System.out.println();
	}

	public static void main(String[] args) {
		Main s = new Main();
		s.assign();
	}
}
