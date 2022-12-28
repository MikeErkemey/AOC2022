import java.io.IOException;
import java.util.*;

public class Day18 {


    public static void main(String[] args) throws IOException {
        System.out.println("part1: " + solution(AOC.input(18), 1));
        System.out.println("part2: " + solution(AOC.input(18), 2));
    }

    private static long solution(List<String> input, int part) {

        List<Point> points = new ArrayList<>();
        Set<Point> airPockets = new HashSet<>();

        for (String s: input) {
            String[] coords = s.split(",");
            points.add(new Point(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]), Integer.parseInt(coords[2])));
        }

        int checkTogether = 0;
        //counter all surface area
        for(int i = 0; i < points.size(); i++) {
            List<Point> check = getNeighbours(points.get(i));
            for (int j = i + 1; j < points.size(); j++) {
                if(check.contains(points.get(j))) checkTogether++;
            }
        }

        //check all that are inbetween points
        for (int i = points.stream().mapToInt(p -> p.x).min().getAsInt(); i <= points.stream().mapToInt(p -> p.x).max().getAsInt(); i++) {
            for (int j = points.stream().mapToInt(p -> p.y).min().getAsInt(); j <= points.stream().mapToInt(p -> p.y).max().getAsInt(); j++) {
                for (int l = points.stream().mapToInt(p -> p.z).min().getAsInt(); l <= points.stream().mapToInt(p -> p.z).max().getAsInt(); l++) {
                    Point temp = new Point(i,j,l);
                    if(!points.contains(temp) && checkAirpocket(temp, points)) airPockets.add(temp);
                }
            }
        }

        int counter = 0;
        boolean change = true;

        //remove every points that is connected to the outside
        while(change) {
            change = false;
            for (int i = 0; i < airPockets.size(); i++) {
                Point point = airPockets.stream().toList().get(i);
                List<Point> neighbours = getNeighbours(point);
                if (missingNeighbour(neighbours, points, airPockets)) {
                    Queue<Point> stack = new LinkedList<>();
                    stack.add(point);
                    while (!stack.isEmpty()) {
                        Point remove = stack.remove();

                        if (airPockets.contains(remove)) {
                            airPockets.remove(remove);
                            stack.addAll(getNeighbours(remove));
                        }
                    }
                    change = true;
                }
            }
        }
        //counter surface area of airpockets
        for(Point point1 : airPockets) {
            for(Point point2 : getNeighbours(point1)) {
                if(points.contains(point2)) {
                    counter++;
                }
            }
        }

        return part == 1 ? points.size() * 6  - checkTogether * 2 : points.size() * 6  - checkTogether * 2 - counter;
    }

    private static List<Point> getNeighbours(Point point1) {
        return new ArrayList<>(List.of(
                new Point(point1.x + 1,point1.y,point1.z),
                new Point(point1.x - 1,point1.y,point1.z),
                new Point(point1.x ,point1.y + 1,point1.z),
                new Point(point1.x ,point1.y - 1,point1.z),
                new Point(point1.x ,point1.y, point1.z + 1),
                new Point(point1.x , point1.y,point1.z - 1)
        ));
    }

    private static boolean checkAirpocket(Point point, List<Point> points) {
        if(
        points.stream().filter(p -> p.x < point.x && p.y == point.y && p.z == point.z).count() > 0 &&
        points.stream().filter(p -> p.x > point.x && p.y == point.y && p.z == point.z).count() > 0 &&
        points.stream().filter(p -> p.x == point.x && p.y < point.y && p.z == point.z).count() > 0 &&
        points.stream().filter(p -> p.x == point.x && p.y > point.y && p.z == point.z).count() > 0 &&
        points.stream().filter(p -> p.x == point.x && p.y == point.y && p.z < point.z).count() > 0 &&
        points.stream().filter(p -> p.x == point.x && p.y == point.y && p.z > point.z).count() > 0)
            return true;
        return false;
    }

    private static boolean missingNeighbour(List<Point> neighbours, List<Point> points, Set<Point> airPockets) {
        for(Point point : neighbours) {
            if(!points.contains(point) && !airPockets.contains(point)) {
                return true;
            }
        }
        return false;
    }

    private static class Point {
        int x;
        int y;
        int z;

        public Point(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x && y == point.y && z == point.z;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, z);
        }
    }
}
