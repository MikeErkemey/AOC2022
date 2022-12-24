import java.util.*;
import java.util.stream.IntStream;

public class Day15 {

    public static void main(String[] args) {
        System.out.println("part1: " + part1(AOC.input(15)));
        System.out.println("part2: " + part2(AOC.input(15)));
    }

    private static long part1(List<String> input) {
        List<Sensor> sensors = parseSensors(input);
        Set<Point> sensorSet = new HashSet<>();
        int yCheck = 2000000;
//        int yCheck = 10;
        for (Sensor sensor : sensors) {
            int distLeft = sensor.distance - (Math.abs(yCheck - sensor.sensor.y));
            if (distLeft < 0) continue;
            for (int i = sensor.sensor.x - distLeft; i <= sensor.sensor.x + distLeft; i++) {
                sensorSet.add(new Point(i, yCheck));
            }
        }

        return sensorSet.size() - sensors.stream().map(s -> s.beacon).filter(b -> b.y == yCheck).distinct().count();
    }

    private static long part2(List<String> input) {
        List<Sensor> sensors = parseSensors(input);
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                boolean inreach = false;
                for(Sensor s : sensors) {
                    if(inReach(i, j, s.sensor, s.distance )) {
                        inreach = true;
                        break;
                    }
                }
                if(!inreach){
                    return i * 4000000 + j;
                }
                points.add(new Point(i,j));
            }
            System.out.println(i);
        }
        return -1;
    }

    private static boolean inReach(int x, int y, Point sensor, int distance) {
        return distance >= Math.abs(y - sensor.y) + Math.abs(x - sensor.x);
    }


    private static List<Sensor> parseSensors(List<String> input) {
        List<Sensor> sensors = new ArrayList<>();
        for (String s : input) {
            String replaced = s.replace("Sensor at ", "")
                    .replace("x=", "")
                    .replace(" y=", "")
                    .replace(" closest beacon is at ", "");

            sensors.add(new Sensor(
                    Integer.parseInt(replaced.split(":")[0].split(",")[0]),
                    Integer.parseInt(replaced.split(":")[0].split(",")[1]),
                    Integer.parseInt(replaced.split(":")[1].split(",")[0]),
                    Integer.parseInt(replaced.split(":")[1].split(",")[1])));
        }
        return sensors;
    }

    private static class Sensor {
        Point sensor;
        Point beacon;

        int distance;

        public Sensor(int x, int y, int xClose, int yClose) {
            this.sensor = new Point(x, y);
            this.beacon = new Point(xClose, yClose);
            this.distance = Math.abs(xClose - x) + Math.abs(yClose - y);

        }
    }

    private static class Point {
        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public String toString() {
            return "Point{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }
}
