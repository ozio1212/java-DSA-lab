package dsaa.lab13;

public class Point implements Comparable<Point>{
	public long x;
	public long y;


	public Point(long x, long y){
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}

	@Override
	public int compareTo(Point other){
		if (this.y != other.y){
			return Long.compare(this.y, other.y);
		}

		return Long.compare(this.x, other.x);
	}
}
