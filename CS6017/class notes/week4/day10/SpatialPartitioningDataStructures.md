# Spatial Partitioning Data Structures 

We talked briefly about the complexity of the using KNN for classificaiton (or regression).  The most Naive approach requires looking at every point in the dataset for each query.  We'll look at a few "spatial partitioning data structures" that can improve the efficiency of algorithms like KNN.

## Query Types

We'll talk about 2 different types of query which are similar, but not quite the same (and can be implemented in terms of one another).

**Range Queries** ask for all the points within a given region/area/volume.  The simplest range queries ask for all data points within an axis-aligned bounding box (AABB).  Only slightly more complex are queries asking for points within a given distance from a query point (give me all points within a circle/sphere).  

**KNN Queries** ask for the k closest points to a query point.  A very simple, and often reasonably efficient way to implement this is to make a range query.  If the range contains enough points, return the closest K.  Otherwise, increase the range and try again.  This often expends unecessary effort, but is simple to implement and is usually still a lot faster than naive approaches.



## The simplest: None

The Naive approach which works fine for very small datasets is to use no spatial partitioning scheme.  Just look at all points when performing a query.  For range queries, we simply filter out points outside the range.  For KNN queries, we need to partition/sort based on distance to a query point.  Partitioning can be done in O(N) time (where N is the number of points).  Sorting requires O(N lg N) time.

## Maybe good: Sorting

If you only care about searching in 1 dimension, sort your points according to that dimension.  For example, if you have spatio-temporal (space and time) data, but you only need to range queries based on the time dimension, just sort your points by time.  No extra structure is required!

If your search needs are more complicated, you'll need to try something more complex.

## Surprisingly good: Bucketing

The next simplest approach is just to divide points into constant-sized tiles.  Each tile stores a list of points.  Since the tiles are constant-sized finding the bucket that contains a point just requires computing index = (query-point - min)/tileSize for each dimension.  Basically, this converts a floating point coordinate to an integer index in each dimension.  

There are many ways to store the buckets.  One is to simply make an d-dimensional array of buckets (often this is flattened into a 1-D array of buckets).  Storing buckets in a hash-table is another common approach.

Range queries require computing the bucket indices that fall within a range, then looking at those buckets.  

KNN queries are most easily performed by performing range queries with increasly growing ranges, then partitioning once a range query returns more than K neighbors.

This approach works well when points are evenly distributed so the points are evenly distributed into buckets.  Increasing the number of buckets means a smaller list in each bucket, but more overhead related to organizing/traversing the buckets.  If points are not evenly distributed, points clump in a few buckets and we're pretty much in the "no spatial partitioning" scheme.

This approach is cache friendly since we're working with contiguous arrays, which is why it often works well in practice.  It's also pretty simple to implement.

## Asymptotically Good: KD-Trees

A KD tree is a (possibly) balanced binary tree, like a binary search tree.  At each node, we store a point and a dimension (usually as an index 0 = x, 1 = y, 2 = z, etc).  The subtrees obey the binary-search-tree property with respect to that dimension.  

For example, if the root node has dimension "x", all the nodes in the left subtree have a smaller x coordinate, all the nodes in the right tree have a larger x coordinate.

The dimension stored at nodes in the tree alternate as you descend.  Level 0 (root) is x, level 1 is y, level 2 is z, level 3 is x, etc.

Each node is partitioning the subtree into 2 halves (imagine drawing an axis-aligned plane going through the point stored at the node.  It cuts the box containing that point into 2 pieces).

Range Queries are performed by starting at the root of the tree.  Before descending to a child, we can see if the volume/area it partitions overlaps with the search area.  If it doesn't, we don't have to explore it!  

KNN queries are a bit more complex.  They're again easily implemented by performing range queries of increasing radius, but they can be computed directly as well.  More on this later in the week.

## Another approach: Quad/Oct-Trees

Similarly to the KD Tree, a quadtree/octree is a search tree that subdivides space at each level.  Quad/Octrees are not binary (as the names suggest).  Each internal node has 4 children (2-d) or 8 children (3-d) for the equally divided squares/cubes contained within the node.  Leaf nodes store a list of points contained inside them.  

Unlike KD Trees, quadtrees are less data-dependent.  This means that there might be a lot of "wasted" nodes.  However, the quadtree has a very regular structure (all subdivided areas are the same shape) which is important for some applications.  For point-location/range/knn queries, KD trees are more popular.
