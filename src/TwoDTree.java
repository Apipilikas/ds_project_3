import java.io.*;
import java.util.*;

//Project's part B
public class TwoDTree{
    
    private TreeNode head;      //TwoDTree's root
    private int nOfPoints = 0;  //Used for size() method
    
    public TwoDTree(){
        head = null;
    }

    public boolean isEmpty(){
        if (head == null){
            return true;
        }
        return false;
    }

    public int size(){
        return nOfPoints;
    }

    //ptToInsCont = contents (x or y coordinates) of Point to be inserted
    //curPtCont = contents (x or y coordinates) of current Point
    //Returns the TreeNode towards which we should move next ~/~ helps in following the correct search path inside the tree
    public TreeNode findNextSubTree(int ptToInsCont, int curPtCont, TreeNode curNode){
        if (ptToInsCont <= curPtCont)
            curNode = curNode.getLeftNode();
        else if (ptToInsCont >= curPtCont)
            curNode = curNode.getRightNode();
        
        return curNode;
    }

    //Returns true if TwoDTree contains Point p, else false
    public boolean search(Point p){
        int i = 0;
        TreeNode current;
        boolean found = false;

        if (isEmpty())
            return found;
        else if(head.getPoint().isEqual(p)){
            found = true;
        }
        else{
            current = head;
            while(current != null){
                if (i % 2 == 0){
                    current = findNextSubTree(p.x(), current.getPoint().x(), current);
                }
                else {
                    current = findNextSubTree(p.y(), current.getPoint().y(), current);
                }
                if (current != null){
                    if (current.getPoint().isEqual(p)){
                        found = true;
                        break;              //ENDS while loop if Point p parameter is found in TwoDTree
                    }
                }
                i++;
            }
        }
        
        return found;
    }
    
    //Helps in following the correct path inside TwoDTree in order to find the correct position to add a new Point in it and inserts it. 
    public TreeNode checkAndInsert(int ptToInsCont, int curPtCont, TreeNode curNode, Point p){
        TreeNode newNode;
        if (curNode.getPoint().isEqual(p)){
            System.out.println(">!< This Point is already in the 2D Tree >!<");
            curNode = null;
        }
        else if (ptToInsCont <= curPtCont){             //Compares x or y passed coordinates of current TreeNode with its children.
            if (curNode.isLeftNodeNull()){              //If there are no children then makes a new TreeNode and hangs it below current TreeNode (left or right).
                newNode = new TreeNode(p);              //If there are children then returns the correct next TreeNode so as to follow the correct path inside the Tree and
                curNode.setLeftNode(newNode);           //insert Point p in the correct position.
                curNode = null;     //Sets as null to terminate while loop in insert() method (check below)          
                nOfPoints++;        //Updates the size
            }
            else {
                curNode = curNode.getLeftNode();
            }
        }
        else if (ptToInsCont >= curPtCont){
            if (curNode.isRightNodeNull()){
                newNode = new TreeNode(p);
                curNode.setRightNode(newNode);
                curNode = null;
                nOfPoints++;
            }
            else {
                curNode = curNode.getRightNode();
            }
        }
        return curNode;
    }

    
    public void insert(Point p){
        int i = 0;
        TreeNode current;
        if (isEmpty()){
            head = new TreeNode(p);
            nOfPoints++;
        }
        else {
            current = head;
            while(current != null){
                if (i % 2 == 0){
                    current = checkAndInsert(p.x(), current.getPoint().x(), current, p);
                }
                else {
                    current = checkAndInsert(p.y(), current.getPoint().y(), current, p);
                }
                i++;
            }
        }
    }
    
    //Returns a list with the Points that are contained in the passed Rectangle parameter
    public List<Point> rangeSearch(Rectangle rect){
        List<Point> list = new List<>();
        int i = 0;
        rangeSearchR(head, new Rectangle(0, 100, 0, 100), rect, list, i);
        return list;
    }

