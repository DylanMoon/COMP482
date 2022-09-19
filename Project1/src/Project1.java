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
//        matches.PrintMen();
//        matches.PrintWomen();
//        matches.PrintMatches();
        var count = 0;
        while(count < 100){
            var stability = matches.isStable();
            if((Boolean)stability.get(0))break;
            count++;
            //some logic to swap the unstable pair's spouses
            var manToSwap = (int)stability.get(1);
            var womanToSwap = (int)stability.get(2);
            var thirdWheelMan = matches.GetWoman(womanToSwap).matchedTo;
            var thirdWheelWoman = matches.GetMan(manToSwap).matchedTo;
            matches.GetMan(manToSwap).matchedTo = womanToSwap;
            matches.GetWoman(womanToSwap).matchedTo = manToSwap;
            matches.GetMan(thirdWheelMan).matchedTo = thirdWheelWoman;
            matches.GetWoman(thirdWheelWoman).matchedTo = thirdWheelMan;
            matches.matches[manToSwap-1] = womanToSwap;
            matches.matches[thirdWheelMan -1] = thirdWheelWoman;
        }
        System.out.println(count);
        for (int i = 0; i < matches.matches.length; i++) {
            System.out.println(i + 1 + " " + matches.matches[i]);
        }
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

    public final ArrayList<Object> isStable(){
        var result = new ArrayList<Object>();
        for (var man : men) {
//            System.out.println("Checking man " + man.Id);
            var womenWouldLeaveFor = man.GetPref();
//            System.out.println(womenWouldLeaveFor);
            for (var woman : womenWouldLeaveFor){
//                System.out.println("Checking woman " + woman);
                if(GetWoman(woman).WouldSwap(man.Id)) {
                    result.add(false);
                    result.add(man.Id);
                    result.add(woman);
                    return result;
                }
            }
        }
        result.add(true);
        return result;
    }

    //ho-boy, I do not like java and how it handles things, so I ended up with this stupid constructor to fit things into classes
    public Matches(List<Integer> input){
        int numPeople = input.get(0); //uses the first number to set the number of men and women
        men = new Man[numPeople]; // creates an array of men corresponding to the number of people
        women = new Woman[numPeople]; // creates an array of women corresponding to the number of people
        for (int i = 0; i < numPeople; i++) {
            //populates the arrays of men and women with a People class, which takes in a list of numbers corresponding to the preference list of that man/woman
            men[i] = new Man(input.subList((i * numPeople) + 1, ((i + 1) * numPeople) + 1), i + 1);
            women[i] = new Woman(input.subList(((i + numPeople) * numPeople) + 1, (((i + numPeople) + 1) * numPeople) + 1), i +1);
        }
        //builds the match
        matches = new int[numPeople];
        for (int i = 0; i < numPeople; i++) {
            matches [input.get(1 + ((numPeople * numPeople) << 1) + (i << 1)) - 1] = input.get(2 + ((numPeople * numPeople) << 1) + (i << 1));
        }
        for (int i = 0; i < numPeople; i++) {
            GetMan(i+1).matchedTo = matches[i];
            GetWoman(GetMan(i+1).matchedTo).matchedTo = i+1;
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

class Man extends Person{
    public Man(List<Integer> inputPrefs, int id) {
        super(inputPrefs, id);
    }
    @Override
    public boolean equals(Object o){
        if(o == this)return true;
        if(!(o instanceof Man other)) return false;
        return other.Id == this.Id;
    }
}

class Woman extends Person{
    public Woman(List<Integer> inputPrefs, int id) {
        super(inputPrefs, id);
    }
    @Override
    public boolean equals(Object o){
        if(o == this)return true;
        if(!(o instanceof Woman other)) return false;
        return other.Id == this.Id;
    }
}

class Person {
    public final int[] preferences;

    public final int Id;
    public int matchedTo;

    public Person(List<Integer> inputPrefs, int id) {
        preferences = new int[inputPrefs.size()];
        for (int i = 0; i < inputPrefs.size(); i++) {
            preferences[inputPrefs.get(i) - 1] = i + 1;
        }
        Id = id;
    }

    public boolean WouldSwap(int other) {
        return preferences[matchedTo - 1] > preferences[other - 1];
    }

    public List<Integer> GetPref() {
        var temp = new ArrayList<Integer>();
        for (int i = 0; i < preferences.length; i++) {
            if(preferences[i] < preferences[matchedTo-1])temp.add(i+1);
        }
        return temp;
    }

    @Override
    public String toString() {
        return Arrays.toString(preferences);
    }
}

