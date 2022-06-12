# Pathfinder

<img width="500" alt="Screen Shot 2022-06-12 at 5 50 24 PM" src="https://user-images.githubusercontent.com/101377119/173255120-dddb314d-6d72-46a4-93d5-3bf64ac1bddc.png"><img width="500" alt="Screen Shot 2022-06-12 at 5 50 32 PM" src="https://user-images.githubusercontent.com/101377119/173255126-64aee904-7b1c-44c2-a964-3c0f95cfefe7.png">



# This is not a shortest path algorithm
The algorithm I designed is simplistic. It tries to find a path; not neccessarily the shortest path. Since it only follows one trajectory, it may fail in certain situations when it gets trapped by its own tail.

I decided to take on this project to learn how pathfinding works. I have no background in the subject, but rather than study already made algorithms, I designed my own with very little guidance from the internet. 

## Here is the setup...

- n x n grid.
- The starting point is defined as _(sx,sy)_
- The ending point is defined as _(ex,ey)_

## High level algorithm explanation...

1. An array of adjacent neighbors of _(sx,sy)_ is defined as _neighbors_
2. _neighbors_ is sorted in order of proximity to _(ex,ey)_  
3. The first element in _neighbors_ _n_ is chosen
4. The process is repeated this time with _n_
5. The process stops when _(ex,ey)_ is reached
6. All the while an array _travelled_ keeps track of the steps taken

**Note that these elements are an abstraction and do not actually exist in the code, it is a little more complicated than above but same idea**

# How to run

1. Ensure all files are in same directory
2. Open linux terminal in directory and type ```javac *.java```
3. Next type ```java Main```
4. If that doesn't work...  ;(
