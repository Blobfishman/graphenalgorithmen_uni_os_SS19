

public class Main {

    public static void main(String[] args) {
        Puzzle ob = new Puzzle();
        boolean solved = false;
        solved = ob.dfs_beschraenkt(ob.randomPuzzle(), 20);
        if(!solved){
            System.out.println("Puzzle konnte nicht gelöst werden");
        }else{
            ob.printStack();
        }
    }

}
