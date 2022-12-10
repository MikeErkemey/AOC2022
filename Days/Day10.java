import java.util.Arrays;
import java.util.List;

public class Day10 {

    public static void main(String[] args) {
        System.out.println("part1: " + solution(AOC.input(10), 1));
        System.out.println("part2: " + solution(AOC.input(10), 2));
    }

    private static String solution(List<String> input, int part) {
        int cycle = 0;
        long x = 1;
        long sum = 0;
        StringBuilder CRT = new StringBuilder("\n");

        for(String s: input) {
            for (int i = 0; i < s.split(" ").length; i++) {
                CRT.append( (x-1 <= cycle%40 && x+1 >= cycle%40)  ? "█" : " " ).append(cycle%40 == 39? "\n": "");
                if ((++cycle + 20) % 40 == 0) sum += x * cycle;
            }
            if(s.split(" ").length == 2) x += Integer.parseInt(s.split(" ")[1]);
        }

        return part == 1 ? String.valueOf(sum): CRT.toString();
    }
}
