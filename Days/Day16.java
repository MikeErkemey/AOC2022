import java.util.*;

public class Day16 {

    //    Valve DD has flow rate=20; tunnels lead to valves CC, AA, EE
    public static void main(String[] args) {
        System.out.println("part1: " + solution(AOC.input(16), 1));
        System.out.println("part2: " + solution(AOC.input(16), 2));
    }

    private static int solution(List<String> input, int part) {
       List<Valve> valves = new ArrayList<>();

        for(String s : input) {
            String[] split = s.split("; ");
            String[] values = split[0].substring(6).split(" has flow rate=");
            String[] neighbours = split[1].substring(22).stripIndent().split(", ");
            valves.add(new Valve(values, neighbours));
        }
        Valve start = valves.stream().filter(v -> v.name.equals("AA")).toList().get(0);
        List<Valve> importantValves = valves.stream().filter(v -> v.flow != 0 || v.name.equals("AA")).toList();
        for (Valve valve : importantValves) {
            fastestWay(valve, importantValves, valves);
        }

        return part == 1 ? findWay(start, new ArrayList<>(), 30) :
                findWay2(start, start, new ArrayList<>(Arrays.asList(start)), 26, 26, 0);
    }


    public static int findWay(Valve valve, List<Valve> been, int minutes ) {
        if (minutes <= 0) return valve.flow * minutes;
        been.add(valve);
        int highestPressure = valve.flow * minutes;
        int max = Integer.MIN_VALUE;
        for (Valve v : valve.fastestWays.keySet()) {
            if(been.contains(v)) continue;
            int time = valve.fastestWays.get(v) + 1;
            max = Math.max(max, findWay(v, been, minutes - time));
        }

        been.remove(valve);
        return max < 0 ? highestPressure : highestPressure + max;
    }

    public static int findWay2(Valve me, Valve elephant, List<Valve> been, int minutesMe, int minutesElephant, int step) {
        Valve current = step % 2 == 0 ? me : elephant;

        if(been.size() == current.fastestWays.keySet().size() + 1) {
            return step % 2 == 0 ? current.flow * minutesMe  + elephant.flow * minutesElephant : current.flow * minutesElephant + me.flow * minutesMe;
        }

        int highestPressure = step % 2 == 0 ? current.flow * minutesMe : current.flow * minutesElephant;
        int max = Integer.MIN_VALUE;
        boolean change = false;

        for (Valve v : current.fastestWays.keySet()) {
            if (been.contains(v)) continue;
            int time = current.fastestWays.get(v) + 1;
            if(step % 2 == 0 ? minutesMe - time < 0 : minutesElephant - time < 0 ) continue;
            change = true;
            been.add(v);
            if (step % 2 == 0)
                max = Math.max(max, findWay2(v, elephant, been, minutesMe - time, minutesElephant, minutesElephant <= 0 ? step : step + 1));
            else
                max = Math.max(max, findWay2(me, v, been, minutesMe, minutesElephant - time, minutesMe <= 0 ? step : step + 1));
            been.remove(v);
        }

        if(!change)
            return step % 2 == 0 ? current.flow * minutesMe  + elephant.flow * minutesElephant : current.flow * minutesElephant + me.flow * minutesMe;

        return max < 0 ? highestPressure : highestPressure + max;
    }

    public static void fastestWay(Valve valve, List<Valve> importantValves, List<Valve> valves ) {
        for(Valve v : importantValves) {
            if(valve.equals(v)) continue;
            Queue<Valve> q = new LinkedList<>();
            Queue<Valve> q2 = new LinkedList<>();
            q.add(valve);
            boolean vFound = false;
            int counter = 0;
            List<Valve> been = new ArrayList<>(Arrays.asList(valve));
            while(!vFound) {
                counter++;
                while (!q.isEmpty()) {
                    Valve current = q.remove();
                    been.add(current);
                    if(current.equals(v)) {
                        vFound = true;
                        break;
                    }
                    for (String s : current.neighbours) {
                        if(!been.contains(valves.stream().filter(e -> e.name.equals(s)).toList().get(0))) {
                            q2.add(valves.stream().filter(e -> e.name.equals(s)).toList().get(0));
                        }
                    }
                }
                q = q2;
                q2 = new LinkedList<>();
            }
            valve.fastestWays.put(v, counter - 1);
        }
        valve.been = new boolean[valve.fastestWays.size()];
    }

    public static class Valve {

        String name;
        int flow;
        List<String> neighbours;
        boolean[] been;
        Map<Valve, Integer> fastestWays;

        public Valve(String[] values, String[] neighbours) {
            this.name = values[0];
            this.flow = Integer.parseInt(values[1]);
            this.neighbours = Arrays.stream(neighbours).toList();
            this.been = new boolean[this.neighbours.size()];
            this.fastestWays = new HashMap<>();
        }

    }



}
