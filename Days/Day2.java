import java.util.List;

public class Day2 {

    public static void main(String[] args) {
        System.out.println("part1: " + solution(AOC.input(2), 1));
        System.out.println("part2: " + solution(AOC.input(2), 2));
    }

    public static int solution(List<String> input, int part) {
        int points = 0;

        for (String s : input) {
            int choice = s.toLowerCase().charAt(2) - 'x' + 1;
            int opponentChoice = s.toLowerCase().charAt(0) - 'a' + 1;

            if(part == 1)
                points += choice + (choice == opponentChoice ? 3 : ((choice - 1) == opponentChoice % 3 ? 6 : 0));
            if(part == 2)
                points += choice == 1 ? (opponentChoice + 1) % 3 + 1  : choice == 2 ? 3 + opponentChoice  :  6 + (opponentChoice) % 3 + 1;
        }

        return points;
    }
}
