package dsaa.lab13;

import java.util.LinkedList;
import java.util.Scanner;

public class PointsReader {
	static LinkedList<Point> load(Scanner scan,int nrOfPoints){

		LinkedList<Point> points = new LinkedList<>();

		for (int i = 0; i < nrOfPoints; i++) {
			long x = scan.nextLong();
			long y = scan.nextLong();

			points.add(new Point(x, y));
		}

		return points;
	}
}
