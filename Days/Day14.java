import java.util.*;
import java.util.stream.Collectors;

public class Day14 {

    public static void main(String[] args) {
        System.out.println("part1: " + solution(AOC.input(14), 1));
        System.out.println("part2: " + solution(AOC.input(14), 2));
    }

    private static int solution(List<String> input, int part) {
        Set<Point> points = new HashSet<>();
        parsePoints(input, points);

        int abyss = part == 1 ? points.stream().mapToInt(p -> p.y).max().getAsInt() : points.stream().mapToInt(p -> p.y).max().getAsInt() + 1;
        int restSum = 0;
        while(dropSand(points, abyss, part)) restSum++;

        return restSum;
    }

    private static boolean dropSand(Set<Point> points, int abyss, int part) {
        Point sand = new Point(500,0);
        boolean rest = false;
        while(!rest){
            if(!points.contains(new Point(sand.x, sand.y+1))) {
                sand = new Point(sand.x, sand.y+1);
            }else if(!points.contains(new Point(sand.x-1, sand.y+1))) {
                sand = new Point(sand.x-1, sand.y+1);
            }
            else if(!points.contains(new Point(sand.x+1, sand.y+1))) {
                sand = new Point(sand.x+1, sand.y+1);
            }else{
                rest = true;
            }
            if(part == 1 && sand.y >= abyss) return false;
            if(part == 2 && sand.y >= abyss) break;
        }
        if(part == 2 && points.contains(sand)) return false;

        points.add(sand);
        return true;
    }

    private static void parsePoints(List<String> input, Set<Point> points) {
        for(String s : input) {
            int xPrev = -1;
            int yPrev = -1;
            for(String s2 : s.split( " -> ")) {
                if(xPrev == -1) {
                    xPrev = Integer.parseInt(s2.split(",")[0]);
                    yPrev = Integer.parseInt(s2.split(",")[1]);
                    continue;
                }

                int x = Integer.parseInt(s2.split(",")[0]);
                int y = Integer.parseInt(s2.split(",")[1]);
                
                for(int i = Math.min(yPrev, y); i <= Math.max(yPrev, y); i++) {
                    for (int j = Math.min(xPrev,x); j <= Math.max(xPrev, x) ; j++) {
                        points.add(new Point(j,i));
                    }
                }
                xPrev = x;
                yPrev = y;
            }
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
