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

## Cornercuts

We did cut a corner in the Dijkstra algorithm, you need to always expand the node with the lowest cost, and you need to be able to check if a given node already is explored. To implemented it right, we need to create a heap to retrieve the node with the lowest cost, and then have a second data structure to be able to retrieve a node by name to check if a given node already has been visited. We used a hashmap to both of those things, we can check if a node has been visited in O(1) time, but to get the node with the lowest cost, it can take up to O(n) where n is the number of nodes in the graph. 


## Size of the graph structure if an adjacency matrix where used

There are 5653 airports, which have to go out of both axis. We end up with 5653^2 bits = 31956409 bits = 3.99 megabytes. And that is without the structures for memorizing the explored nodes / nodes to be explored.



