import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class Puzzle {

    Stack<int[][]> stack = new Stack<>();

    public boolean dfs_beschraenkt(int puzzle[][],  int q){
        int i [][];
        int j [][];
        stack.push(null);
        //Lösung des Puzzles
        int puzzleLoesung [][] = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0} };
        if( puzzle.equals(puzzleLoesung)) { printPuzzle(puzzle); return true;};
        stack.push(puzzle.clone());
        do{
            i = stack.peek();
            j = nextNeighbor(i);
            if (j != null){
                if( j.equals(puzzleLoesung)) {printPuzzle(j); return true;};
                if (stack.search(j) == -1 && stack.search(null) < q){
                    stack.push(j);
                }
            }
            else {
                stack.pop();
            }
        }while(stack.peek() == null);
        return false;
    }

    public int[][] nextNeighbor(int tmppuzzle[][]){
        int x = 0;
        int y = 0;
        int tmp = 0;
        int refPuzzle[][] = tmppuzzle.clone();
        int puzzle[][] = tmppuzzle.clone();

        for (int i = 0; i < 3; ++i){
            for (int j = 0; j < 3;++j){
                if (puzzle[i][j] == 0){ x = i; y = j;};
            }
        }

        if(x+1 < 3){
            puzzle = switchNumbers(puzzle, x,y,x+1,y);
            System.out.println(stack.search(puzzle));
            if(stack.search(puzzle) == -1){return puzzle;}
            puzzle = refPuzzle.clone();
        }
        if (x-1 >= 0){
            puzzle = switchNumbers(puzzle, x,y,x-1,y);
            if(stack.search(puzzle) == -1){return puzzle;}
            puzzle = refPuzzle.clone();
        }
        if (y+1 < 3){
            puzzle = switchNumbers(puzzle, x,y,x,y+1);
            if(stack.search(puzzle) == -1){return puzzle;}
            puzzle = refPuzzle.clone();
        }
        if (y-1 >= 0){
            puzzle = switchNumbers(puzzle, x,y,x,y-1);
            if(stack.search(puzzle) == -1){return puzzle;}
            puzzle = refPuzzle.clone();
        }
        return null;
    }

    public int[][] switchNumbers(int puzzle[][], int x1, int y1, int x2, int y2){
        int tmp = 0;
        tmp = puzzle[x2][y2];
        puzzle[x1][y1] = tmp;
        puzzle[x2][y2] = 0;
        return puzzle;
    }

    public int[][] randomPuzzle(){
        int puzzle[][] = new int[3][3];
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i=0; i<9; i++) {
            list.add(new Integer(i));
        }
        Collections.shuffle(list);
        for (int i = 0; i < 3; ++i){
            for (int j = 0; j < 3;++j){
                puzzle[i][j] = list.get(0);
                list.remove(0);
            }
        }
        return puzzle;
    }

    public void printPuzzle(int puzzle[][]) {

        for(int i = 0; i < 3;++i){
            System.out.println(puzzle[i][0] + "  " + puzzle[i][1] + "  " + puzzle[i][2]);
        }
        System.out.println("##################################");

    }
}
