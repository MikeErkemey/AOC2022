import java.util.*;

public class Day06 {

    public static void main(String[] args) {
        System.out.println("part1: " + solution(AOC.input(6), 1));
        System.out.println("part2: " + solution(AOC.input(6), 2));
    }

    public static int solution(List<String> input, int part) {
        String s = input.get(0);
        int distinctChars = part == 1 ? 4 : 14;

        for (int i = distinctChars; i <= s.length(); i++) {
            if(s.substring(i-distinctChars, i).chars().distinct().count() == distinctChars){
                return i;
            }
        }
        return -1;
    }
}
