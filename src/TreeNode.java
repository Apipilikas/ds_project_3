public class TreeNode{

    private Point item;
    private TreeNode l;
    private TreeNode r;

    public TreeNode(){
    }

    public TreeNode(Point p, TreeNode lNode, TreeNode rNode){
        item = p;
        l = lNode;
        r = rNode;
    }
    
    public TreeNode(Point np){
        this(np, null, null);
    }
    

    public TreeNode getLeftNode(){
        return l;
    }

    public TreeNode getRightNode(){
        return r;
    }
    
    public Point getPoint(){
        return item;
    }
    

    public void setLeftNode(TreeNode nLNode){
        l = nLNode;
    }

    public void setRightNode(TreeNode nRNode){
        r = nRNode;
    }

    public boolean isLeftNodeNull(){
        if (getLeftNode() == null)
            return true;
        else
            return false;
    }

    public boolean isRightNodeNull(){
        if (getRightNode() == null){
            return true;
        }
        else {
            return false;
        }
    }

}