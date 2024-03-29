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

    /**
     * Entry point for a binary(ish) search of a 2D array
     * @param target {@link Integer} that is the search target
     * @param arr 2D array to be searched
     * @return {@link String} 1 indexed coordinates of the target or 'NOT FOUND' if not found
     * @author Dylan Moon
     */
    public static String BinarySearch2d(final int target, final int[][] arr){
        return BinarySearch2dRecursive(target, arr, 0, arr.length -1, 0, arr[0].length -1).toString();
    }


    /**
     * Recursive case for a 2D array binary(ish) search
     * @param target {@link Integer} that is the search target
     * @param arr 2D array to be searched
     * @param rlb Row Lower Bound - 0 based index
     * @param rub Row Upper Bound - 0 based index
     * @param clb Column Lower Bound - 0 based index
     * @param cub Column Upper Bound - 0 based index
     * @return {@link Result}: {@link Found} if found, {@link NotFound} if not found
     * @author Dylan Moon
     */
    private static Result BinarySearch2dRecursive(final int target, final int[][] arr, final int rlb, final int rub, final int clb, final int cub){
        if(rub < rlb || cub < clb)return Result.NotFound();
        if(rub == rlb) return RowBinarySearch(target, arr, clb, cub, rub);
        if(cub == clb) return ColumnBinarySearch(target, arr, rlb, rub, cub);
        var row = (rlb + rub) / 2;
        int column = (clb + cub) / 2;
        var curr = arr[row][column];
        if(curr == target) return Result.Found(row, column);
        else if (curr < target) {
            var r1 = BinarySearch2dRecursive(target, arr, rlb, row, column + 1, cub);
            if(r1 instanceof Found)return r1;
            var r2 = BinarySearch2dRecursive(target, arr, row + 1, rub, clb, column);
            if(r2 instanceof Found)return r2;
            return BinarySearch2dRecursive(target, arr, row + 1, rub, column + 1, cub);
        }
        else{
            var r1 = BinarySearch2dRecursive(target, arr, rlb, row - 1, clb, column);
            if(r1 instanceof Found)return r1;
            var r2 = BinarySearch2dRecursive(target, arr, rlb, row -1, column + 1, cub);
            if(r2 instanceof Found)return r2;
            return BinarySearch2dRecursive(target, arr, row, rub, clb, column - 1);
        }
    }

    /**
     * Standard Binary search in a single row
     * @param target {@link Integer} that is the search target
     * @param arr 2D array to be searched
     * @param lowerBound 0 based index column lower bound
     * @param upperBound 0 based index column upper bound
     * @param row the row to be searched
     * @return {@link Result}: {@link Found} if found, {@link NotFound} if not found
     * @author Dylan Moon
     */
    private static Result RowBinarySearch(final int target, final int[][] arr, final int lowerBound, final int upperBound, final int row){
        if(lowerBound > upperBound) return Result.NotFound();
        if(lowerBound == upperBound) return arr[row][lowerBound] == target ?
                Result.Found(row, lowerBound):
                Result.NotFound();
        var index = (lowerBound + upperBound) /2;
        var curr = arr[row][index];
        if(curr == target){
            return Result.Found(row, index);
        }
        else if(curr < target){
            return RowBinarySearch(target, arr, index + 1, upperBound, row);
        }
        else {
            return RowBinarySearch(target, arr, lowerBound, index -1, row);
        }
    }

    /**
     * Standard Binary search in a single column
     * @param target {@link Integer} that is the search target
     * @param arr 2D array to be searched
     * @param lowerBound 0 based index row lower bound
     * @param upperBound 0 based index row upper bound
     * @param column the column to be searched
     * @return {@link Result}: {@link Found} if found, {@link NotFound} if not found
     * @author Dylan Moon
     */
    private static Result ColumnBinarySearch(final int target, final int[][] arr, final int lowerBound, final int upperBound, final int column){
        if(lowerBound > upperBound) return Result.NotFound();
        if(lowerBound == upperBound) return arr[lowerBound][column] == target ?
                Result.Found(lowerBound, column):
                Result.NotFound();
        var index = (lowerBound + upperBound) /2;
        var curr = arr[index][column];
        if(curr == target){
            return Result.Found(index, column);
        }
        else if(curr < target){
            return ColumnBinarySearch(target, arr, index + 1, upperBound, column);
        }
        else {
            return ColumnBinarySearch(target, arr, lowerBound, index -1, column);
        }
    }
}


/**
 * Abstract class for the result pattern
 */
abstract class Result{
    public static Result Found(final int row, final int column){
        return new Found(row, column);
    }
    public static Result NotFound(){
        return new NotFound();
    }
}

/**
 * Result returned if target is found.
 * Uses toString() to return the 1 indexed coordinates of the target
 */
final class Found extends Result{
    final int Row;
    final int Column;
    Found(final int row, final int column){
        Row=row + 1;
        Column = column + 1;
    }
    @Override
    public String toString() {
        return Row + " " + Column;
    }
}

/**
 * Result returned if target is not found.
 * Uses toString() to return the message 'NOT FOUND'
 */
final class NotFound extends Result{
    @Override
    public String toString() {
        return "NOT FOUND";
    }
}


/**
 * Class used to 'deserialize' the input into
 */
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
