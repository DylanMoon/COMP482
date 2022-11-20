// Dylan Moon
// Project 3
// 11/30/2022

import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

public class Project3 {
    //TODO: DO NOT FORGET TO SET INPUT_FILE TO "input.txt" BEFORE SUBMITTING
    final static String INPUT_FILE = "input.txt";

    public static void main(String[] args) {
        final var input = GetInput();
//        System.out.println(input);
//        System.out.println("Target :" + input.target);
        System.out.println(BinarySearch2d(input.target, input.arr));
    }

    /**
     * Reads a file in the same directory as the java file
     * @implNote filename and path gotten from {@link Project3#INPUT_FILE} : {@value INPUT_FILE}
     * @return 2D array of {@link Integer}
     * @author Dylan Moon
     */
    private static ArrayAndTarget GetInput() {
        try (var sc = new Scanner(new File(INPUT_FILE))){
            var rows = sc.nextInt();
            var columns = sc.nextInt();
            var target = sc.nextInt();
            var result = new ArrayAndTarget(rows, columns, target, new int[rows][columns]);
            for (int row = 0; row < rows; row++) {
                for(int column = 0; column < columns; column++){
                    result.arr[row][column]=sc.nextInt();
                }
            }
            return result;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return new ArrayAndTarget(0,0,0,new int[0][0]);
        }
    }

    private static String BinarySearch2d(final int target, final int[][] arr){
        var result = BinarySearch2dRecursive(target, arr, 0, arr.length -1, 0, arr[0].length -1);
        if(result.Found) return result.Row + " " + result.Column;
        return "NOT FOUND";
    }
    private static Result BinarySearch2dRecursive(final int target, final int[][] arr, final int rlb, final int rub, final int clb, final int cub){
//        System.out.println("BinarySearch2dRecursive");
        if(rub < rlb || cub < clb)return Result.False();
        if(rub == rlb) return RowBinarySearch(target, arr, clb, cub, rub);
        if(cub == clb) return ColumnBinarySearch(target, arr, rlb, rub, cub);
        var row = (rlb + rub) / 2;
        int column = (clb + cub) / 2;
        var curr = arr[row][column];
        if(curr == target) return Result.True(row, column);
        else if (curr < target) {
            var r1 = BinarySearch2dRecursive(target, arr, rlb, row, column + 1, cub);
            if(r1.Found)return r1;
            var r2 = BinarySearch2dRecursive(target, arr, row + 1, rub, clb, column);
            if(r2.Found)return r2;
            var r3 = BinarySearch2dRecursive(target, arr, row + 1, rub, column + 1, cub);
            if(r3.Found)return r3;
        }
        else{
            var r1 = BinarySearch2dRecursive(target, arr, rlb, row - 1, clb, column);
            if(r1.Found)return r1;
            var r2 = BinarySearch2dRecursive(target, arr, rlb, row -1, column + 1, cub);
            if(r2.Found)return r2;
            var r3 = BinarySearch2dRecursive(target, arr, row, rub, clb, column - 1);
            if(r3.Found)return r3;
        }
        return Result.False();
    }

    private static Result RowBinarySearch(final int target, final int[][] arr, final int lowerBound, final int upperBound, final int row){
//        System.out.println("RowBinarySearch");
        if(lowerBound > upperBound) return Result.False();
        if(lowerBound == upperBound) return arr[row][lowerBound] == target ?
                Result.True(row, lowerBound):
                Result.False();
        var index = (lowerBound + upperBound) /2;
        var curr = arr[row][index];
        if(curr == target){
            return Result.True(row, index);
        }
        else if(curr < target){
            return RowBinarySearch(target, arr, index + 1, upperBound, row);
        }
        else {
            return RowBinarySearch(target, arr, lowerBound, index -1, row);
        }
    }

    private static Result ColumnBinarySearch(final int target, final int[][] arr, final int lowerBound, final int upperBound, final int column){
//        System.out.println("ColumnBinarySearch");
        if(lowerBound > upperBound) return new Result(false);
        if(lowerBound == upperBound) return arr[lowerBound][column] == target ?
                Result.True(lowerBound, column):
                Result.False();
        var index = (lowerBound + upperBound) /2;
        var curr = arr[index][column];
        if(curr == target){
            return Result.True(index, column);
        }
        else if(curr < target){
            return ColumnBinarySearch(target, arr, index + 1, upperBound, column);
        }
        else {
            return ColumnBinarySearch(target, arr, lowerBound, index -1, column);
        }
    }
}

final class Result{
    final boolean Found;
    final int Row;
    final int Column;
    public Result(final boolean found){
        Found = found;
        Row=0;
        Column = 0;
    }
    public Result(final boolean found, final int row, final int column){
        Found = found;
        Row = row;
        Column = column;
    }

    public static Result False(){
        return new Result(false);
    }

    public static Result True(final int row, final int column){
        return new Result(true, row + 1, column + 1);
    }
}

final class ArrayAndTarget {
    final int rows;
    final int columns;
    final int target;
    final int[][] arr;

    public ArrayAndTarget(final int r, final int c, final int t, final int[][] a){
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
