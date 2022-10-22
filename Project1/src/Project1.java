// Dylan Moon
// Project 1
// 10/5/2022

import java.io.*;
import java.util.*;

public class Project1 {
    public static void main(String[] args) {
        //TODO: DO NOT FORGET TO SET INPUT_FILE TO "input.txt" BEFORE SUBMISSION
        final String INPUT_FILE = "input.txt";
        var matches = new Matches(GetInput(INPUT_FILE)); // reads the input file and builds the structures to be used
        var count = 0;
        while(count < 100){
            var stability = matches.isStable();
            if((Boolean)stability.get(0))break;
            matches.Swap((int)stability.get(1), (int)stability.get(2));
            count++;
        }
        System.out.println(count);
        for (int i = 0; i < matches.matches.length; i++) {
            System.out.println(i + 1 + " " + matches.matches[i]);
        }
    }

    /**
     * Reads a file in (same directory as the java file)
     * @param filePath Path (including file name and extension) of the file to be read
     * @return List of Integers
     */
    public static List<Integer> GetInput(String filePath) {
        try {
            var sc = new Scanner(new File(filePath));
            var result = new ArrayList<Integer>();
            while (sc.hasNextInt()) {
                result.add(sc.nextInt());
            }
            sc.close();
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

    /**
     * Checks each man to see if the women that the man rates higher than his current match would prefer him to their current match too
     * @return Stable: [true]</p>Unstable: [false, man Id, woman Id]
     * @author Dylan Moon
     */
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

    /**
     * Swaps the matches between the unstable man and woman
     * @param manToSwap Id of the unstable man
     * @param womanToSwap Id of the unstable woman*/
    public void Swap(int manToSwap, int womanToSwap){
        var thirdWheelMan = GetWoman(womanToSwap).matchedTo;
        var thirdWheelWoman = GetMan(manToSwap).matchedTo;
        GetMan(manToSwap).matchedTo = womanToSwap;
        GetWoman(womanToSwap).matchedTo = manToSwap;
        GetMan(thirdWheelMan).matchedTo = thirdWheelWoman;
        GetWoman(thirdWheelWoman).matchedTo = thirdWheelMan;
        matches[manToSwap-1] = womanToSwap;
        matches[thirdWheelMan -1] = thirdWheelWoman;
    }

    /**
     * Chops up the input file into an object-oriented form
     * @param input the list of integers from the input file
     * */
    public Matches(List<Integer> input){
        int numPeople = input.get(0);
        men = new Person[numPeople];
        women = new Person[numPeople];
        for (int i = 0; i < numPeople; i++) {// this is messy, and I hate it
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
    /**
     * @param man Id of the man to return
     * @return {@code Person}
     * */
    public Person GetMan(int man){
        return men[man -1];
    }
    /**
     * @param woman Id of the woman to return
     * @return {@code Person}
     * */
    public Person GetWoman(int woman){
        return women[woman -1];
    }
}

class Person {
    public final int[] preferences;
    public final int Id;
    public int matchedTo;

    /**
     * ReOrders the preference list to be: index of the preference to check = their priority
     * @param id Id of the person
     * @param inputPrefs sublist from the input file
     * */
    public Person(List<Integer> inputPrefs, int id) {
        preferences = new int[inputPrefs.size()];
        for (int i = 0; i < inputPrefs.size(); i++) {
            preferences[inputPrefs.get(i) - 1] = i + 1;
        }
        Id = id;
    }

    /**
     * @param other Id of the other person to check
     * @return true if the Person would swap to the checked Id
     * */
    public boolean WouldSwap(int other) {
        return preferences[matchedTo - 1] > preferences[other - 1];
    }

    /**
     * @return List of the Ids of the People preferred over the current match
     * */
    public List<Integer> GetPref() {
        var temp = new ArrayList<Integer>();
        for (int i = 0; i < preferences.length; i++) {
            if(preferences[i] < preferences[matchedTo-1])temp.add(i+1);
        }
        return temp;
    }
}

