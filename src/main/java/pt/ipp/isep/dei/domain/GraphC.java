package pt.ipp.isep.dei.domain;

import java.util.ArrayList;
import java.util.List;


    public class GraphC {
        private List<Station> stations = new ArrayList<>();
        private List<Line> lines = new ArrayList<>();

        public void buildGraph(List<Station> stations, List<Line> lines) {
            this.stations.clear();
            for (Station s : stations) {
                this.stations.add(s);
            }

            this.lines.clear();
            for (Line l : lines) {
                this.lines.add(l);
            }
        }

        public List<Station> getStations() {
            return stations;
        }

        public List<Line> getLines() {
            return lines;
        }

        public List<Line> getConnections(Station station) {
            List<Line> connections = new ArrayList<>();
            for (Line line : lines) {
                if (line.getStart().equals(station) || line.getEnd().equals(station)) {
                    connections.add(line);
                }
            }
            return connections;
        }

        public boolean containsStation(Station station) {
            for (Station s : stations) {
                if (s.equals(station)) {
                    return true;
                }
            }
            return false;
        }
    }
