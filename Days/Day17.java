import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

public class Day17 {

    public static void main(String[] args) throws IOException {
        System.out.println("part1: " + solution(AOC.input(17), 1));
        System.out.println("part2: " + solution(AOC.input(17), 2));
    }

    private static String solution(List<String> input, int part) throws IOException {
        Queue<Character> nextMove = new LinkedList<>();
        List<Point> points = makeFloor();
        for (int i = 0; i < input.get(0).length(); i++) {
            nextMove.add(input.get(0).charAt(i));
        }

        int countmove = 0;
        int leftWall = 0;
        int rightWall = 8;
        List<Form> forms = makeShapes();
        int rocksFallen = 0;
        int cycleInt = -1;

        List<int[]> indicators = new ArrayList<>();
        List<Integer> heights = new ArrayList<>();

        while (indicators.size() < 2 || rocksFallen <= 4022) {
            if (countmove > nextMove.size() && cycleInt == -1) cycleInt = countmove % nextMove.size();
            if (indicators.size() == 0 || rocksFallen <= 2022)
                heights.add(points.stream().mapToInt(p -> p.y).max().getAsInt());
            if (cycleInt != -1 && cycleInt == countmove % nextMove.size()) {
                indicators.add(new int[]{rocksFallen, points.stream().mapToInt(p -> p.y).max().getAsInt()});
            }
            Shape shape = new Shape(forms.get(rocksFallen % forms.size()));
            int highest = points.stream().mapToInt(p -> p.y).max().getAsInt();

            shape.points.stream().forEach(p -> p.y += highest + 4);

            boolean canMoveDown = true;
            while (canMoveDown) {
                countmove++;
                Character move = nextMove.remove();

                if (move.equals('<')) {
                    if (shape.points.stream().filter(p -> p.x - 1 == leftWall || points.contains(new Point(p.x - 1, p.y))).count() == 0) {
                        shape.points.stream().forEach(p -> p.x--);
                    }
                } else {
                    if (shape.points.stream().filter(p -> p.x + 1 == rightWall || points.contains(new Point(p.x + 1, p.y))).count() == 0) {
                        shape.points.stream().forEach(p -> p.x++);
                    }
                }

                if (shape.points.stream().filter(p -> points.contains(new Point(p.x, p.y - 1))).count() == 0) {
                    shape.points.stream().forEach(p -> p.y--);
                } else {
                    canMoveDown = false;
                }
                nextMove.add(move);
            }
            points.addAll(shape.points);

            rocksFallen++;
        }

        int rocksFallenCycle = indicators.get(1)[0] - indicators.get(0)[0];
        int heightDifferenceCycle = indicators.get(1)[1] - indicators.get(0)[1];
        BigDecimal cycles = new BigDecimal(1000000000000l / rocksFallenCycle);
        BigDecimal remain = new BigDecimal(heights.get((int) (1000000000000l % rocksFallenCycle)));

        return part == 1 ? String.valueOf(heights.get(2022)) : cycles.multiply(new BigDecimal(heightDifferenceCycle)).add(remain).toString();
    }

    private static List<Form> makeShapes() {
        return new ArrayList<>(List.of(
                Form.STRIPE,
                Form.PLUS,
                Form.L,
                Form.LINE,
                Form.SQUARE
        ));
    }

    private static List<Point> makeFloor() {
        return new ArrayList<>(List.of(
                new Point(1,0),
                new Point(2,0),
                new Point(3,0),
                new Point(4,0),
                new Point(5,0),
                new Point(6,0),
                new Point(7,0)
        ));
    }

    enum Form {STRIPE, PLUS, L, LINE, SQUARE}

    private static class Shape {
        List<Point> points;

        public Shape(Form shape) {
            this.points = new ArrayList<>();
            switch (shape) {
                case STRIPE -> this.points.addAll(List.of(new Point(3,0),new Point(4,0),new Point(5,0),new Point(6,0)));
                case PLUS -> this.points.addAll(List.of(new Point(4,0),new Point(3,1),new Point(4,1),new Point(5,1), new Point(4,2)));
                case L -> this.points.addAll(List.of(new Point(3,0),new Point(4,0),new Point(5,0),new Point(5,1), new Point(5, 2)));
                case LINE -> this.points.addAll(List.of(new Point(3,0),new Point(3,1),new Point(3,2),new Point(3,3)));
                case SQUARE -> this.points.addAll(List.of(new Point(3,0),new Point(4,0),new Point(3,1),new Point(4,1)));
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

