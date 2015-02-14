import java.awt.Point;
import java.util.ArrayList;


public class DrawPolygon{
	
	  public ArrayList<Point> QuickHull (ArrayList<Point> points) {
	    ArrayList<Point> convexH = new ArrayList<Point>();
	    if (points.size() < 3){
	    	return (ArrayList)points.clone();
	    }
	    int minPoint = -1, maxPoint = -1;
	    int minX = Integer.MAX_VALUE;
	    int maxX = Integer.MIN_VALUE;
	    
	    for (int i = 0; i < points.size(); i++) {
	      if (points.get(i).x < minX) {
	        minX = points.get(i).x;
	        minPoint = i;
	      } 
	      if (points.get(i).x > maxX) {
	        maxX = points.get(i).x;
	        maxPoint = i;       
	      }
	    }
	    
	    Point A = points.get(minPoint);
	    Point B = points.get(maxPoint);
		convexH.add(A);
	    convexH.add(B);
	    points.remove(A);
	    points.remove(B);
	    
	    ArrayList<Point> leftSet = new ArrayList<Point>();
	    ArrayList<Point> rightSet = new ArrayList<Point>();
	    
	    for (int i = 0; i < points.size(); i++) {
	      Point p = points.get(i);
	      if (pointLocation(A,B,p) == -1)
	        leftSet.add(p);
	      else
	        rightSet.add(p);
	    }
	    hullSet(A,B,rightSet,convexH);
	    hullSet(B,A,leftSet,convexH);
	    
	    return convexH;
	  }
	  
	  private void hullSet(Point A, Point B, ArrayList<Point> set, ArrayList<Point> hull) {
	    int insertPosition = hull.indexOf(B);
	    if (set.size() == 0){
	    	return;
	    }
	    if (set.size() == 1) {
	      Point p = set.get(0);
	      set.remove(p);
	      hull.add(insertPosition,p);
	      return;
	    }
	    int dist = Integer.MIN_VALUE;
	    int furthest_Point = -1;
	    for (int i = 0; i < set.size(); i++) {
	      Point p = set.get(i);
	      int distance  = distance(A,B,p);
	      if (distance > dist) {
	        dist = distance;
	        furthest_Point = i;
	      }
	    }
	    Point P = set.get(furthest_Point);
	    set.remove(furthest_Point);
	    hull.add(insertPosition,P);
	    
	    ArrayList<Point> leftSetAP = new ArrayList<Point>();
	    for (int i = 0; i < set.size(); i++) {
	      Point M = set.get(i);
	      if (pointLocation(A,P,M)==1) {
	        leftSetAP.add(M);
	      }
	    }
	    
	    ArrayList<Point> leftSetPB = new ArrayList<Point>();
	    for (int i = 0; i < set.size(); i++) {
	      Point M = set.get(i);
	      if (pointLocation(P,B,M)==1) {
	        leftSetPB.add(M);
	      }
	    }
	    hullSet(A,P,leftSetAP,hull);
	    hullSet(P,B,leftSetPB,hull);
	}
	
	  private int pointLocation(Point A, Point B, Point P) {
		    int pointPosition = (B.x-A.x)*(P.y-A.y) - (B.y-A.y)*(P.x-A.x);
		    if(pointPosition > 0){
		    	return 1;
		    }else{
		    	return -1;
		    }
	  }
		  
	  
	  private int distance(Point A, Point B, Point C) {
		    int ABx = B.x-A.x;
		    int ABy = B.y-A.y;
		    int num = ABx*(A.y-C.y)-ABy*(A.x-C.x);
		    if (num < 0){
		    	num = -num;
		    }
		    return num;
	  }   
	  
	  public ArrayList<Point> growPolygon(ArrayList<Point> Points,int circleRobotR){
		  ArrayList<Point> growPoints1 = new ArrayList<Point>();
		  ArrayList<Point> growPoints2 = new ArrayList<Point>();
		  ArrayList<Point> growPoints = new ArrayList<Point>();
		  
		  growPoints1.add(getGrowPoint(Points.get(Points.size()-1),Points.get(0),circleRobotR));
		  for(int i = 0; i < Points.size()-1; i++){
			  growPoints1.add(getGrowPoint(Points.get(i),Points.get(i+1),circleRobotR));
		  }
		  
		  growPoints2.add(getGrowPoint(Points.get(1),Points.get(0),circleRobotR));
		  growPoints2.add(getGrowPoint(Points.get(0),Points.get(Points.size()-1),circleRobotR));
		  for(int i = Points.size()-1; i > 1; i--){
			  growPoints2.add(getGrowPoint(Points.get(i),Points.get(i-1),circleRobotR));
		  }
		  
		  for(int i = 0; i < growPoints1.size();i++){
			  growPoints.add(growPoints1.get(i));
			  growPoints.add(growPoints2.get(growPoints.size()-1-i));
		  }
		  
		  return growPoints;
	  }
	  
	  private Point getGrowPoint(Point p1, Point p2,int circleRobotR){
		  int x1 = p1.x;
		  int x2 = p2.x;
		  int y1 = p1.y;
		  int y2 = p2.y;
		  Double b = Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));
		  int newX = (int) (((x2-x1)*(circleRobotR+b)/b)+x1);
		  int newY = (int) (((y2-y1)*(circleRobotR+b)/b)+y1);
		  return new Point(newX, newY);
	  }
	  
}
