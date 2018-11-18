package ixs171130;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Select {
    private static final int THRESHOLD = 17;
    private static Random random = new Random();

    /**
     * Randomized partition
     *
     * @param arr Array to sort
     * @param p   Starting element
     * @param r   Last element
     * @return int, Result of partition
     */
    private static int randomizedPartition(Comparable[] arr, int p, int r) {
        int i = p + random.nextInt(r - p);
        Comparable t;
        t = arr[i];
        arr[i] = arr[r];
        arr[r] = t;
        return partition1(arr, p, r);
    }

    /**
     * Partition version 1
     *
     * @param arr Array to be sorted
     * @param p   Starting element
     * @param r   Beginning element
     * @return int
     */
    private static int partition1(Comparable[] arr, int p, int r) {
        Comparable x = arr[r], t;
        int i = p - 1;
        for (int j = p; j < r; j++) {
            if (arr[j].compareTo(x) <= 0) {
                i++;
                t = arr[j];
                arr[j] = arr[i];
                arr[i] = t;
            }
        }
        t = arr[i + 1];
        arr[i + 1] = arr[r];
        arr[r] = t;
        return i + 1;
    }

    /**
     * Sort the array using Insertion Sort
     *
     * @param arr Array to sort
     * @throws Exception Exception in case incorrect indexes are given
     */
    public static void insertionSort(Comparable[] arr) throws Exception {
        insertionSort(arr, 0, arr.length - 1);
    }

    /**
     * Sort array between start and end range using insertion sort
     *
     * @param arr   Array to sort
     * @param start Start range
     * @param end   End range
     * @throws Exception Exception in case incorrect indexes are given
     */
    public static void insertionSort(Comparable[] arr, int start, int end) throws Exception {
        if (start >= end || start < 0) {
            throw new Exception("Incorrect index given for insertion sort");
        }

        Comparable temp;
        int i, j;
        for (i = start + 1; i <= end; i++) {
            temp = arr[i];
            j = i - 1;
            while (j >= start && temp.compareTo(arr[j]) < 0) {
                arr[j + 1] = arr[j];
                j = j - 1;
            }
            arr[j + 1] = temp;
        }
    }

    public static void main(String[] args) throws Exception {
        int size, k;
        System.out.print("Enter size of array, elements to select, followed by array: ");
        Scanner in = new Scanner(System.in);
        size = in.nextInt();
        k = in.nextInt();

        Integer[] arr = new Integer[size];

        for (int i = 0; i < size; i++) {
            arr[i] = in.nextInt();
        }

        Select s = new Select();
        s.select(arr, k);
        System.out.println(Arrays.toString(arr));
    }

    /**
     * Select k largest elements from array
     *
     * @param arr Input array
     * @param k   Number of elements
     */
    public static void select(Comparable[] arr, int k) throws Exception {
        select(arr, 0, arr.length, k);
    }

    /**
     * Select k largest elements from array
     *
     * @param arr Input array
     * @param p   Start index
     * @param n   Size of array
     * @param k   Number of elements to select
     * @return int
     * @throws Exception In case insertion sort gets wrong index values
     */
    public static int select(Comparable[] arr, int p, int n, int k) throws Exception {
        int q, left, right;
        if (n < THRESHOLD) {
            insertionSort(arr, p, p + n - 1);
            return 0;
        } else {
            q = randomizedPartition(arr, p, p + n - 1);
            left = q - p;
            right = n - left - 1;
            if (right >= k) {
                return select(arr, q + 1, right, k);
            } else if (right + 1 == k) {
                return q;
            } else {
                return select(arr, p, left, k - right - 1);
            }
        }
    }
}
