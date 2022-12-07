import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Day07 {

    public static void main(String[] args) {
        System.out.println("part1: " + solution(AOC.input(7), 1));
        System.out.println("part2: " + solution(AOC.input(7), 2));
    }

    private static long  solution(List<String> input, int part) {
        Directory root = makeDir(input);

        long delete = root.part2(root.getSize() - 40000000);
        long total = root.part1(100000);

        return part == 1 ? total : delete;
    }

    private static Directory makeDir(List<String> input) {
        Directory root = new Directory("/", null);
        Directory current = root;

        for(String s : input) {
            if (s.contains("$ cd")) {
                if(s.contains("..")){
                    current = current.parent;
                    continue;
                }
                if(s.equals("/")) continue;
                Directory child = new Directory(s.substring(5), current);
                current.childs.add(child);
                current = child;
            }else if(!(s.contains("dir ") || s.contains("$ ls"))){
                current.childs.add(new Directory(s.split(" ")[1], Integer.parseInt(s.split(" ")[0] ),  current));
            }
        }

        return root;
    }

    private static class Directory {

        String name;
        int size;
        List<Directory> childs;
        Directory parent;

        public Directory(String name, Directory parent) {
            this.name = name;
            size = 0;
            childs = new ArrayList<>();
            this.parent = parent;
        }

        public Directory(String name, int size, Directory parent) {
            this.name = name;
            this.size = size;
            childs = new ArrayList<>();
            this.parent = parent;
        }

        public int getSize() {
            if(childs.isEmpty()) return size;

            for(Directory dir:childs) {
                size += dir.getSize();
            }

            return size;
        }

        //getFilesUnderCertainSize()
        public long part1(long under) {
             return childs.stream().map(c -> c.childs.isEmpty() ? 0 : c.part1(under)).mapToLong(c ->c).sum() + (size <= under ? size : 0) ;
        }

        //deleteSmallestClosestToCertainSize
        public long part2(long size) {
            return childs.stream()
                    .map(c -> c.childs.isEmpty() ? Long.MAX_VALUE : (c.part2(size) > c.size && c.size >=size) ? c.size : c.part2(size))
                    .min(Long::compareTo).get();
        }
    }
}


