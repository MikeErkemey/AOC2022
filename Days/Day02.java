import java.util.List;

public class Day02 {

    public static void main(String[] args) {
        System.out.println("part1: " + solution(AOC.input(2), 1));
        System.out.println("part2: " + solution(AOC.input(2), 2));
    }

    private static int solution(List<String> input, int part) {
        int points = 0;

        for (String s : input) {
            int choice = s.charAt(2) - 'X' + 1;
            int opponentChoice = s.charAt(0) - 'A' + 1;

            if(part == 1)
                points += choice + (choice == opponentChoice ? 3 : choice - 1 == opponentChoice % 3 ? 6 : 0);
            if(part == 2)
                points += choice == 1 ? (opponentChoice + 1) % 3 + 1  : choice == 2 ? 3 + opponentChoice  :  6 + opponentChoice % 3 + 1;
        }

        return points;
    }
}
