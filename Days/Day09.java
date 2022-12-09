import java.util.*;

public class Day09 {

    public static void main(String[] args) {
        System.out.println("part1: " + solution(AOC.input(9), 1));
        System.out.println("part2: " + solution(AOC.input(9), 2));
    }

    private static long solution(List<String> input, int part) {
        int length = part == 1 ? 2 : 10;

        Knot.map = new HashMap<>(Map.of(0, new HashSet<>(Set.of(0))));
        Knot head = new Knot(length);

        for(String s : input) {
            int steps = Integer.parseInt(s.split(" ")[1]);
            for (int i = 0; i < steps; i++) {
                head.x += s.charAt(0) == 'L' ? -1 : s.charAt(0) == 'R' ? 1 : 0;
                head.y += s.charAt(0) == 'D' ? -1 : s.charAt(0) == 'U' ? 1 : 0;
                head.child.move(head);
            }
        }

        return   Knot.map.values().stream().mapToLong(x -> x.size()).sum();
    }

    private static class Knot {

        static HashMap<Integer, HashSet<Integer>> map = new HashMap<>();

        Knot child;
        int x;
        int y;

        public Knot(int size) {
            this.x = 0;
            this.y = 0;
            if(size != 1) child = new Knot(--size);
        }

        public void move(Knot parent) {
            if(Math.abs(parent.y - this.y) < 2 && Math.abs(parent.x - this.x) < 2) return;

            this.x += (Math.abs(parent.x - this.x)) >= 2 ? this.x < parent.x ?  1 : -1 : parent.x - this.x ;
            this.y += (Math.abs(parent.y - this.y)) >= 2 ? this.y < parent.y ?  1 : -1 : parent.y - this.y ;

            if(child != null){
                child.move(this);
            }else{
                map.putIfAbsent(this.x, new HashSet<>());
                map.get(this.x).add(this.y);
            }
        }

    }
}
