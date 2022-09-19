// Dylan Moon
// Project 1
// 10/5/2022

import java.io.*;
import java.util.*;

public class Project1 {
    public static void main(String[] args) {
        var matches = new Matches(GetInput());
        var count = 0;
        while(count < 100){
            var stability = matches.isStable();
            if((Boolean)stability.get(0))break;
//            System.out.println(stability);
            count++;
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
        //TODO: DO NOT FORGET TO PUT THE INPUT FILENAME BACK TO "input.txt" BEFORE SUBMISSION
        try {
            var sc = new Scanner(new File("input3.txt"));
            var result = new ArrayList<Integer>();
            while (sc.hasNextInt()) {
                result.add(sc.nextInt());
            }
            return Collections.unmodifiableList(result);
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
            var womenWouldLeaveFor = man.GetPref();
            for (var woman : womenWouldLeaveFor){
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

    public Matches(List<Integer> input){
        int numPeople = input.get(0);
        men = new Person[numPeople];
        women = new Person[numPeople];
        for (int i = 0; i < numPeople; i++) {
            men[i] = new Person(input.subList((i * numPeople) + 1, ((i + 1) * numPeople) + 1), i + 1);
            women[i] = new Person(input.subList(((i + numPeople) * numPeople) + 1, (((i + numPeople) + 1) * numPeople) + 1), i +1);
        }
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
}

