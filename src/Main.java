import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Stack;

import java.util.HashMap;

public class Main {
	static int n = 3;
	char tileA = 'A', tileB = 'B', tileC = 'C', tileO = 'O', tileN = 'N';
	float dfscount = 0, bfscount = 0, itdcount = 0, astarcount = 0;
	// static HashMap<String, Integer> existingStates = new HashMap<String,
	// Integer>();

	/*
	 * ArrayList<Node> discovered = new ArrayList<Node>();
	 */

	/*
	 * public static boolean addState(String m) { if
	 * (existingStates.containsKey(m)) { return false; } existingStates.put(m,
	 * 1); return true; }
	 */

	public void controlDepth(int difficulty, State startstate) {
		char lastcharacter = 'z';
		while (difficulty > 0) {
			ArrayList<Character> moves = new ArrayList<Character>();
			moves.addAll(Arrays.asList(new Character[] { 'L', 'U', 'R', 'D' }));
			moves.remove(new Character(lastcharacter));
			int currentX = startstate.getAgentx();
			int currentY = startstate.getAgenty();

			if (currentX == 0) {
				moves.remove(new Character('L'));
			}
			if (currentX == n - 1) {
				moves.remove(new Character('R'));
			}
			if (currentY == 0) {
				moves.remove(new Character('U'));
			}
			if (currentY == n - 1) {
				moves.remove(new Character('D'));
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

	public char[][] getInitialMatrix() {
		char[] tiles = new char[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H' };
		int counter = 0;
		char[][] matrix = new char[n][n];
		matrix[2][2] = tileO;
		for (int j = 0; j < n; j++) {
			for (int i = 0; i < n; i++) {
				if (matrix[i][j] != tileO) {
					matrix[i][j] = tiles[counter];
					++counter;
				}
			}
		}
		return matrix;
	}
	
	public void assign() {
		for (int j = 1; j < 6; j++) {
			float r = 1;
			for (int i = 0; i < r; i++) {
				State startstate = new State(getInitialMatrix(), 2, 2);
				controlDepth(j, startstate);
				// print(startstate.getParentMatrix());
				Node startNode = new Node(startstate);
				/*
				 * addState(startstate.hashString);
				 */

//				breadthFirstSearch(startNode);
//				depthFirstSearch(startNode);
//				iterativeDeepening(startNode);
				astarsearch(startNode);
			}
//			System.out.println("BFS at dificulty: " + j + " = " + bfscount / r);
		//	System.out.println("DFS at dificulty: " + j + " = " +  dfscount / r);
//			System.out.println("Iterative deepening at dificulty: " + j + " = " +  itdcount / r);
			System.out.println("A* search at dificulty: " + j + " = " +  astarcount / r);
			bfscount = 0;
			dfscount = 0;
			itdcount = 0;
			astarcount = 0;
		}
	}

	public class HeuristicComparator implements Comparator<Node> {

		public int compare(Node node1, Node node2) {
			if (node1.getState().getHeuristic() < node2.getState().getHeuristic()) {
				return -1;
			}
			if (node1.getState().getHeuristic() > node2.getState().getHeuristic()) {
				return 1;
			}
			return 0;
		}
	}

	public void astarsearch(Node start) {
		ArrayList<Node> closedSet = new ArrayList<Node>();
		PriorityQueue<Node> open = new PriorityQueue<Node>(10, new HeuristicComparator());
		open.add(start);
		HashMap<Node, Integer> gscore = new HashMap<Node, Integer>();
		gscore.put(start, 0);
		HashMap<Node, Integer> fscore = new HashMap<Node, Integer>();
		fscore.put(start, start.getState().getHeuristic());

		while (!open.isEmpty()) {
			Node current = open.poll();
			astarcount++;
			if (current.getState().checkGoalState()) {
				break;
			}
			closedSet.add(current);
			ArrayList<Node> children = current.createChildren();
			for (Node child : children) {
				if (closedSet.contains(child)) {
					continue;
				}
				int tempg = gscore.get(current) + 1;
				if (!open.contains(child)) {
					open.add(child);
				} else if (tempg >= gscore.get(child)) {
					continue;
				}
				gscore.put(child, tempg);
				fscore.put(child, tempg + child.getState().getHeuristic());
			}
		}
	}

	public void breadthFirstSearch(Node start) {
		LinkedList<Node> queue = new LinkedList<Node>();
		queue.add(start);
		while (!queue.isEmpty()) {
			Node n = queue.remove();
			bfscount++;
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
			dfscount++;
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
		itdcount++;
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
