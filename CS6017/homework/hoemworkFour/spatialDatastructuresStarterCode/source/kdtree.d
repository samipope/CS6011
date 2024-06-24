import std.stdio;
import std.array;
import std.algorithm;
import std.math;
import std.random;

import common;

// alias P2 = Point!2; this time, using point that isn't 2D need to pass dimensions


class KDTreeNode(size_t Dim) {
    Point!Dim point;
    size_t splitAxis = -1;
    KDTreeNode!Dim leftChild;
    KDTreeNode!Dim rightChild;

    this(Point!Dim pointPass, size_t splitAxisPass) {
        this.point = pointPass;
        this.splitAxis = splitAxisPass;
    }
}

struct KDTree(size_t Dim) {
    KDTreeNode!Dim root;

    this(Point!Dim[] points) {
        root = constructTree(points, 0, points.length, 0);
    }

    private KDTreeNode!Dim constructTree(Point!Dim[] points, size_t start, size_t end, size_t depth) {
        if (start >= end) {
            return null;
        }

        size_t axis = depth % Dim;
        sort!((a, b) => a[axis] < b[axis])(points[start .. end]);
        size_t median = start + (end - start) / 2;
        KDTreeNode!Dim node = new KDTreeNode!Dim(points[median], axis);

        node.leftChild = constructTree(points, start, median, depth + 1);
        node.rightChild = constructTree(points, median + 1, end, depth + 1);

        return node;
    }

    Point!Dim[] rangeQuery(Point!Dim queryPoint, float range) {
        Point!Dim[] result;

       void recurse(KDTreeNode!Dim node) {
    if (!node) return;

    float distSq = pow(distance(queryPoint, node.point), 2);
    if (distSq <= pow(range, 2)) {
        result ~= node.point;
    }

    float distToSplit = queryPoint[node.splitAxis] - node.point[node.splitAxis];
    KDTreeNode!Dim first = distToSplit < 0 ? node.leftChild : node.rightChild;
    KDTreeNode!Dim second = distToSplit < 0 ? node.rightChild : node.leftChild;

    recurse(first);
    if (pow(distToSplit, 2) <= pow(range, 2)) {
        recurse(second);
    }
    }


        recurse(root);
        return result;
    }

    Point!Dim[] knnQuery(Point!Dim queryPoint, int k) {
        auto pq = makePriorityQueue(queryPoint);

        void search(KDTreeNode!Dim node) {
            if (!node) return;

            pq.insert(node.point);

            if (pq.length > k) {
                pq.removeFront();
            }

            float distanceToSplit = queryPoint[node.splitAxis] - node.point[node.splitAxis];
            float distancePQFront = distance(pq.front, queryPoint);

            KDTreeNode!Dim first = distanceToSplit < 0 ? node.leftChild : node.rightChild;
            KDTreeNode!Dim second = distanceToSplit < 0 ? node.rightChild : node.leftChild;

            search(first);
            if (pow(distanceToSplit, 2) <= pow(distancePQFront, 2)) {
                search(second);
            }
        }

        search(root);
        return pq.array;
    }
}




