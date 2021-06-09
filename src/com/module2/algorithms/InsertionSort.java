package com.module2.algorithms;

import java.util.Arrays;

public class InsertionSort {
    public static <T extends Comparable<T>> void insertionSort(T[] input) {
        for (int i = 1; i < input.length; i++) {
            T key = input[i];
            int j = i - 1;
            while (j >= 0 && input[j].compareTo(key) > 0) {
                System.out.println("Swap needed");
                input[j + 1] = input[j];
                j = j - 1;
            }
            input[j + 1] = key;
        }
    }

    public static void main(String ... a) {
        String[] arr = {"France" , "Germany", "Denmark", "England", "Croatia", "Belgium" };
        InsertionSort.insertionSort(arr);
        System.out.println(Arrays.toString(arr));
    }
}
