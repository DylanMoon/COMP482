// Dylan Moon
// Project 3
// 11/30/2022

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Project3 {
    //TODO: DO NOT FORGET TO SET INPUT_FILE TO "input.txt" BEFORE SUBMITTING
    final static String INPUT_FILE = "input.txt";

    public static void main(String[] args) {
        final var input = GetInput();
        System.out.println(input);
        System.out.println("Target :" + input.target);
        BinarySearch2d(input.target, input.arr);
    }

    /**
     * Reads a file in the same directory as the java file
     * @implNote filename and path gotten from {@link Project3#INPUT_FILE} : {@value INPUT_FILE}
     * @return 2D array of {@link Integer}
     * @author Dylan Moon
     */
    private static IDK GetInput() {
        try (var sc = new Scanner(new File(INPUT_FILE))){
            var rows = sc.nextInt();
            var columns = sc.nextInt();
            var target = sc.nextInt();
            var result = new IDK(rows, columns, target, new int[rows][columns]);
            for (int row = 0; row < rows; row++) {
                for(int column = 0; column < columns; column++){
                    result.arr[row][column]=sc.nextInt();
                }
            }
            return result;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return new IDK(0,0,0,new int[0][0]);
        }
    }

    private static ArrayList<Object> BinarySearch2d(final int target, final int[][] arr){
        return BinarySearch2dRecursive(target, arr, 0, arr.length -1, 0, arr[0].length -1);
    }
    private static ArrayList<Object> BinarySearch2dRecursive(final int target, final int[][] arr, final int rlb, final int rub, final int clb, final int cub){
        if(rub < rlb || cub < clb)return new ArrayList<Object>(){{add(false);}};
        if(rub == rlb || cub == clb) return new ArrayList<Object>(){{add(false);}};
        System.out.println("BinSearch | rlb: " + rlb + " rub: " + rub + " clb: " + clb + " cub: " + cub);
        var row = (rlb + rub) / 2;
        int column = (clb + cub) / 2;
        var curr = arr[row][column];
        System.out.println("Checking position: (" + row + "," + column + ") = " + curr);
        if(curr == target) return new ArrayList<Object>(){{add(true); add(row); add(column);}};
        else if (curr < target) {
            var r1 = BinarySearch2dRecursive(target, arr, rlb, row, column + 1, cub);
            var r2 = BinarySearch2dRecursive(target, arr, row + 1, rub, clb, column);
            var r3 = BinarySearch2dRecursive(target, arr, row + 1, rub, column + 1, cub);
        }
        else{
            var r1 = BinarySearch2dRecursive(target, arr, rlb, row - 1, clb, column);
            var r2 = BinarySearch2dRecursive(target, arr, rlb, row -1, column + 1, cub);
            var r3 = BinarySearch2dRecursive(target, arr, row, rub, clb, column - 1);
        }
        return new ArrayList<>(){{add(false);}};
    }
}

final class IDK{
    final int rows;
    final int columns;
    final int target;
    final int[][] arr;

    public IDK(final int r, final int c, final int t, final int[][] a){
        rows = r;
        columns = c;
        target = t;
        arr = a;
    }

    @Override
    public String toString(){
        return Arrays.deepToString(arr);
    }
}
