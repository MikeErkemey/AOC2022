import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.regex.Pattern;

public class Day05 {

    public static void main(String[] args) {
        System.out.println("part1: " + solution(AOC.input(5), 1));
        System.out.println("part2: " + solution(AOC.input(5), 2));
    }

    public static String solution(List<String> input, int part) {
        List<String> sArr = new ArrayList<>();
        List<Stack<String>> stacks = new ArrayList<>();
        int l = 0;

        while(!input.get(l).contains("1")) {
            sArr.add(input.remove(0));
        }

        for (int i = 0; i < 9; i++) {
            stacks.add(new Stack<>());
        }

        for (int i = sArr.size() - 1; i >= 0; i--) {
            String[] blocks = sArr.get(i).replace("    ", " ").split(" ");

            for (int j = 0; j < blocks.length; j++) {
                if(blocks[j].equals("")) continue;
                stacks.get(j).push(blocks[j]);
            }
        }

        Stack<String> temp = new Stack<>();

        for (int i = 2; i < input.size(); i++ ) {
            String[] moves = input.get(i).split("move | from | to ");

            for (int j = 0; j < Integer.parseInt(moves[1]); j++) {
                if(part == 1){
                    stacks.get(Integer.parseInt(moves[3])-1).push(stacks.get(Integer.parseInt(moves[2]) - 1).pop());
                }else {
                    temp.push(stacks.get(Integer.parseInt(moves[2]) - 1).pop());
                }
            }

            while(!temp.isEmpty()) {
                stacks.get(Integer.parseInt(moves[3]) - 1).push(temp.pop());
            }
        }

        StringBuilder s = new StringBuilder();
        for (Stack<String> stack: stacks) {
            s.append(stack.pop().replace("]", "" ).replace("[", ""));
        }

        return s.toString();
    }


}
