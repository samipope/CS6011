package assignment09;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class BSPTreeTest {

    @Test
    public void emptyConstructor(){
        BSPTree bsptree = new BSPTree();
        Segment seg = new Segment(1,2, 3,4);
        assertNull(bsptree.collision(seg));
        assertNull(bsptree.getRoot());
    }

    @Test
    public void constructorWithArray(){
        ArrayList<Segment> segmentList = new ArrayList<>();
        segmentList.add(new Segment(0, 0, 1, 1));
        segmentList.add(new Segment(1, 0, 0, 1));
        segmentList.add(new Segment(0, 1, 1, 0));
        BSPTree bspTree = new BSPTree(segmentList);
        assertNotNull(bspTree.getRoot());
        //checking that a segment is attached
        bspTree.getSegment(bspTree.getRoot());
    }
    @Test
    public void testInsert() {
        BSPTree bspTree = new BSPTree();
        Segment segment = new Segment(0, 0, 1, 1);

        bspTree.insert(segment);

        assertNotNull(bspTree.getRoot(), "The root should not be null after inserting a segment.");
        assertEquals(segment, bspTree.getSegment(bspTree.getRoot()), "The segment in the root should be the one that was inserted.");
    }

    @Test
    public void testTraverseFarToNear() {
        // Create and populate the BSPTree
        BSPTree bspTree = new BSPTree();
        Segment s1 = new Segment(0, 0, 1, 1);
        Segment s2 = new Segment(1, 1, 2, 2);

        bspTree.insert(s1);
        bspTree.insert(s2);

        ArrayList<Segment> visitedSegments = new ArrayList<>();
        SegmentCallback callback = segment -> visitedSegments.add(segment);
        bspTree.traverseFarToNear(0, 0, callback);
        assertEquals(List.of(s2,s1), visitedSegments);

    }

    @Test
    public void testTraverseFarToNearOtherOrder() {
        // Create and populate the BSPTree
        BSPTree bspTree = new BSPTree();
        Segment s1 = new Segment(0, 0, 1, 1);
        Segment s2 = new Segment(1, 1, 2, 2);

        bspTree.insert(s2);
        bspTree.insert(s1);

        ArrayList<Segment> visitedSegments = new ArrayList<>();
        SegmentCallback callback = segment -> visitedSegments.add(segment);
        bspTree.traverseFarToNear(0, 0, callback);
        assertEquals(List.of(s1,s2), visitedSegments);

    }


    @Test
    public void testCollisionDetection() {
        // Create and populate the BSPTree
        BSPTree bspTree = new BSPTree();
        Segment s1 = new Segment(0, 0, 1, 1);
        Segment s2 = new Segment(1, 0, 2, 1);
        // ... add more segments as needed ...
        bspTree.insert(s1);
        bspTree.insert(s2);
        // ... insert more segments ...

        // Test for a collision with a known intersecting segment
        Segment query1 = new Segment(0.5, 0, 0.5, 2);
        Segment result1 = bspTree.collision(query1);
        assertNotNull(result1, "A collision should be detected.");
        assertTrue(result1.equals(s1) || result1.equals(s2), "The detected collision should be with one of the inserted segments.");

        // Test for no collision with a non-intersecting segment
        Segment query2 = new Segment(-1, -1, -2, -2);
        Segment result2 = bspTree.collision(query2);
        assertNull(result2, "No collision should be detected with a non-intersecting segment.");
    }

}
