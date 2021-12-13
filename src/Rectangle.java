public class Rectangle{
    
    private int xmn, xmx, ymn, ymx;
    Point c1, c2, c3, c4;
    Point k1, k2, k3, k4;

    Rectangle(int xmn, int xmx, int ymn, int ymx){
        this.xmn = xmn;
        this.xmx = xmx;
        this.ymn = ymn;
        this.ymx = ymx;

        this.k1 = new Point(xmin(), ymax());
        this.k2 = new Point(xmin(), ymin());
        this.k3 = new Point(xmax(), ymin());
        this.k4 = new Point(xmax(), ymax());
    }

    Rectangle(){
    }

    public void setxMax(int xmx){
        this.xmx = xmx;
    }

    public void setxMin(int xmn){
        this.xmn = xmn;
    }

    public void setyMax(int ymx){
        this.ymx = ymx;
    }

    public void setyMin(int ymn){
        this.ymn = ymn;
    }

    //Minimum x-coordinate of Rectangle
    public int xmin(){
        return this.xmn;
    }

    //Maximum x-coordinate of Rectangle
    public int xmax(){
        return this.xmx;
    }

    //Minimum y-coordinate of Rectangle
    public int ymin(){
        return this.ymn;
    }

    //Maximum y-coordinate of Rectangle
    public int ymax(){
        return this.ymx;
    }

    //Returns true if Point p belongs to the Rectangle, else false
    public boolean contains(Point p){
        if (p.x() >= xmn && p.x() <= xmx && p.y() <= ymx && p.y() >= ymn)
            return true;
        else
            return false;
    }

    //Returns true if the two Rectangles intersect, else false
    public boolean intersects(Rectangle that){
        
        c1 = new Point(that.xmin(), that.ymin());
        c2 = new Point(that.xmin(), that.ymax());
        c3 = new Point(that.xmax(), that.ymin());
        c4 = new Point(that.xmax(), that.ymax());

        if (contains(c1) || contains(c2) || contains(c3) || contains(c4))
            return true;
        else if (that.contains(k1) || that.contains(k2) || that.contains(k3) || that.contains(k4))
            return true;
        else
            return false;
    }

    //Returns the minimum Distance between Point p and Rectangle
    public double distanceTo(Point p){

        double tbr = 100000;
        
        //Checks if Point p is on or belongs to the Rectangle
        if (p.x() >= k2.x() && p.x() <= k3.x() && p.y() >= k2.y() && p.y() <= k1.y())
            tbr = 0;
        else if ( (p.x() > xmax() && p.y() < ymin() ) || (p.x() > xmax() && p.y() > ymax() ) || (p.x() < xmin() && p.y() < ymin()) || (p.x() < xmin() && p.y() > ymax())){ 
            //Checks if vertical (minimum) distance between Point p and Rectangle CANNOT be calculated.
            double minDist = p.distanceTo(k1);
            double dist2 = p.distanceTo(k2);
            double dist3 = p.distanceTo(k3);
            double dist4 = p.distanceTo(k4);

            if (dist2 < minDist)
                minDist = dist2;
            if (dist3 < minDist)
                minDist = dist3;
            if (dist4 < minDist)
                minDist = dist4;
            tbr = minDist;
        }
        //Calculates vertical (minimum) Distance
        else if (p.y() <= ymax() && p.y() >= ymin()){
            if (p.x() > xmax())
                tbr = p.x() - xmax();
            else
                tbr =  xmin() - p.x();
        }
        else if (p.x() >= xmin() && p.x() <= xmax()){
            if (p.y() < ymin())
                tbr = ymin() - p.y();
            else
                tbr = p.y() - ymax();
        }

        return tbr;

    }

    //Same as before but returns squared Distance
    public int squareDistanceTo(Point p){

        int tbr = 100000;
                
        if (p.x() >= k2.x() && p.x() <= k3.x() && p.y() >= k2.y() && p.y() <= k1.y())
            tbr = 0;
        else if ( (p.x() > xmax() && p.y() < ymin() ) || (p.x() > xmax() && p.y() > ymax() ) || (p.x() < xmin() && p.y() < ymin()) || (p.x() < xmin() && p.y() > ymax())){
            int minDist = p.squareDistanceTo(k1);
            int dist2 = p.squareDistanceTo(k2);
            int dist3 = p.squareDistanceTo(k3);
            int dist4 = p.squareDistanceTo(k4);

            if (dist2 < minDist)
                minDist = dist2;
            if (dist3 < minDist)
                minDist = dist3;
            if (dist4 < minDist)
                minDist = dist4;
            tbr = minDist;
        }
        else if (p.y() <= ymax() && p.y() >= ymin()){
            if (p.x() > xmax())
                tbr = (p.x() - xmax())*(p.x() - xmax());
            else
                tbr =  (xmin() - p.x())*(xmin() - p.x());
        }
        else if (p.x() >= xmin() && p.x() <= xmax()){
            if (p.y() < ymin())
                tbr = (ymin() - p.y())*(ymin() - p.y());
            else
                tbr = (p.y() - ymax())*(p.y() - ymax());
        }

        return tbr;

    }

    //String representation as [xmin, xmax]x[ymin, ymax]
    public String toString(){
        return "[" + xmin() + ", " + xmax() + "]" + " x [" + ymin() + ", " + ymax() + "]";
    }
}