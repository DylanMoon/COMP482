// Dylan Moon
// Project 2
// 11/9/2022

import java.io.File;
import java.util.*;

public class Project2 {
    //TODO: DO NOT FORGET TO SET INPUT_FILE TO "input.txt" BEFORE SUBMITTING
    final static String INPUT_FILE = "input.txt";

    public static void main(String[] args) {
        final var jobs = GetInput();
        ProcessJobs(jobs, new EarliestDeadlineFirst());
        ProcessJobs(jobs, new ShortestJobFirst());
        ProcessJobs(jobs, new LeastSlackFirst());
    }

    /**
     * Reads a file in the same directory as the java file
     * @implNote filename and path gotten from {@link Project2#INPUT_FILE} : {@value INPUT_FILE}
     * @return List of {@link Job}
     * @author Dylan Moon
     */
    private static List<Job> GetInput() {
        try (var sc = new Scanner(new File(INPUT_FILE))){
            var result = new ArrayList<Job>();
            var count = sc.nextInt();
            for (int i = 0; i < count; i++) {
                result.add(new Job(sc.nextInt(), sc.nextInt()));
            }
            return result;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * processes the jobs in order, and prints the sort method used and how many jobs finish late
     * @param jobList list of jobs to be processed
     * @param jobComparator comparator to be used in the sort
     * @author Dylan Moon
     */
    private static void ProcessJobs(final List<Job> jobList, final Comparator<Job> jobComparator){
        jobList.sort(jobComparator);
        System.out.println(jobComparator + " " + CountLateJobs(jobList, 0, 0 ,0));
    }

    /**
     * @param jobList list of jobs
     * @param index current job index being processed
     * @param numLate running total of late jobs
     * @param currentTime running current job completion time
     * @return total number of late jobs
     * @author Dylan Moon
     */
    private static int CountLateJobs(final List<Job> jobList, final int index, final int numLate, final int currentTime) {
        if (index >= jobList.size()) return numLate;
        var current = jobList.get(index);
        var endTime = currentTime + current.processingTime;
        var late = endTime <= current.deadline ? 0 : 1;

//        System.out.println(current + " completes at time: " + endTime + (late > 0 ? " late" : ""));

        return CountLateJobs(jobList, index + 1, numLate + late, endTime);
    }
}

/**
 * Represents a Job
 * @see Job#toString()
 * @author Dylan Moon
 */
final class Job {
    final int processingTime;
    final int deadline;

    /**
     * @param p  The processing time
     * @param d  The deadline
     * @author Dylan Moon
     */
    public Job(final int p, final int d) {
        processingTime = p;
        deadline = d;
    }

    /**
     * @return "Job { {@code processingTime} : {@code deadline}}"
     */
    @Override
    public String toString() {
        return "Job { " + processingTime + " : " + deadline + " }";
    }
}

/**
 * Compares {@link Job} deadlines
 * @implNote Overrides {@link #toString()} for specifying method.
 * @author Dylan Moon
 */
final class EarliestDeadlineFirst implements Comparator<Job> {
    @Override
    public int compare(Job job1, Job job2) {
        return job1.deadline - job2.deadline;
    }

    /**
     * @return "EDF"
     */
    @Override
    public String toString() {
        return "EDF";
    }
}

/**
 * Compares {@link Job} processing times
 * @implNote Overrides {@link #toString()} for specifying method.
 * @author Dylan Moon
 */
final class ShortestJobFirst implements Comparator<Job> {
    @Override
    public int compare(Job job1, Job job2) {
        return job1.processingTime - job2.processingTime;
    }

    /**
     * @return "SJF"
     */
    @Override
    public String toString() {
        return "SJF";
    }
}

/**
 * Compares {@link Job} slack
 * @implNote Overrides {@link #toString()} for specifying method.
 * @author Dylan Moon
 */
final class LeastSlackFirst implements Comparator<Job> {
    @Override
    public int compare(Job job1, Job job2) {
        return (job1.deadline - job1.processingTime) - (job2.deadline - job2.processingTime);
    }

    /**
     * @return "LSF"
     */
    @Override
    public String toString() {
        return "LSF";
    }
}
