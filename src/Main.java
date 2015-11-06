public class Main {
	int n = 4;
	char[][] matrix = new char[n][n];
	char tileA = 'A';
	char tileB = 'B';
	char tileC = 'C';
	char tileO = 'O';
	char tileN = 'N';

	public void assign() {
		for (int j = 0; j < n; j++) {
			for (int i = 0; i < n; i++) {
				matrix[i][j] = tileN;
			}
		}
		// initial positions
		matrix[0][3] = tileA;
		matrix[1][3] = tileB;
		matrix[2][3] = tileC;
		matrix[3][3] = tileO;
	}
	
	public void print() {
		for (int j = 0; j < n; j++) {
			for (int i = 0; i < n; i++) {
				System.out.print(matrix[i][j]);
			}
			System.out.println();
		}
	}
	
	public static void main(String[] args) {
		Main s = new Main();
		s.assign();
		s.print();
	}
	
	
}
