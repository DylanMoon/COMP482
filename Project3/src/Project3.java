// Dylan Moon
// Project 3
// 12/01/2022  //TODO: this needs to be the assignment's due date.
// Find the number of potential Binary Search Trees in 1 node - N nodes

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class Project3 {
    public static void main(String[] args) throws IOException {
        var input = Integer.parseInt(Files.readAllLines(Paths.get("input.txt")).get(0));
        var cn = new CatalanNumber();
        System.out.println(cn.Calculate(input));
    }
}


class CatalanNumber {

    private BigInteger BigFactorial(BigInteger n) throws IllegalArgumentException{
        if( n.signum() < 0) throw new IllegalArgumentException("BigFactorial argument must be positive");
        return BigFactorialHelper(n, new HashMap<BigInteger, BigInteger>());
    }
    private BigInteger BigFactorialHelper(BigInteger n, HashMap<BigInteger,BigInteger> map){
        if(n.equals(BigInteger.ZERO) || n.equals(BigInteger.ONE)) return BigInteger.ONE;
        if(map.containsKey(n)){
            return map.get(n);
        }
        else{
            map.put(n, n.multiply(BigFactorialHelper(n.subtract(BigInteger.ONE), map)));
            return map.get(n);
        }
    }

    public BigInteger Calculate(BigInteger n) throws IllegalArgumentException{
        return BigFactorial(n.multiply(BigInteger.TWO)).divide(BigFactorial(n).multiply(BigFactorial(n.add(BigInteger.ONE))));
    }

    public BigInteger Calculate(int n) throws IllegalArgumentException{
        return Calculate(BigInteger.valueOf(n));
    }

    public <T> BigInteger Calculate(T input) throws IllegalArgumentException{
        return Calculate(new BigInteger(input.toString()));
    }

    public <T> void Test(T input) {
        try{
            System.out.print("input: " + input.toString());
            System.out.print(" --> " + Calculate(input));
        }
        catch (Exception e){
            System.err.print("Error: " + e);
        }
        finally {
            System.out.println();
        }
    }
}
