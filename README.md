# Particle Swarm Optimization for Pathfinding
## Introduction
This is a student project by flipketzi (https://github.com/flipketzi) and Ocepris where we explore using Particle Swarm Optimization to solve pathfinding problems in a 2D environment with different blocks. Black blocks represent walls, blue blocks represent start points, and red blocks represent goals.

![screen-gif](./res/graphics/Basic_PSO.gif)

## Problems

The strength of the PSO algorithm comes from the distribution of the particles in the space. Due to the nature of Pathfinding problems we had to define a fixed start point which was a huge problem as the particles now got stuck in local optima.

## Solutions

To solve this problem we decided to disable the swarm properties of the particles to random search if they didn't improve their personal best position after a certain amount of time. 

![alt text](./res/graphics/before_optimization.png)

Though this approach helped the particles find the goal, the found path was quite chaotic. To address this, we optimized the path by removing all intersections using the following steps:

1. Search for intersections in path
2. Cut unnecessary path from intersection point to intersection point
3. Repeat until there are no more intersections left

![alt text](./res/graphics/after_optimization.png)
The resulting path is now much shorter and efficient.

## PSO for Pathfinding on a test map

We have included a GIF that showcases our PSO algorithm at work on a test map.

![screen-gif](./res/graphics/PSO_for_pathfinding.gif)