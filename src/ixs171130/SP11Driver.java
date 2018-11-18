package ixs171130;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Random;


public class SP11Driver {
    public static Random random = new Random();
    public static int numTrials = 10;

    public static void main(String[] args) throws Exception {
        int k = 0, size = 0;
        int choice = 4;
        if (args.length > 0) {
            size = Integer.parseInt(args[0]);
        }
        if (args.length > 1) {
            k = Integer.parseInt(args[1]);
        }
        if (args.length > 2) {
            choice = Integer.parseInt(args[2]);
        }
        Comparable[] arr = new Comparable[size];
        for (int i = 0; i < size; i++) {
            arr[i] = i + 1;
        }
        Timer timer = new Timer();
        switch (choice) {
            // for testing
            case 1:
                Shuffle.shuffle(arr);
                for (int i = 0; i < k; i++)
                    System.out.print(arr[i] + " ");
                System.out.println();
                Select.select(arr, k);
                for (int i = 0; i < k; i++)
                    System.out.print(arr[i] + " ");
                break;
            case 2:
                for (int i = 0; i < numTrials; i++) {
                    Shuffle.shuffle(arr);
                    Select.select(arr, k);
                }
                break;
            case 3:
                for (int i = 0; i < numTrials; i++) {
                    Shuffle.shuffle(arr);
                    Comparable[] res = new Comparable[k];
                    // our kthLargest method needs an iterator. Fastest method for array to iterator is converting to list
                    Iterator<Comparable> it = Arrays.asList(arr).iterator();
                    Comparator c = Comparator.reverseOrder();
                    BinaryHeap.kLargest(it, res, c);
                }
            default:
                for (int i = 0; i < numTrials; i++) {
                    Shuffle.shuffle(arr);
                }
                break;

        }
        timer.end();
        timer.scale(numTrials);

        System.out.println("Choice: " + choice + "\n" + timer);
    }

    public static class Shuffle {

        public static void shuffle(int[] arr) {
            shuffle(arr, 0, arr.length - 1);
        }

        public static <T> void shuffle(T[] arr) {
            shuffle(arr, 0, arr.length - 1);
        }

        public static void shuffle(int[] arr, int from, int to) {
            int n = to - from + 1;
            for (int i = 1; i < n; i++) {
                int j = random.nextInt(i);
                swap(arr, i + from, j + from);
            }
        }

        public static <T> void shuffle(T[] arr, int from, int to) {
            int n = to - from + 1;
            Random random = new Random();
            for (int i = 1; i < n; i++) {
                int j = random.nextInt(i);
                swap(arr, i + from, j + from);
            }
        }

        static void swap(int[] arr, int x, int y) {
            int tmp = arr[x];
            arr[x] = arr[y];
            arr[y] = tmp;
        }

        static <T> void swap(T[] arr, int x, int y) {
            T tmp = arr[x];
            arr[x] = arr[y];
            arr[y] = tmp;
        }

        public static <T> void printArray(T[] arr, String message) {
            printArray(arr, 0, arr.length - 1, message);
        }

        public static <T> void printArray(T[] arr, int from, int to, String message) {
            System.out.print(message);
            for (int i = from; i <= to; i++) {
                System.out.print(" " + arr[i]);
            }
            System.out.println();
        }
    }

    /**
     * Timer class for roughly calculating running time of programs
     *
     * @author rbk
     * Usage:  Timer timer = new Timer();
     * timer.start();
     * timer.end();
     * System.out.println(timer);  // output statistics
     */

    public static class Timer {
        long startTime, endTime, elapsedTime, memAvailable, memUsed;
        boolean ready;

        public Timer() {
            startTime = System.currentTimeMillis();
            ready = false;
        }

        public void start() {
            startTime = System.currentTimeMillis();
            ready = false;
        }

        public Timer end() {
            endTime = System.currentTimeMillis();
            elapsedTime = endTime - startTime;
            memAvailable = Runtime.getRuntime().totalMemory();
            memUsed = memAvailable - Runtime.getRuntime().freeMemory();
            ready = true;
            return this;
        }

        public long duration() {
            if (!ready) {
                end();
            }
            return elapsedTime;
        }

        public long memory() {
            if (!ready) {
                end();
            }
            return memUsed;
        }

        public void scale(int num) {
            elapsedTime /= num;
        }

        public String toString() {
            if (!ready) {
                end();
            }
            return "Time: " + elapsedTime + " msec.\n" + "Memory: " + (memUsed / 1048576) + " MB / " + (memAvailable / 1048576) + " MB.";
        }
    }

}
