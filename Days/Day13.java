import jdk.swing.interop.SwingInterOpUtils;

import java.util.*;

public class Day13 {

    public static void main(String[] args) {
        System.out.println("part1: " + part1(AOC.input(13)));
        System.out.println("part2: " + part2(AOC.input(13)));
    }

    private static int part1(List<String> input) {
        List<PacketPair> packetPairs = new ArrayList<>();
        for(int i = 0; i < input.size(); i++) {
                packetPairs.add(new PacketPair(new Packet(input.get(i++).substring(1)), new Packet(input.get(i++).substring(1))));
        }
        int sum = 0;
        for (int i = 0; i < packetPairs.size(); i++) {
            if(inOfOrder(packetPairs.get(i).left, packetPairs.get(i).right)) {
                sum+=i+1;
            }
        }

        return sum;
    }

    private static int part2(List<String> input) {
        List<Packet> packetList = new ArrayList<>(Arrays.asList(new Packet("[[2]]"), new Packet("[[6]]")));

        for (int i = 0; i < input.size(); i++) {
            if(input.get(i).equals("")) continue;
            packetList.add(new Packet(input.get(i).substring(1)));
        }
        String divider1 = packetList.get(0).name;
        String divider2 = packetList.get(1).name;

        packetList = sort(packetList);

        return (packetList.indexOf(packetList.stream().filter(p -> p.name!=null && p.name.equals(divider1)).toList().get(0))+1) *
                (packetList.indexOf(packetList.stream().filter(p -> p.name!= null && p.name.equals(divider2)).toList().get(0))+1);
    }

    private static List<Packet> sort(List<Packet> packets) {
        for (int i = 1; i < packets.size(); i++) {
            for (int j = i; j >= 1; j--) {
                Packet l = new Packet(packets.get(j-1));
                Packet r = new Packet(packets.get(j));
                boolean help = inOfOrder(packets.get(j - 1) , packets.get(j ));
                if(!help){
                    packets.set(j, l);
                    packets.set(j-1, r);
                }else {
                    packets.set(j, r );
                    packets.set(j-1, l);
                    break;
                }
            }
        }
        return packets;
    }

    private static boolean inOfOrder(Packet left, Packet right) {

        Stack<Packet> stackL = new Stack<>();
        Stack<Packet> stackR = new Stack<>();
        stackL.push(left);
        stackR.push(right);

        while(!stackL.isEmpty() && !stackR.isEmpty()) {
            left = stackL.pop();
            right = stackR.pop();

            if(left.packets == null && right.packets == null){
                if(left.x < right.x) return true;
                if(left.x > right.x) return false;
                continue;
            }

            if(left.packets != null && right.packets != null) {
                if(left.packets.isEmpty() && !right.packets.isEmpty()) return true;
                if(!left.packets.isEmpty() && right.packets.isEmpty()) return false;
                if(left.packets.isEmpty() && right.packets.isEmpty()) continue;
                stackL.push(left);
                stackR.push(right);
                stackL.push(left.packets.remove(0));
                stackR.push(right.packets.remove(0));
            }

            if(left.packets == null) {
                Packet current = right;
                List<Packet> temp = new ArrayList<>();
                while (current.packets != null && !current.packets.isEmpty()) {
                    temp.add(current);
                    current = current.packets.get(0);
                }
                if (left.x < current.x) return true;
                if (left.x > current.x) return false;
                if(temp.size() > 1) return true;
                if(temp.stream().filter(p -> p.packets.size() > 1).count() > 0) return true;
                if(current.x == -1) return true;
            }else if(right.packets == null) {
                List<Packet> temp = new ArrayList<>();
                Packet current = left;
                while(current.packets != null && !current.packets.isEmpty()){
                    temp.add(current);
                    current = current.packets.get(0);
                }
                if(current.x < right.x) return true;
                if(current.x > right.x) return false;
                if(temp.size() > 1) return false;
                if(temp.stream().filter(p -> p.packets.size() > 1).count() > 0) return false;
                if(current.x == -1) return false;
            }
        }
        return true;
    }



    private static class Packet{
        int x = -1;
        String name;
        List<Packet> packets;
        public Packet(int x) {
            this.x = x;
        }

        public Packet(Packet packet) {
            this.x = packet.x;
            this.name = packet.name;
            if(packet.packets == null) return;
            this.packets = new ArrayList<>();
            for(Packet p : packet.packets) {
                this.packets.add(new Packet(p));
            }
        }

        public Packet(String s) {
            if(s.charAt(0) == ']') return;
            this.name = s;
            this.packets = new ArrayList<>();
            Stack<Character> stack = new Stack<>();
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                switch (c) {
                    case '[':
                        if(stack.isEmpty()) packets.add(new Packet(s.substring(i+1)));
                        stack.add(c);
                        break;
                    case ',':
                        break;
                    case ']':
                        if(stack.isEmpty()) return;
                        stack.pop();
                        break;
                    default:
                        if(stack.isEmpty()) {
                            try{
                                Integer.parseInt(s.substring(i, i + 2));
                                packets.add(new Packet(Integer.parseInt(s.substring(i, i+2))));
                                i++;
                            }catch (NumberFormatException e) {
                                packets.add(new Packet(Integer.parseInt(s.substring(i,i+1))));
                            }
                        }
                        break;
                }
            }
        }
    }

    private static class PacketPair {
        Packet left;
        Packet right;

        public PacketPair(Packet left, Packet right) {
            this.left = left;
            this.right = right;
        }
    }


}



