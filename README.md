# dsalg-4-airlinenetwork

## Assignment
assignment: https://github.com/datsoftlyngby/soft2019spring-algorithms/blob/master/Weeklies/Week_11/04-assignment/04%20Assignment%20Airline%20Network.pdf

Within your groups, implement a directed Graph for flight routes using source data from the Airline Network Data directory.
Your graph representation should be such that it is both efficient in terms of memory use and time taken when you complete the following operations on the graph:
1. Find if an airport can be reached from another using only a single airline company. You should compare
- Depth-first search
- Breadth-first search
2. Finding shortest path (in distance) from one location to another (Dijkstra’s algirithm)
3. Finding shortest path (in time) from one location to another, assuming that each leg transfer takes one hour.
4. Finding airline that has widest coverage (MinimumSpanningTree)

- Defend your choice of data structure, regarding time and memory com-
plexity – you know.... O(?).
- Also argue why you did not choose the other data structures possible.
Were they too slow, too large – or both?
- Estimate the size (in bytes) of the array that would be required to complete task #1 above, if you were to use an adjacency matrix.

----

## Assignment Data
Airline Network Data: https://github.com/datsoftlyngby/soft2019spring-algorithms/tree/master/Weeklies/Week_11/04-assignment/Airline%20Network%20Data

-----

## Output

```
depthFirstSearch:
true
Took 0.227695 ms

breadthFirstSearch:
Path{path=[AER, KZN, CEK], cost=0.0, airline='2B', location='OVB'}
Took 0.065220684 seconds

dijkstra (distance):
Path{path=[AER, KRR, OVB], cost=3421.5451952682306, airline='null', location='OVB'}
Took 0.588816004 seconds

dijkstra (time):
Path{path=[AER, KRR, OVB], cost=7.271096821059627, airline='null', location='OVB'}
Took 0.435430712 seconds

minimum spanning tree
AA has a max coverage of 429
Took 7.466753164 seconds
```

------
## Depth First Search

When implementing depth first search, we used a _hashmap_ to store the explored nodes. In that way, we can check if a node has been visited in **O(1)** time. The adjacency list allowed us to get the neighbors in constant time as well. The algorithm need to visit every single node in the worst case, and will be checking every edge to check if it explored, hence the complexity is going to be **O(|V|+|E|)**.

## Breadth First Search

For breadth first search, we used a fifo queue to hold the explored notes. The nodes are automatically explored in order by the path length, and therefore the frontier with the shortest path will always be the first one. We do also have a _hashmap_ with the explored notes to be able to check if a node has been explored in **O(1)** time, this leads to some memory overheat because it is stored twice. The complexity is **O(|V|+|E|)** at the same reasons as depth first search.

### Alternatives

We could of course have used an min-heap, but it was unnecessary for this implementation.

## Dijkstra's First Search

We did cut a corner in the Dijkstra algorithm, you need to always expand the node with the lowest cost, and you need to be able to check if a given node already is explored. To implemented it right, we need to create a heap to retrieve the node with the lowest cost, and then have a second data structure to be able to retrieve a node by name to check if a given node already has been visited. We used a hashmap to both of those things, we can check if a node has been visited in **O(1)** time, but to get the node with the lowest cost, it can take up to **O(n)** where n is the number of nodes in the graph.

Some alternatives we looked into involve:

fibonacci heap `O(|E|+|V|log| V|)` - this is the fastest implementation possible (with the possible exception of the new 2-3 heap implementation which seems to undercut it slightly in time-complexity).

unsorted array - `O(|V|^2)`  

min heap // binary heap - `O((|E| + |V|) log |V|)`

### Why did we use the above Data Structures?

We used what we used in the interest of what we already had coded in earlier exercises, and in time. We didn’t really research any alternative solutions or approaches.

## Minimum spanning tree

When building the _mst_, we both use a hashset to keep track of which nodes there has been visited, and we have a hashset for the unexplored set of nodes. Alongside the hashset for the unexplored nodes, we have a min heap for the same data to be able to get the node with the lowest cost in **O(1)** time. The algorithm is based on prim’s algorithm which has a time complexity of `O(|E|log|V|)` when a binary heap and an adjacency list structure is used.

In graphs with more edges than vertices and many nodes an alternative solution would be superior - where we maintain two sets of vertices, one for our growing tree and one that is not contained within the tree. With the use of a fibonacci heap we can reach a complexity of `O(E + V log V)`. Our current method of obtaining the lowest cost method is a better solution in general to handle typical occurrences one might encounter and makes use of simpler data-structures which we have encountered in class. 

------



## Size of the graph structure if an adjacency matrix where used

There are 5653 airports, which have to go out of both axis. We end up with 5653^2 bits = 31956409 bits = 3.99 megabytes. And that is without the structures for memorizing the explored nodes / nodes to be explored.

--------




