// Dylan Moon
// Project 1
// 11/9/2022

import java.io.*;
import java.util.*;

public class Project2 {
    //TODO: DO NOT FORGET TO SET INPUT_FILE TO "input.txt" BEFORE SUBMISSION
    final static String INPUT_FILE = "input.txt";

    public static void main(String[] args) {
        final var jobs = GetInput(INPUT_FILE);
        ProcessJobs(jobs, new EarliestDeadlineFirst());
        System.out.println();
        ProcessJobs(jobs, new ShortestJobFirst());
        System.out.println();
        ProcessJobs(jobs, new LeastSlackFirst());
    }

    public static List<Job> GetInput(String filepath) {
        try {
            var sc = new Scanner(new File(filepath));
            var result = new ArrayList<Job>();
            var count = sc.nextInt();
            for (int i = 0; i < count; i++) {
                result.add(new Job(sc.nextInt(), sc.nextInt()));
            }
            sc.close();
            return result;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return Collections.emptyList();
        }
    }

    public static void ProcessJobs(final List<Job> jobList, Comparator<Job> jobComparator){
        jobList.sort(jobComparator);
        System.out.println(jobComparator + " " + CountLate(jobList, 0, 0 ,0));
    }

    private static int CountLate(final List<Job> jobList, final int index, final int numLate, final int currentTime) {
        if (index >= jobList.size()) return numLate;
        var current = jobList.get(index);
        var endTime = currentTime + current.processingTime;
        var late = endTime <= current.deadline ? 0 : 1;

        System.out.println(current + " deadline: " + current.deadline + " completes at time: " + endTime + "\tlate: " + late);

        return CountLate(jobList, index + 1, numLate + late, endTime);
    }
}

class Job {
    final int processingTime;
    final int deadline;

    public Job(final int p, final int d) {
        processingTime = p;
        deadline = d;
    }

    @Override
    public String toString() {
        return "Job { " + processingTime + " : " + deadline + " }";
    }
}

class EarliestDeadlineFirst implements Comparator<Job> {
    @Override
    public int compare(Job job1, Job job2) {
        return job1.deadline - job2.deadline;
    }

    @Override
    public String toString() {
        return "EDF";
    }
}

class ShortestJobFirst implements Comparator<Job> {
    @Override
    public int compare(Job job1, Job job2) {
        return job1.processingTime - job2.processingTime;
    }

    @Override
    public String toString() {
        return "SJF";
    }
}

class LeastSlackFirst implements Comparator<Job> {
    @Override
    public int compare(Job job1, Job job2) {
        return (job1.deadline - job1.processingTime) - (job2.deadline - job2.processingTime);
    }

    @Override
    public String toString() {
        return "LSF";
    }
}
