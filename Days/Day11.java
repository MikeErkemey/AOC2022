import jdk.dynalink.CallSiteDescriptor;

import java.math.BigDecimal;
import java.util.*;

public class Day11 {

    public static void main(String[] args) {
        System.out.println("part1: " + solution(AOC.input(11), 1));
        System.out.println("part2: " + solution(AOC.input(11), 2));
    }

    private static String solution(List<String> input, int part) {
        List<Monkey> monkeys = makeMonkeys(input);
        Monkey.alldivisible = 1;
        monkeys.stream().forEach(m -> Monkey.alldivisible *= m.divisibleTest);

        for (int i = 0; i < (part == 1 ? 20 : 10000); i++) {
            for (Monkey monkey : monkeys) {
                monkey.inspect(monkeys, part);
            }
        }
        monkeys.sort(Comparator.comparing(m -> m.itemsInspected));

        BigDecimal one = new BigDecimal(monkeys.get(monkeys.size() - 2).itemsInspected);
        BigDecimal two = new BigDecimal(monkeys.get(monkeys.size() - 1).itemsInspected);
        return one.multiply(two).toString();
    }

    private static List<Monkey> makeMonkeys(List<String> input) {
        List<Monkey> monkeys = new ArrayList<>();

        for (int i = 0; i < input.size(); i++) {
            String[] items = input.get(++i).replace("  Starting items: ", "").split(", ");
            String[] operation = input.get(++i).replace("  Operation: new = old ", "").split(" ");
            int test = Integer.parseInt(input.get(++i).replaceAll("\\D+", ""));
            int mTrue = Integer.parseInt(input.get(++i).replaceAll("\\D+", ""));
            int mFalse = Integer.parseInt(input.get(++i).replaceAll("\\D+", ""));
            Monkey monkey = new Monkey(operation, test, mTrue, mFalse);
            Arrays.stream(items).forEach(x -> monkey.items.add(Long.parseLong(x)));
            monkeys.add(monkey);
            i++;
        }
        return monkeys;
    }

    private static class Monkey {

        static long alldivisible;

        Queue<Long> items;
        String[] operation;
        int itemsInspected;
        int divisibleTest;
        int monkeyTrue;
        int monkeyFalse;

        public Monkey(String[] operation, int divisibleTest, int monkeyTrue, int monkeyFalse) {
            this.items = new LinkedList<>();
            this.operation = operation;
            this.itemsInspected = 0;
            this.divisibleTest = divisibleTest;
            this.monkeyTrue = monkeyTrue;
            this.monkeyFalse = monkeyFalse;
        }

        public void inspect(List<Monkey> monkeys, int part) {
            while (!items.isEmpty()) {
                long old = items.remove();
                long num2 = operation[1].equals("old") ? old : Long.parseLong(operation[1]);
                old = operation[0].equals("*") ? old * num2 : old + num2;
                if(part == 1) old = old/3;
                monkeys.get(old % divisibleTest == 0 ? monkeyTrue : monkeyFalse).items.add(part == 1 ? old : old%alldivisible);
                itemsInspected++;
            }
        }

    }
}
