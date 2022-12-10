import java.util.Arrays;
import java.util.List;

public class Day10 {

    public static void main(String[] args) {
        System.out.println("part1: " + solution(AOC.input(10), 1));
        System.out.println("part2: BRJLFULP -> Look at the printed message");
    }

    private static long solution(List<String> input, int part) {
        int cycle = 0;
        long x = 1;
        long sum = 0;
        StringBuilder CRTRow = new StringBuilder();

        for(String s: input) {
            for (int i = 0; i < s.split(" ").length; i++) {
                CRTRow.append((x-1 <= cycle%40 && x+1 >= cycle%40)  ? "█" : " " );
                if ((cycle++ + 20) % 40 == 0) sum += x * cycle;
            }
            if(s.split(" ").length == 2) x += Integer.parseInt(s.split(" ")[1]);
        }

        for (int i = 0; i < CRTRow.length(); i++) {
            if(i%40 == 0) System.out.println();
            System.out.print(CRTRow.charAt(i));
        }

        System.out.println("\n");

        return sum;
    }
}
