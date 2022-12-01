import java.util.*;

public class Day1 {

    public static void main(String[] args) {
        System.out.println("part1: " + solution(AOC.input(1), 1));
        System.out.println("part2: " + solution(AOC.input(1), 2));
    }

    public static int solution(List<String> input, int part) {

        List<Integer> elfCallories = new ArrayList<>();
        int sum = 0;

        for (int i = 0; i < input.size(); i++) {
            if(input.get(i) == ""){
                elfCallories.add(sum);
                sum = 0;
                continue;
            }
            sum += Integer.parseInt(input.get(i));
        }

        elfCallories.sort((a,b) -> b-a);

        return part == 1 ? elfCallories.get(0)  : elfCallories.get(0) + elfCallories.get(1) + elfCallories.get(2);
    }
}
