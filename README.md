# Pathfinder
A simple Java/Swing pathfinding simulator

<img width="500" alt="Screen Shot 2022-06-12 at 5 05 59 PM" src="https://user-images.githubusercontent.com/101377119/173254274-e9b13d7c-af40-4fb9-985f-5aac5abfe5f9.png">
<img width="500" alt="Screen Shot 2022-06-12 at 5 06 16 PM" src="https://user-images.githubusercontent.com/101377119/173254284-ef0904ca-b47d-4e78-ac71-e908ff71c11a.png">

# This is not a shortest path algorithm
The algorithm I designed is simplistic. It tries find a path, not neccessarily the shortest path. Since it only follows one path, it may fail in certain situations.

## Here is the setup...

- n x n grid.
- The starting point is defined as _(sx,sy)_
- The ending point is defined as _(ex,ey)_

## Highly simplified process...

1. An array of adjacent neighbors of _(sx,sy)_ is defined as _neighbors_
2. _neighbors_ is sorted in order of proximity to _(ex,ey)_  
3. The first element in _neighbors_ _n_ is chosen
4. The process is repeated this time with _n_
5. The process stops when _(ex,ey)_ is reached
6. All the while an array _travelled_ keeps track of the steps taken

**Note that these elements are an abstraction and do not actually exist in the code, it is a little more complicated than above but same idea**

