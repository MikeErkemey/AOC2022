import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Day3 {

    public static void main(String[] args) {
        System.out.println("part1: " + solution(AOC.input(3), 1));
        System.out.println("part2: " + solution(AOC.input(3), 2));
    }

    public static int solution(List<String> input, int part) {

        int part1 = input.stream().map(s -> s.substring(0,s.length()/2)
                        .replace("", "\n")
                        .lines()
                        .filter(c -> s.substring(s.length()/2).contains(c) && !c.isEmpty())
                        .map(c -> c.charAt(0) >= 97 ? (int)c.charAt(0) - 96 : (int) c.charAt(0) - 38)
                        .toList().get(0))
                .mapToInt(c -> c).sum();
        //partitions had to look up
        final AtomicInteger counter = new AtomicInteger();
        List<List<String>> partitions = input.stream()
                        .collect(Collectors.groupingBy(it -> counter.getAndIncrement() / 3))
                        .values().stream().toList();

        int part2 = partitions.stream().map(arr -> arr.get(0)
                        .replace("", "\n")
                        .lines()
                        .filter(c -> arr.get(1).contains(c) && arr.get(2).contains(c) && !c.isEmpty())
                        .map(c -> c.charAt(0) >= 97 ? (int)c.charAt(0) - 96 : (int) c.charAt(0) - 38)
                        .toList().get(0))
                .mapToInt(c -> c).sum();


        return part == 1 ? part1 : part2;
    }
}
