
public class State {
	int agentx, agenty, n;
	char[][] matrix;

	public State(State parent, char direction) {
	    this.agentx = parent.getAgentx();
		this.agenty = parent.getAgenty();
		n = parent.getParentMatrix().length;
		matrix = new char[n][n];
		for(int j = 0; j < n; j++) {
			for(int i = 0; i < n; i++) {
				matrix[i][j] = parent.getParentMatrix()[i][j];
			}
		}
		moveDirection(direction);
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
			agentx = agentx -1;
		}
	}
	public void moveRight() {
		if (agentx + 1 < n) {
			char lastchar = matrix[agentx][agenty];
			matrix[agentx][agenty] = matrix[agentx + 1][agenty];
			matrix[agentx + 1][agenty] = lastchar;
			agentx = agentx + 1;
		}
	}
	public void moveUp() {
		if (agenty - 1 >= 0) {
			char lastchar = matrix[agentx][agenty];
			matrix[agentx][agenty] = matrix[agentx][agenty - 1];
			matrix[agentx][agenty - 1] = lastchar;
			agenty = agenty - 1;
		}
	}
	public void moveDown() {
		if (agenty + 1 < n) {
			char lastchar = matrix[agentx][agenty];
			matrix[agentx][agenty] = matrix[agentx][agenty + 1];
			matrix[agentx][agenty + 1] = lastchar;
			agenty = agenty + 1;
		}
	}

	public void moveDirection(char direction) {
		switch (direction) {
		case 1:
			direction = 'l';
			moveLeft();
			break;
		case 2:
			direction = 'r';
			moveRight();
			break;
		case 3:
			direction = 'u';
			moveUp();
			break;
		case 4:
			direction = 'd';
			moveDown();
			break;
		default: break;
		}
	}
	// checks whether the tiles formed a tower
	public void checkGoalState() {
		for(int j = 0; j < n-2; j++) {
			for(int i = 0; i < n; i++) {
				if(matrix[i][j] =='A' && matrix[i][j+1] == 'B' && matrix[i][j+2] == 'C') {
					System.out.println("Success");
				}
			}
	}
}
}
