import java.time.temporal.ValueRange;
import java.util.Arrays;
import java.util.List;

public class Day4 {

    public static void main(String[] args) {
        System.out.println("part1: " + solution(AOC.input(4), 1));
        System.out.println("part2: " + solution(AOC.input(4), 2));
    }

    public static long solution(List<String> input, int part) {
        long part1 = input.stream()
                .map(s -> Arrays.stream(s.replace(",", "-").split("-"))
                        .mapToInt(x -> Integer.parseInt(x))
                        .toArray())
                .filter(s -> s[0] <=s[2] && s[1] >=s[3] || s[0] >=s[2] && s[1] <=s[3])
                .count();


        long part2 =  input.stream()
                .map(s -> Arrays.stream(s.replace(",", "-").split("-"))
                        .mapToInt(x -> Integer.parseInt(x))
                        .toArray())
                .filter(s -> {
                    for(int i = s[0]; i <= s[1]; i++ ) {
                        if (i >= s[2] && i <= s[3]) return true;
                    }
                    return false;
                }).count();
        
        return part == 1 ? part1 : part2 ;
    }


}
