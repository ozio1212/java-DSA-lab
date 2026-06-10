package dsaa.lab13;

import java.util.*;

public class ConvexHull {

	public static LinkedList<Point> solve(LinkedList<Point> points) {

		if (points == null || points.size() < 3) {
			return new LinkedList<>(points);
		}

		Point p0 = Collections.min(points);

		points.remove(p0);

		points.sort((p1, p2) -> {

			long orient = orientation(p0, p1, p2);

			if (orient == 0) {
				return Long.compare(distSq(p0, p1), distSq(p0, p2));
			}

			return orient > 0 ? -1 : 1;
		});

		ArrayList<Point> filtered = new ArrayList<>();
		filtered.add(p0);

		for (int i = 0; i < points.size(); i++) {

			while (i < points.size() - 1 && orientation(p0, points.get(i), points.get(i + 1)) == 0) {
				i++;
			}

			filtered.add(points.get(i));
		}

		if (filtered.size() < 3) {
			return new LinkedList<>(filtered);
		}

		Stack<Point> stack = new Stack<>();

		stack.push(filtered.get(0));
		stack.push(filtered.get(1));
		stack.push(filtered.get(2));

		for (int i = 3; i < filtered.size(); i++) {

			while (stack.size() > 1) {

				Point top = stack.pop();
				Point nextToTop = stack.peek();


				if (orientation(nextToTop, top, filtered.get(i)) > 0) {
					stack.push(top);
					break;
				}
			}

			stack.push(filtered.get(i));
		}

		return new LinkedList<>(stack);
	}

	private static long orientation(Point o, Point a, Point b) {
		return (a.x - o.x) * (b.y - o.y) - (a.y - o.y) * (b.x - o.x);
	}

	private static long distSq(Point p1, Point p2) {
		return (p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y);
	}

}