    //Recursive method
    //Starts from TwoDTree's root and
    //Defines every TreeNode's ,inside TwoDTree, left and right sub-Rectangles
    //Checks if passed Rectangle (that) intersects with the sub-Rectangles and if so continues for next TreeNodes
    //If not, returns
    public void rangeSearchR(TreeNode n, Rectangle parentRect, Rectangle that, List<Point> lst, int i){
        TreeNode parentNode;
        Rectangle leftSubTreeRect = new Rectangle();
        Rectangle rightSubTreeRect = new Rectangle();

        if (i == 0){
            leftSubTreeRect.setxMin(0);
            leftSubTreeRect.setxMax(n.getPoint().x());
            leftSubTreeRect.setyMin(0);
            leftSubTreeRect.setyMax(100);

            rightSubTreeRect.setxMin(n.getPoint().x());
            rightSubTreeRect.setxMax(100);
            rightSubTreeRect.setyMin(0);
            rightSubTreeRect.setyMax(100);
        }

        if (i % 2 == 1){
            leftSubTreeRect.setxMin(parentRect.xmin());
            leftSubTreeRect.setxMax(parentRect.xmax());
            leftSubTreeRect.setyMin(parentRect.ymin());
            leftSubTreeRect.setyMax(n.getPoint().y());

            rightSubTreeRect.setxMin(parentRect.xmin());
            rightSubTreeRect.setxMax(parentRect.xmax());
            rightSubTreeRect.setyMin(n.getPoint().y());
            rightSubTreeRect.setyMax(100);
        }

        if (i%2 == 0  && i > 0){
            leftSubTreeRect.setxMin(0);
            leftSubTreeRect.setxMax(n.getPoint().x());
            leftSubTreeRect.setyMin(0);
            leftSubTreeRect.setyMax(parentRect.ymax());

            rightSubTreeRect.setxMin(n.getPoint().x());
            rightSubTreeRect.setxMax(parentRect.xmax());
            rightSubTreeRect.setyMin(parentRect.ymin());
            rightSubTreeRect.setyMax(parentRect.ymax());
        }

        parentNode = n;

        if (that.intersects(leftSubTreeRect) && that.contains(n.getPoint()) || that.intersects(rightSubTreeRect) && that.contains(n.getPoint())){
            lst.insertAtFront(n.getPoint());
        }

        i++;

        if (that.intersects(parentRect) && parentNode.getLeftNode() != null){
            rangeSearchR(parentNode.getLeftNode(), leftSubTreeRect, that, lst, i);   
        }        

        if (that.intersects(parentRect) && parentNode.getRightNode() != null){
            rangeSearchR(parentNode.getRightNode(), rightSubTreeRect, that, lst, i);
        }
        
        return;
    }

    //Returns a Point in the Tree that is closest to the Point passed
    public Point nearestNeighbor(Point p){
        Rectangle rec = new Rectangle(0, 100, 0, 100);
        Point nearestPoint = head.getPoint();
        double minDistance = 150;
        Point pa = new Point(20, 30);
        System.out.println(pa.distanceTo(p));
        int i = 0;
        nearestPoint = nearestNeighborR(head, p, nearestPoint, minDistance, i, rec);
        return nearestPoint;
    }


