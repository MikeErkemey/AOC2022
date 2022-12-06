import java.util.*;

public class Day01 {

    public static void main(String[] args) {
        System.out.println("part1: " + solution(AOC.input(1), 1));
        System.out.println("part2: " + solution(AOC.input(1), 2));
    }

    private static int solution(List<String> input, int part) {

        List<Integer> elfCalories = new ArrayList<>();
        int sum = 0;

        for (int i = 0; i < input.size(); i++) {
            if(input.get(i) == ""){
                elfCalories.add(sum);
                sum = 0;
                continue;
            }
            sum += Integer.parseInt(input.get(i));
        }

        elfCalories.sort((a,b) -> b-a);

        return part == 1 ? elfCalories.get(0) : elfCalories.get(0) + elfCalories.get(1) + elfCalories.get(2);
    }
}
