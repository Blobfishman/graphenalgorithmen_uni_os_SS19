import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

class Puzzle {

    private Stack<int[][]> stack = new Stack<>();
    private Stack<int[][]> stackOut = new Stack<>();

    boolean dfs_beschraenkt(int[][] puzzle, int q){
        int[][] i;
        int[][] j;
        int[][] defaultPuzzle = new int[3][3];
        stack.push(defaultPuzzle);
        printPuzzle(puzzle);
        //Lösung des Puzzles
        int[][] puzzleLoesung = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        if( equalsPuzzle(puzzle,puzzleLoesung)) { printPuzzle(puzzle); return true;}
        stack.push(puzzle);
        do{
            i = stack.peek();
            j = nextNeighbor(i);
            int[][] lastPop = new int[3][3];
            if (j != null){
                if( equalsPuzzle(j,puzzleLoesung)) {stack.push(j); return true;}
                if (!searchPuzzle(stack,j) && stack.search(defaultPuzzle) <= q+1){
                    stack.push(j);
                }else{
                    lastPop = stack.pop();
                    stackOut.push(lastPop);
                }
            }else {
                lastPop = stack.pop();
                stackOut.push(lastPop);
            }
        }while(!equalsPuzzle(stack.peek(),defaultPuzzle));
        return false;
    }

    private int[][] nextNeighbor(int[][] tmppuzzle){
        int x = 0;
        int y = 0;
        int tmp = 0;
        int[][] refPuzzle = clonePuzzle(tmppuzzle);
        int[][] puzzle = clonePuzzle(tmppuzzle);

        for (int i = 0; i < 3; ++i){
            for (int j = 0; j < 3;++j){
                if (puzzle[i][j] == 0){ x = i; y = j;};
            }
        }

        if(x+1 < 3){
            switchNumbers(puzzle, x,y,x+1,y);
            if(!searchPuzzle(stack,puzzle) && !searchPuzzle(stackOut,puzzle)){return puzzle;}
            puzzle = clonePuzzle(refPuzzle);
        }
        if (x-1 >= 0){
            switchNumbers(puzzle, x,y,x-1,y);
            if(!searchPuzzle(stack,puzzle) && !searchPuzzle(stackOut,puzzle)){return puzzle;}
            puzzle = clonePuzzle(refPuzzle);
        }
        if (y+1 < 3){
            switchNumbers(puzzle, x,y,x,y+1);
            if(!searchPuzzle(stack,puzzle) && !searchPuzzle(stackOut,puzzle)){return puzzle;}
            puzzle = clonePuzzle(refPuzzle);
        }
        if (y-1 >= 0){
            switchNumbers(puzzle, x,y,x,y-1);
            if(!searchPuzzle(stack,puzzle) && !searchPuzzle(stackOut,puzzle)){return puzzle;}
        }
        return null;
    }

    private void switchNumbers(int[][] puzzle, int x1, int y1, int x2, int y2){
        int tmp = 0;
        tmp = puzzle[x2][y2];
        puzzle[x1][y1] = tmp;
        puzzle[x2][y2] = 0;
    }

    int[][] randomPuzzle(){
        int[][] puzzle = new int[3][3];
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i=0; i<9; i++) {
            list.add(i);
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

    private int[][] clonePuzzle(int[][] tmp){
        int[][] puzzle = new int[3][3];
        for (int i = 0; i < 3;++i){
            System.arraycopy(tmp[i], 0, puzzle[i], 0, 3);
        }
        return puzzle;
    }

    private boolean equalsPuzzle(int[][] puzzle1, int[][] puzzle2){
        for (int i = 0; i < 3;++i){
            for (int j = 0; j < 3;++j){
                if(puzzle1[i][j] != puzzle2[i][j]){
                    return false;
                }
            }
        }
        return true;
    }

    private boolean searchPuzzle(Stack<int[][]> stack, int[][] puzzle){
        for (int[][] ints : stack) {
            if (equalsPuzzle(ints, puzzle)) {
                return true;
            }
        }
        return false;
    }

    private void printPuzzle(int[][] puzzle) {

        for(int i = 0; i < 3;++i){
            System.out.println(puzzle[i][0] + "  " + puzzle[i][1] + "  " + puzzle[i][2]);
        }
        System.out.println("##################################");

    }

    void printStack(){
        for (int[][] ints : stack) {
            printPuzzle(ints);
        }
    }
}
