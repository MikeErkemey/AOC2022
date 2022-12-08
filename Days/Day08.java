import java.util.Arrays;
import java.util.List;

public class Day08 {

    public static void main(String[] args) {
        System.out.println("part1: " + part1(AOC.input(8)));
        System.out.println("part2: " + part2(AOC.input(8)));
    }

    private static long part1(List<String> input) {
        List<List<Integer>> forest = input.stream().map(x -> Arrays.stream(x.split("")).map(Integer::valueOf).toList()).toList();
        int sizeX = forest.size();
        int sizeY = forest.get(0).size();
        long sum = 0;

        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeX; j++) {
                int check = forest.get(i).get(j);
                final int y = j;

                if(!forest.get(i).subList(j+1,sizeY).stream().anyMatch(x -> x >= check)) sum++;
                else if(!forest.get(i).subList(0,j).stream().anyMatch(x -> x >= check)) sum++;
                else if(!forest.subList(i+1, sizeX).stream().anyMatch(x -> x.get(y) >= check)) sum++;
                else if(!forest.subList(0, i).stream().anyMatch(x -> x.get(y) >= check)) sum++;
            }
        }
        return sum;
    }

    private static long part2(List<String> input) {
        List<List<Integer>> forest = input.stream().map(x -> Arrays.stream(x.split("")).map(Integer::valueOf).toList()).toList();
        int sizeX = forest.size();
        int sizeY = forest.get(0).size();
        long sum = Integer.MIN_VALUE;

        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                int check = forest.get(i).get(j);
                final int y = j;
                List<Integer> right = forest.get(i).subList(j+1,sizeY);
                List<Integer> left = forest.get(i).subList(0,j).stream().toList();
                List<Integer> down = forest.subList(i+1,sizeX).stream().map(x -> x.get(y)).toList();
                List<Integer> up = forest.subList(0, i).stream().map(x -> x.get(y)).toList();

                int r = right.indexOf(right.stream().filter(x -> x>=check).findFirst().orElse(-1)) + 1;
                int l = left.lastIndexOf(left.stream().filter(x -> x>=check).findFirst().orElse(-1));
                int d = down.indexOf(down.stream().filter(x -> x>=check).findFirst().orElse(-1)) + 1;
                int u = up.lastIndexOf(up.stream().filter(x -> x>=check).findFirst().orElse(-1));

                int sumTemp = (r > 0? r : right.size()) * (l != -1? left.size() - l : left.size()) *
                        (d > 0 ? d : down.size()) * (u != -1? up.size() - u : up.size());

                if(sum < sumTemp) sum = sumTemp;
            }
        }

        return sum;
    }


}
