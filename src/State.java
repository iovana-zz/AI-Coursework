
public class State {
	int agentx, agenty, n;
	char[][] matrix;
	boolean moved = false, stateExists = false;
	int heuristic;
	String hashString = new String();

	public State(State parent, char direction) {
		this.agentx = parent.getAgentx();
		this.agenty = parent.getAgenty();
		n = parent.getParentMatrix().length;
		matrix = new char[n][n];
		for (int j = 0; j < n; j++) {
			for (int i = 0; i < n; i++) {
				matrix[i][j] = parent.getParentMatrix()[i][j];
			}
		}
		moveDirection(direction);
		/*
		for (int j = 0; j < n; j++) {
			for (int i = 0; i < n; i++) {
				// must convert it to a string as each char[][] is a new object and states won't be unique
				hashString = hashString + String.valueOf(matrix[i][j]);
			}
		}
		stateExists = Main.addState(hashString);
		*/

	}
	public void calculateHeuristic() {
		int x = 0, y = 0;
		char[] tiles = new char[]{'A','B','C','D','E','F','G','H'};
		for(char tile: tiles) {
			int x1 = 0, y1 = 0;
			for (int j = 0; j < n; j++) {
				for (int i = 0; i < n; i++) {
					if(matrix[i][j] == tile) {
						x1 = i;
						y1 = j; 
						j = n;
						break;
					}
				}
			}
			int deltax = Math.abs(x1-x);
			int deltay = Math.abs(y1-y);
			heuristic = deltax + deltay;
			++x;
			if(x == n) {
				++y;
				x = 0;
			}
		}
	}
	public State(char[][] matrix, int agentx, int agenty) {
		this.matrix = matrix;
		this.agentx = agentx;
		this.agenty = agenty;
		n = matrix.length;
	}

	public boolean getMoved() {
		return moved;
	}

	public int getAgentx() {
		return agentx;
	}

	public int getAgenty() {
		return agenty;
	}

	public char[][] getParentMatrix() {
		return matrix;
	}

	// moveDirection - check the char and move direction according to letter
	public void moveLeft() {
		if (agentx - 1 >= 0) {
			char lastchar = matrix[agentx][agenty];
			matrix[agentx][agenty] = matrix[agentx - 1][agenty];
			matrix[agentx - 1][agenty] = lastchar;
			agentx = agentx - 1;
			moved = true;
		}
	}

	public void moveRight() {
		if (agentx + 1 < n) {
			char lastchar = matrix[agentx][agenty];
			matrix[agentx][agenty] = matrix[agentx + 1][agenty];
			matrix[agentx + 1][agenty] = lastchar;
			agentx = agentx + 1;
			moved = true;
		}
	}

	public void moveUp() {
		if (agenty - 1 >= 0) {
			char lastchar = matrix[agentx][agenty];
			matrix[agentx][agenty] = matrix[agentx][agenty - 1];
			matrix[agentx][agenty - 1] = lastchar;
			agenty = agenty - 1;
			moved = true;
		}
	}

	public void moveDown() {
		if (agenty + 1 < n) {
			char lastchar = matrix[agentx][agenty];
			matrix[agentx][agenty] = matrix[agentx][agenty + 1];
			matrix[agentx][agenty + 1] = lastchar;
			agenty = agenty + 1;
			moved = true;
		}
	}

	public void moveDirection(char direction) {
		switch (direction) {
		case 'L':
			moveLeft();
			break;
		case 'R':
			moveRight();
			break;
		case 'U':
			moveUp();
			break;
		case 'D':
			moveDown();
			break;
		default:
			break;
		}
	}

	// checks whether the tiles form a tower
	public boolean checkGoalState() {
		char[] tiles = new char[]{'A','B','C','D','E','F','G','H'};
		int counter = 0;
		for (int j = 0; j < n-2; j++) {
			for (int i = 0; i < n; i++) {
				if(matrix[i][j]!=tiles[counter]) {
					return false;
				}
				counter++;
			}
		}
		System.out.println("Success");
		return true;
	}
}
