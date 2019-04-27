

public class Main {

    public static void main(String[] args) {
        Puzzle ob = new Puzzle();
        boolean solved = false;
        //Puzzle erzeugen
        int puzzle [][] = ob.randomPuzzle();
        ob.printPuzzle(puzzle);
        solved = ob.dfs_beschraenkt(puzzle, 10);
        if(!solved){System.out.println("Puzzle konnte nicht gelöst werden");};

    }

}
