// Dylan Moon
// Project 1
// 10/5/2022

import java.io.*;
import java.util.*;

public class Project1 {
    //TODO: DO NOT FORGET TO PUT THE INPUT FILENAME BACK TO "input.txt" BEFORE SUBMISSION
    private static final String FILENAME = "input3.txt";

    public static void main(String[] args) {
        var matches = new Matches(GetInput());
        matches.PrintMen();
        matches.PrintWomen();
        matches.PrintMatches();
    }

    public static List<Integer> GetInput() {
        try {
            var sc = new Scanner(new File(FILENAME));
            var result = new ArrayList<Integer>();
            while (sc.hasNextInt()) {
                result.add(sc.nextInt());
            }
            return result;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return Collections.emptyList();
        }
    }
}

class Matches{
    public final Person[] men;
    public final Person[] women;

    public final int[] matches;

    public Matches(List<Integer> input){
        System.out.println(input.size());
        int numPeople = input.get(0);
        men = new Person[numPeople];
        women = new Person[numPeople];
        for (int i = 0; i < numPeople; i++) {
            men[i] = new Person(input.subList((i * numPeople) + 1, ((i + 1) * numPeople) + 1));
            women[i] = new Person(input.subList(((i + numPeople) * numPeople) + 1, (((i + numPeople) + 1) * numPeople) + 1));
        }
        matches = new int[numPeople];
        for (int i = 0; i < numPeople; i++) {
            matches [input.get(1 + ((numPeople * numPeople) << 1) + (i << 1)) - 1] = input.get(2 + ((numPeople * numPeople) << 1) + (i << 1));
        }
    }

    public Person GetMan(int man){
        return men[man -1];
    }

    public Person GetWoman(int woman){
        return women[woman -1];
    }

    public void PrintMen(){
        System.out.println("Men");
        for (var dude : men) {
            System.out.println(dude);
        }
    }
    public void PrintWomen(){
        System.out.println("Women");
        for (var brosephina : women) {
            System.out.println(brosephina);
        }
    }
    public void PrintMatches(){
        System.out.println("Matches");
        System.out.println(Arrays.toString(matches));
    }
}

class Person {
    private final int[] preferences;
    public Person(List<Integer> inputPrefs) {
        preferences = new int[inputPrefs.size()];
        for (int i = 0; i < inputPrefs.size(); i++) {
            preferences[inputPrefs.get(i) - 1] = i + 1;
        }
    }
    public int GetPref(int person){
        return preferences[person -1];
    }

    @Override
    public String toString() {
        return Arrays.toString(preferences);
    }
}