    //Recursive method
    //Starts from TwoDTree's root and
    //Defines every TreeNode's ,inside TwoDTree, left and right sub-Rectangles
    //Checks if each sub-Tree's distance from passed Point p has less distance than already calculated minDistance OR each TreeNode's child's distance from Point p is less than already calculated minimum distance between a TreeNode and Point p
    //and if so, recursively checks for next sub-Trees.
    //If not returns. 
    public Point nearestNeighborR(TreeNode n, Point p, Point nearestPoint, double minDistance, int i, Rectangle rec){
        Rectangle leftSubTreeRect, rightSubTreeRect;
        TreeNode leftTreeNode, rightTreeNode;
        
        if (n != null){
            if (n.getPoint().distanceTo(p) <= nearestPoint.distanceTo(p)) {
                
                nearestPoint = n.getPoint();
            }
            
            if (i % 2 == 0){
                leftSubTreeRect = new Rectangle(rec.xmin(), n.getPoint().x(), rec.ymin(), rec.ymax());
                rightSubTreeRect = new Rectangle(n.getPoint().x(), rec.xmax(), rec.ymin(), rec.ymax());
            
                i++;

                if (! n.isLeftNodeNull()){

                    
                    if (leftSubTreeRect.distanceTo(p) <= minDistance || n.getLeftNode().getPoint().distanceTo(p) <= nearestPoint.distanceTo(p)){
                        minDistance = leftSubTreeRect.distanceTo(p);
                        leftTreeNode = n.getLeftNode();
                        nearestPoint = nearestNeighborR(leftTreeNode, p, nearestPoint, minDistance, i, leftSubTreeRect);
                    }  
                }

                
                if (! n.isRightNodeNull()){
                    
                    if (rightSubTreeRect.distanceTo(p) <= minDistance || n.getRightNode().getPoint().distanceTo(p) <= nearestPoint.distanceTo(p)){
                        minDistance = rightSubTreeRect.distanceTo(p);
                        rightTreeNode = n.getRightNode();
                        nearestPoint = nearestNeighborR(rightTreeNode, p, nearestPoint, minDistance, i, rightSubTreeRect);
                    }
                }
            }
            else {
                //>!< >!< leftSubTree is the upSubTree and rightSubTree is the downSubTree
                leftSubTreeRect = new Rectangle(rec.xmin(), rec.xmax(), n.getPoint().y(), rec.ymax());
                rightSubTreeRect = new Rectangle(rec.xmin(), rec.xmax(), rec.ymin(), n.getPoint().y());
                i++;
                
                if (! n.isRightNodeNull()){

                    if (leftSubTreeRect.distanceTo(p) <= minDistance || n.getRightNode().getPoint().distanceTo(p) <= nearestPoint.distanceTo(p)){
                        minDistance = leftSubTreeRect.distanceTo(p);
                        rightTreeNode = n.getRightNode();
                        nearestPoint = nearestNeighborR(rightTreeNode, p, nearestPoint, minDistance, i, leftSubTreeRect);
                    }
                    
                }
                
                if (! n.isLeftNodeNull()){

                    if (rightSubTreeRect.distanceTo(p) <= minDistance || n.getLeftNode().getPoint().distanceTo(p) <= nearestPoint.distanceTo(p)){
                        minDistance = rightSubTreeRect.distanceTo(p);
                        leftTreeNode = n.getLeftNode();
                        nearestPoint = nearestNeighborR(leftTreeNode, p, nearestPoint, minDistance, i, rightSubTreeRect);
                    }  
                }    
            }
        }

        return nearestPoint;
    }

    //Checks if x,y passed coordinates are in range [0, 100]
    public boolean pointInRange(int x, int y){
        if (x >= 0 && x <= 100 && y >= 0 && y<= 100){
            return true;
        }
        else {
            return false;
        }
    }

    public boolean readFile(String filename, TwoDTree tDTree){
        String line;
        int intLine, x, y;
        BufferedReader reader = null;
        StringTokenizer st;
        Point p;

        try {
            reader = new BufferedReader(new FileReader(new File(filename)));
            line = reader.readLine();
            intLine = Integer.parseInt(line);
            line = reader.readLine();
            for (int i = 0; i < intLine; i++){
                st = new StringTokenizer(line, " ");
                x = Integer.parseInt(st.nextToken());
                y = Integer.parseInt(st.nextToken());
                if (pointInRange(x, y)){
                    p = new Point(x, y);
                    tDTree.insert(p);
                }
                else {
                    System.err.println(">!< Some of the Point coordinates are out of the range [0, 100]. >!<");
                    return false;
                }
                line = reader.readLine();
            }
            if (line != null){
                throw new Exception();
            }
            reader.close();
        }
        catch(IOException ex){
            System.err.println(">!< Error reading the file. >!<");
            return false;
        }
        catch(Exception ex){
            System.err.println(">!< Wrong file format. >!<");
            return false;
        }

        return true;

    }

