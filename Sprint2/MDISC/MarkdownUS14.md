# US14 Mathematical Code Explanation Documentation

## Overview

US14 is responsible for determining if an **Eulerian path** or **Eulerian circuit** exists in a railway network. This is used specifically for planning a maintenance route that passes through each railway line **exactly once**.

---

## Method: `findEulerianPath(GraphC graph)`

### Description

This method checks the structure of the railway graph and determines whether an Eulerian path exists. An Eulerian path visits every edge (line) exactly once. This is relevant for scheduling a maintenance route that covers each railway line once and only once.

### Parameters

- **`graph`**: An instance of `GraphC` containing:
    - a list of `Station` nodes,
    - a list of `Line` edges connecting these stations.

### Returns

- **`List<Station>`**:
    - If an Eulerian circuit is possible, returns **all stations** as valid starting points.
    - If an Eulerian path is possible (2 odd-degree nodes), returns **those two stations**.
    - If neither is possible, returns **`null`**.

---

## Algorithm Details

### Eulerian Path Conditions:

1. **Eulerian Circuit**:
    - All stations (nodes) have an **even degree**.
    - You can start from any station.

2. **Eulerian Path**:
    - Exactly **two stations** have an **odd degree**.
    - Start from one of the two odd-degree stations.

3. **No Eulerian Path**:
    - More than 2 stations with an odd degree.
    - Not possible to find such a route.

### Logic Summary
```
List<Station> stations = graph.getStations();
List<Line> lines = graph.getLines();
List<Station> oddDegreeStations = new ArrayList<>();
```

This initializes the list of stations, lines, and a container for odd-degree stations.

### Degree Calculation
```
for (Station current : stations) {
    int degree = 0;

    // Count lines connected to the current station
    for (int j = 0; j < lines.size(); j++) {
        Line line = lines.get(j);
        if (line.getStart().equals(current) || line.getEnd().equals(current)) {
            degree++;
        }
    }

    // If the number of connections (degree) is odd, add to list
    if (degree % 2 != 0) {
        oddDegreeStations.add(current);
    }
}
```

#### Explanation:
- Iterates over each station.
- For each station, it counts how many lines connect to it by checking if it is the start or end of each line.
- The result is stored in the `degree` variable.
- If the degree is odd, the station is added to the `oddDegreeStations` list.

```
// Determine possible starting stations based on the number of odd-degree nodes
if (oddDegreeStations.isEmpty()) {
// Eulerian circuit: any station can be a start
     return stations;
} else if (oddDegreeStations.size() == 2) {
  // Eulerian path: must start at one of the odd-degree stations
    return oddDegreeStations;
} else {
  // No Eulerian path is possible
    return null;
}
```

This determines the correct set of starting stations (or null) based on the number of stations with odd degrees.

---

## Example Use Case

Used in the `RailwayMaintenanceController` to:
- Build the graph of selected railway lines,
- Use `Route.findEulerianPath()` to find starting points,
- Show available options for initiating a maintenance journey.
