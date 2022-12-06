import java.time.temporal.ValueRange;
import java.util.Arrays;
import java.util.List;

public class Day04 {

    public static void main(String[] args) {
        System.out.println("part1: " + solution(AOC.input(4), 1));
        System.out.println("part2: " + solution(AOC.input(4), 2));
    }

    private static long solution(List<String> input, int part) {
        long part1 = input.stream()
                .map(s -> Arrays.stream(s.replace(",", "-").split("-"))
                        .mapToInt(x -> Integer.parseInt(x))
                        .toArray())
                .filter(arr -> arr[0] <= arr[2] && arr[1] >= arr[3] || arr[0] >= arr[2] && arr[1] <= arr[3])
                .count();

        long part2 =  input.stream()
                .map(s -> Arrays.stream(s.replace(",", "-").split("-"))
                        .mapToInt(x -> Integer.parseInt(x))
                        .toArray())
                .filter(arr -> arr[0] <= arr[2] && arr[2] <= arr[1] || arr[2] <= arr[0] && arr[0] <= arr[3])
                .count();
        
        return part == 1 ? part1 : part2;
    }

}