    public static void main(String[] args){
        boolean flag = true;
        TwoDTree tDTree = new TwoDTree();
        int choice;
        Scanner in = new Scanner(System.in);
        Point point;
        Rectangle rectangle;
        flag = tDTree.readFile(args[0], tDTree);
        
        try {
            while (flag){
                
                System.out.println("\n--------------------- MENU ---------------------");
                System.out.println("1. Compute the size of the tree.");
                System.out.println("2. Insert a new point.");
                System.out.println("3. Search if a given point exists in the tree.");
                System.out.println("4. Provide a query rectangle.");
                System.out.println("5. Provide a query point.");
                System.out.println("0. Exit");
                System.out.print("Give your choice: ");
                choice = in.nextInt();
               
                if (choice == 1){
                    System.out.println("Size of the tree: " + tDTree.size());
                }
                else if (choice == 2){
                    int x, y;
                    System.out.print("Give x-coordinate of point: ");
                    x = in.nextInt();
                    System.out.print("Give y-coordinate of point: ");
                    y = in.nextInt();
                    if (tDTree.pointInRange(x, y)){
                        point = new Point(x, y);
                        tDTree.insert(point);
                    }
                    else {
                        System.err.println(">!< Point coordinates are out of the range [0, 100]. >!<");
                    }
                }
                else if (choice == 3){
                    int x, y;
                    System.out.print("Give x-coordinate of point: ");
                    x = in.nextInt();
                    System.out.print("Give y-coordinate of point: ");
                    y = in.nextInt();
                    if (tDTree.pointInRange(x, y)){
                        point = new Point(x, y);
                        if (tDTree.search(point)){
                            System.out.println("Point: " + point + " exists in the tree.");
                        }
                        else {
                            System.out.println("Point: " + point + " does not exist in the tree.");
                        }
                    }
                    else {
                        System.err.println(">!< Point coordinates are out of the range [0, 100]. >!<");
                    }
                }
                else if (choice == 4){
                    int xmin, xmax, ymin, ymax;
                    List<Point> listOfPoints;
                    System.out.print("Give minimum x-coordinate of rectangle: ");
                    xmin = in.nextInt();
                    System.out.print("Give maximum x-coordinate of rectangle: ");
                    xmax = in.nextInt();
                    System.out.print("Give minimum y-coordinate of rectangle: ");
                    ymin = in.nextInt();
                    System.out.print("Give maximum y-coordinate of rectangle: ");
                    ymax = in.nextInt();
                    if (tDTree.pointInRange(xmin, xmax) && tDTree.pointInRange(ymin, ymax)){
                        rectangle = new Rectangle(xmin, xmax, ymin, ymax);
                        
                        listOfPoints = tDTree.rangeSearch(rectangle);
                        ListNode<Point> current = listOfPoints.getFirstListNode();
                        if (current == null){
                            System.out.print(" Rectangle does not contain tree points.");
                        }
                        else {
                            System.out.println("Rectangle contains the points: ");
                            while (current != null){
                                System.out.println(">> " + current.getData());
                                current = current.getNext();
                            }
                        }
                    }
                }
                else if (choice == 5){
                    int x, y;
                    System.out.print("Give x-coordinate of point: ");
                    x = in.nextInt();
                    System.out.print("Give y-coordinate of point: ");
                    y = in.nextInt();
                    if (tDTree.pointInRange(x, y)){
                        point = new Point(x, y);
                        System.out.println("The nearest Neighbor of point " + point + " is " + tDTree.nearestNeighbor(point));
                    }
                    else {
                        System.err.println(">!< Point coordinates are out of the range [0, 100]. >!<");
                    }
                }
                else if (choice == 0){
                    flag = false;
                }
                else {
                    System.out.println(">!< Wrong input. Try again! >!<");
                }
            }
        }
        catch(Exception ex){
            System.err.println(">!< Wrong input. >!<");
        }
    }
}