# US14 Mathematical Code Explanation Documentation

This section complements US13 explanation with the logic used in the `PathVerifier` class to determine if a valid route exists between stations of a given type and under specific locomotive constraints.

---

## Method: `verifyPath(GraphC graph, LocomotiveType locoType, StationType stationType)`

### Purpose
This method checks whether a path exists between at least two stations of a specific type (e.g., TERMINAL), considering the type of locomotive (e.g., ELECTRIC). It returns `true` if such a path exists; otherwise, it returns `false`.

### Logic Summary
```
List<Station> stations = graph.getStations();

List<Station> candidateStations = new ArrayList<>();
for (Station s : stations) {
    if (s.getType() == stationType) {
        candidateStations.add(s);
    }
}

for (Station start : candidateStations) {
    if (bfs(graph, locoType, stationType, start, candidateStations)) {
        return true;
    }
}

return false;
```

#### Explanation:
1. Retrieves all stations from the railway graph.
2. Filters those stations by the given `stationType` (e.g., all TERMINAL stations).
3. For each filtered station, it initiates a BFS to check if another station of the same type is reachable.
4. If any BFS call returns `true`, the method exits early, confirming the route exists.

---

## Helper Method: `bfs(...)`

### Purpose
Performs a Breadth-First Search (BFS) from a starting station to determine if another station of the target type can be reached. It respects the constraints imposed by the `LocomotiveType` (e.g., ELECTRIC locomotives can only traverse electrified lines).

### Logic Summary
```
List<Station> queue = new ArrayList<>();
List<Station> visited = new ArrayList<>();

int head = 0;

queue.add(start);
visited.add(start);

while (head < queue.size()) {
    Station current = queue.get(head);
    head++;

    if (current != start && current.getType() == targetStationType) {
        return true;
    }

    List<Line> connections = graph.getConnections(current);
    for (Line line : connections) {
        Station neighbor = line.getStart().equals(current) ? line.getEnd() : line.getStart();

        if (visited.contains(neighbor)) continue;

        if (locoType == LocomotiveType.ELECTRIC && !line.isElectrified()) {
            continue;
        }

        visited.add(neighbor);
        queue.add(neighbor);
    }
}

return false;
```

#### Explanation:
1. Initializes the BFS queue and the visited list with the `start` station.
2. Iteratively explores connected stations:
    - If a station of the target type is found (excluding the starting station), returns `true`.
    - Skips already visited stations to prevent cycles.
    - For electric locomotives, it only traverses electrified lines.
    - Adds eligible neighboring stations to the queue and marks them as visited.
3. If no such station is found, returns `false`.

---

## Summary
Together, the `verifyPath` and `bfs` methods allow the system to determine if it is feasible to travel between stations of a given type using a certain type of locomotive, considering constraints such as electrification. This is crucial for validating route availability during train travel planning and verification.
