package dsaa.lab01;

public class Drawer {
	private static void drawLine(int n, char ch) {
		for(int i=0;i<n;i++) {
			System.out.print(ch);
		}
	}


	public static void drawPyramid(int n) {
		for(int i = 0; i < n ;i++) {
			drawLine(n-1-i, '.');
			drawLine(2*i+1, 'X');
			drawLine(n-1-i, '.');
			System.out.println();
		}
	}

	// Pomocnicza metoda do rysowania piramidy ograniczonej parametrem border
	public static void drawPyramidWithBorder(int n, int border) {
		for (int i = 0 ; i < n ; i++){
			drawLine(border + (n-1-i), '.'); // do kropek trzeba dodac przesuniecie o border
			drawLine(2*i+1, 'X'); // X'y sie nie zmieniaja
			drawLine(border + (n-1-i), '.');
			System.out.println();
		}
	}
	
	public static void drawChristmassTree(int n) {
		for (int i = 1; i < n+1; i++) {
			drawPyramidWithBorder(i, n-i);
		}
	}

	static void main() {
		drawPyramid(4);
		System.out.println();
		drawChristmassTree(4);
		System.out.println();
		drawPyramidWithBorder(4, 4);
		System.out.println();
		drawPyramidWithBorder(4, 4);
	}

}
