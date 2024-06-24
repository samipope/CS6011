import std.container;
import std.algorithm;
import std.range;
import std.math;
import common;
import std.format;
import dumbknn;
import std.algorithm.comparison : equal;


// define aliases for easier reference and type clarity
alias P2 = Point!2;
alias AABB2 = AABB!2;

struct QuadTreeNode {
    P2[] points; // store points in the node
    QuadTreeNode* northWest; // pointer to north-west subtree
    QuadTreeNode* northEast; // pointer to north-east subtree
    QuadTreeNode* southWest; // pointer to south-west subtree
    QuadTreeNode* southEast; // pointer to south-east subtree
    AABB2 aabb; // axis-aligned bounding box covering this node
    bool isLeaf; // flag indicating if the node is a leaf node
}

struct QuadTree(size_t maxPointsPerAABB) {
    QuadTreeNode* root; // root node of the quadtree

    // constructor that initializes the quadtree with given points
    this(P2[] points) {
        this.root = buildTree(points, boundingBox!2(points));
    }

    // function to recursively build the quadtree
    QuadTreeNode* buildTree(P2[] points, AABB2 aabb) {
        QuadTreeNode* node = new QuadTreeNode();
        node.aabb = aabb;

        if (points.length <= maxPointsPerAABB) {
            node.isLeaf = true;
            node.points = points;
            return node;
        } else {
            node.isLeaf = false;
            P2 centerPoint = getCenter(node.aabb);

            P2[] rightPoints = points.partitionByDimension!0(centerPoint[0]);
            P2[] leftPoints = points[0 .. $ - rightPoints.length];

            auto topRightPoints = rightPoints.partitionByDimension!1(centerPoint[1]);
            auto bottomRightPoints = rightPoints[0 .. $ - topRightPoints.length];
            auto topLeftPoints = leftPoints.partitionByDimension!1(centerPoint[1]);
            auto bottomLeftPoints = leftPoints[0 .. $ - topLeftPoints.length];

            AABB2 nwBounds;
            nwBounds.min = Point!2([aabb.min[0], centerPoint[1]]);
            nwBounds.max = Point!2([centerPoint[0], aabb.max[1]]);

            AABB2 swBounds;
            swBounds.min = aabb.min;
            swBounds.max = centerPoint;

            AABB2 neBounds;
            neBounds.min = centerPoint;
            neBounds.max = aabb.max;

            AABB2 seBounds;
            seBounds.min = Point!2([centerPoint[0], aabb.min[1]]);
            seBounds.max = Point!2([aabb.max[0], centerPoint[1]]);

            node.northWest = buildTree(topLeftPoints, nwBounds);
            node.southWest = buildTree(bottomLeftPoints, swBounds);
            node.northEast = buildTree(topRightPoints, neBounds);
            node.southEast = buildTree(bottomRightPoints, seBounds);

            return node;
        }
    }

    // perform a range query around a center point within a given radius
    P2[] rangeQuery(P2 center, float radius) {
        P2[] result;

        void recurse(QuadTreeNode* node) {
            if (node is null) {
                return;
            }

            if (node.isLeaf) {
                foreach (const point; node.points) {
                    if (distance(center, point) <= radius) {
                        result ~= point;
                    }
                }
            } else {
                if (circleRectangleOverlap(center, radius, node.northWest.aabb)) {
                    recurse(node.northWest);
                }
                if (circleRectangleOverlap(center, radius, node.southWest.aabb)) {
                    recurse(node.southWest);
                }
                if (circleRectangleOverlap(center, radius, node.northEast.aabb)) {
                    recurse(node.northEast);
                }
                if (circleRectangleOverlap(center, radius, node.southEast.aabb)) {
                    recurse(node.southEast);
                }
            }
        }

        recurse(root);
        return result;
    }

    // finds the k nearest neighbors to a given point using the quadtree
    P2[] knnQuery(P2 center, int k) {
        auto priorityQueue = makePriorityQueue(center);

        bool hasPotentialCloserPoints(QuadTreeNode* node) {
            float furthestDistanceInPQ = distance(priorityQueue.front(), center);
            float minDistToNode = [abs(node.aabb.min[0] - center[0]), abs(node.aabb.max[0] - center[0]),
                                   abs(node.aabb.min[1] - center[1]), abs(node.aabb.max[1] - center[1])].minElement;
            return furthestDistanceInPQ >= minDistToNode;
        }

        void recurse(QuadTreeNode* node) {
            if (node is null)
                return;

            if (node.isLeaf) {
                foreach (const point; node.points) {
                    priorityQueue.insert(point);
                    if (priorityQueue.length > k) {
                        priorityQueue.popFront();
                    }
                }
            } else {
                if (priorityQueue.length < k || hasPotentialCloserPoints(node.northWest)) {
                    recurse(node.northWest);
                }
                if (priorityQueue.length < k || hasPotentialCloserPoints(node.northEast)) {
                    recurse(node.northEast);
                }
                if (priorityQueue.length < k || hasPotentialCloserPoints(node.southWest)) {
                    recurse(node.southWest);
                }
                if (priorityQueue.length < k || hasPotentialCloserPoints(node.southEast)) {
                    recurse(node.southEast);
                }
            }
        }

        recurse(root);
        return priorityQueue.array;
    }
}

