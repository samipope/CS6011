package assignment09;

import java.util.ArrayList;

public class BSPTree {
//internal node class
    private class Node {
        Segment segment;
        Node left;
        Node right;
    }
    //member variables (only the root)
    private Node root;

    /**
     * default constructor making an empty tree
     */
    public BSPTree(){
       this.root=null;
    }

    /**
     * constructor taking ArrayList of segments which will perform the "bulk construction"
     * should be recursive call
     */
    public BSPTree(ArrayList<Segment> passedList){
        for(Segment segment: passedList){
            //insert all the segments in passedList
            insert(segment);
        }
    }

    /**
     * adds a segment to an existing tree
     * @param segment
     */
   public void insert(Segment segment){
        //recursive call
       root=insert(root,segment);
    }

    /**
     * recursive function
     * @param node
     * @param segment
     * @return
     */
    public Node insert(Node node, Segment segment){
       //
        if(node==null){
            //make a new node
            Node newNode = new Node();
            //segment to the newly made node
            newNode.segment =segment;
            return newNode;
        }
        int side = node.segment.whichSide(segment);
        //find out which side to add on
        if (side <= 0) { //insert left if helper function returned 0 or less
            node.left = insert(node.left, segment);
        } else {
            node.right = insert(node.right, segment);
        }

        return node;
    }

    /**
     * traverse the segments in "far to near" order, relative to the point x,y
     * @param x
     * @param y
     * @param callBack
     */
   public void traverseFarToNear(double x, double y, SegmentCallback callBack){
        traverseFarToNear(root,x,y,callBack);
        //recursive call
    }
    public void traverseFarToNear(Node node, double x, double y, SegmentCallback callBack){
        if (node == null) {
            return;
            //return null
        }
        int side = node.segment.whichSidePoint(x, y);
        if (side < 0) {
            //if the side is less than 0, then recursively call traverse on the right node and then the left
            traverseFarToNear(node.right, x, y,callBack);
            Segment segmentForCallback = node.segment;
            callBack.callback(segmentForCallback);
            traverseFarToNear(node.left, x, y, callBack);
        } else {
            //recursively call traverse on the left node and then the right node
            traverseFarToNear(node.left, x, y, callBack);
            callBack.callback(node.segment);
            traverseFarToNear(node.right, x, y, callBack);
        }
    }


    /**
     * return any segment in the tree which intersects query
     * @param query
     * @return ANY intersecting segment
     * return null if there are no intersections
     */
    public Segment collision(Segment query) {
        //recursive call
        return collision(root, query);
    }

    private Segment collision(Node node, Segment query) {
        if (node == null) {
            return null;
        }
        //if it intersects, return the segment at that node
        if (node.segment.intersects(query)) {
            return node.segment;
        }
        Segment found = collision(node.left, query);
        if (found == null) {
            found = collision(node.right, query);
        }

        return found;
    }

    //Helper functions for testing -----------------------------
    public Node getRoot() {
        return root;
    }
    public Segment getSegment(Node newNode){
        return newNode.segment;
    }

}
