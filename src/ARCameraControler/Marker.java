package ARCameraControler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.opencv.core.Point;

public class Marker {
	private List<Point> innerBorder;
	private List<Point> outerBorder;
	private Point center;

	public Marker(List<Point> innerBorder, List<Point> outerBorder, double scale) {
		this(innerBorder,outerBorder);
		for(Point p : innerBorder){
			p.x *= scale;
			p.y *= scale;
		}
		for(Point p : outerBorder){
			p.x *= scale;
			p.y *= scale;
		}		
	}

	
	public Marker(List<Point> innerBorder, List<Point> outerBorder) {

		this.innerBorder = innerBorder;
		this.outerBorder = outerBorder;
		center = findCenter();
		orederPoints();		
		
//		if (!isClockwise(this.innerBorder)) {
//			Collections.reverse(this.innerBorder);
////			Collections.reverse(outerBorder);
//		}
//		
//		
//		center = findCenter();
//		int centerPointIndex = indexOfClosestPoint(this.innerBorder, center);
//
//		Collections.rotate(this.innerBorder, -centerPointIndex);
		
//		centerPointIndex = indexOfClosestPoint(outerBorder, innerBorder.get(2));
//		Collections.rotate(outerBorder, -centerPointIndex);
		

	}

	private Point findCenter() {
		double x = 0;
		double y = 0;

		for (Point p : outerBorder) {
			x += p.x;
			y += p.y;
		}
		x /= outerBorder.size();
		y /= outerBorder.size();
		return new Point(x, y);
	}

	public Point getCenter() {
		return center;
	}

	public int indexOfClosestPoint(List<Point> poly, Point p) {
		int min_index = 0;
		double min_dist = Double.MAX_VALUE;

		for (int i = 0; i < poly.size(); i++) {
			Point polyPt = poly.get(i);
			double dist = Math.pow(polyPt.x - p.x, 2) + Math.pow(polyPt.y - p.y, 2);
			if (dist < min_dist) {
				min_dist = dist;
				min_index = i;
			}
		}
		return min_index;
	}
	
	private void orederPoints(){
		int centerPointIndex = indexOfClosestPoint(innerBorder, center);
		int size = innerBorder.size();
		Point p0 = innerBorder.get(centerPointIndex);
		Point p1 = innerBorder.get((centerPointIndex + 1) % size);
		Point p2 = innerBorder.get((centerPointIndex + 2) % size); 
		if(!arePointClockwise(p0, p1, p2)){
			Collections.reverse(this.innerBorder);
			centerPointIndex = size - centerPointIndex - 1;
		}
		Collections.rotate(this.innerBorder, -centerPointIndex);
		
//		centerPointIndex = indexOfClosestPoint(outerBorder, innerBorder.get(2));
//		Collections.rotate(outerBorder, -centerPointIndex);
	}
	
	private boolean arePointClockwise(Point p0, Point p1, Point p2){
		Point v1 = new Point(p1.x - p0.x, p1.y - p0.y);
		Point v2 = new Point(p2.x - p0.x, p2.y - p0.y);
		// checks if z element of vector product is > 0
		return (v1.x * v2.y) - (v2.x * v1.y) > 0;
	}

	private boolean isClockwise(List<Point> line) {
		int positiveCount = 0;
		int lineSize = line.size();
		for(int i=0; i<lineSize; i++){
			Point p0 = line.get(i);
			Point p1 = line.get((i+1) % lineSize);
			Point p2 = line.get((i+2) % lineSize);

			Point v1 = new Point(p1.x - p0.x, p1.y - p0.y);
			Point v2 = new Point(p2.x - p0.x, p2.y - p0.y);
			// checks if z element of vector product is > 0
			if(	(v1.x * v2.y) - (v2.x * v1.y) > 0 )
				positiveCount++;
			else
				positiveCount--;
		}
		return positiveCount > 0;
	}

	public void sortClockwise(int startIndex) {

		// Collections.rotate(innerBorder, startIndex);
		// Collections.rotate(outerBorder, startIndex);
	}

	public List<Point> getSortedPoints() {
		List<Point> sorted = new ArrayList<Point>(innerBorder);
//		List<Point> sorted = new ArrayList<Point>(outerBorder);
		// sorted.addAll(outerBorder);
		return sorted;
	}
}
