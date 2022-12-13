import java.util.*;

public class Day12 {

    public static void main(String[] args) {
        System.out.println("part1: " + solution(AOC.input(12), 1));
        System.out.println("part2: " + solution(AOC.input(12), 2));
    }

    private static int solution(List<String> input, int part) {
        HashMap<Point, Node> map = new HashMap<>();

        for(int i = 0; i < input.size(); i++ ){
            for (int j = 0; j < input.get(i).length(); j++) {
                map.put(new Point(j,i), new Node(input.get(i).charAt(j), new Point(j,i)));
            }
        }

        Node start = map.values().stream().filter(n -> n.height == 'S').findFirst().get();
        Node end = map.values().stream().filter(n -> n.height == 'E').findFirst().get();
        start.height = 'a';
        end.height = 'z';

        int part1 = dijkStra(start, end, map);
        int part2 = Math.min(part1, map.values().stream().filter(n -> n.height == 'a').mapToInt(n->dijkStra(n, end,map)).min().getAsInt());

        return part == 1 ? part1 : part2;
    }

    public static int dijkStra(Node current, Node end, HashMap<Point, Node> map) {
        map.values().stream().forEach(n ->{n.step = 0; n.visited = false;});
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparing(n -> n.step));

        while(current != end  ) {
            if(current == null) return Integer.MAX_VALUE;
            current.visited = true;
            for (Node neighbour : getNeighbours(current, map)) {
                if ((current.height + 1) >= neighbour.height) {
                    if (pq.contains(neighbour)) pq.remove(neighbour);
                    if (neighbour.step > current.step + 1 || !pq.contains(current)) neighbour.step = current.step + 1;

                    pq.add(neighbour);
                }
            }
            current = pq.peek();
            pq.remove(current);
        }

        return current.step;
    }

    public static List<Node> getNeighbours(Node current, HashMap<Point, Node> map) {
        Node r = map.get(new Point(current.point.x+1, current.point.y));
        Node l = map.get(new Point(current.point.x-1, current.point.y));
        Node u = map.get(new Point(current.point.x, current.point.y+1));
        Node d = map.get(new Point(current.point.x, current.point.y-1));
        return new ArrayList<>(Arrays.asList(r,l,u,d)).stream().filter(n -> n != null && !n.visited).toList();
    }

    private static class Node {
        char height;
        Point point;
        int step;
        boolean visited;

        public Node(char height, Point point) {
            this.height = height;
            this.point = point;
            this.step = 0;
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
    }
}
