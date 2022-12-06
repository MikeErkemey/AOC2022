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

    private static String solution(List<String> input, int part) {
        List<String> sArr = input.subList(0,8);
        List<Stack<String>> stacks = new ArrayList<>();

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

        for (String str : input.subList(10, input.size())) {
            int[] moves =  Arrays.stream(str.replace("move ", "").split(" from | to ")).mapToInt(c -> Integer.parseInt(c)).toArray();

            for (int j = 0; j < moves[0]; j++) {
                if(part == 1){
                    stacks.get(moves[2]-1).push(stacks.get(moves[1] - 1).pop());
                }else {
                    temp.push(stacks.get(moves[1] - 1).pop());
                }
            }
            while(!temp.isEmpty()) {
                stacks.get(moves[2] - 1).push(temp.pop());
            }
        }

        StringBuilder s = new StringBuilder();
        for (Stack<String> stack: stacks) {
            s.append(stack.pop().replace("]", "" ).replace("[", ""));
        }

        return s.toString();
    }


}
