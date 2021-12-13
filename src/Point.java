import java.lang.Math.*;

public class Point{
    
    private int x, y;

    Point(int x, int y){
        this.x = x;
        this.y = y;
    }

    Point(){
    }

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }

    //Returns x-coordinate
    public int x(){
        return this.x;
    }

    //Returns y-coordinate
    public int y(){
        return this.y;
    }

    //Euclidean distance between two Points
    public double distanceTo(Point z){
        double dist = Math.sqrt( Math.pow((this.x - z.x()), 2) + Math.pow((this.y - z.y()), 2) );
        return dist;
    }

    //Square of the Euclidean distance between two Points
    public int squareDistanceTo(Point z){
        int dx = (this.x - z.x()) * (this.x - z.x());
        int dy = (this.y - z.y()) * (this.y - z.y());
        if (dx > dy)
            return dx + dy;
        else
            return dy + dx; 
    }

    public boolean isEqual(Point p){
        if (x() == p.x() && y() == p.y()){
            return true;
        }
        return false;
    }

    //String representation: (x, y)
    public String toString(){
        return "(" + x() + ", " + y() + ")";
    }
}