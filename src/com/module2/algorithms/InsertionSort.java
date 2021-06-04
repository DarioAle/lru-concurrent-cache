package com.module2.algorithms;

import java.util.Arrays;

public class InsertionSort {
    public static void insertionSort(int[] input) {
        for (int i = 1; i < input.length; i++) {
            int key = input[i];
            int j = i - 1;
            while (j >= 0 && input[j] > key) {
                input[j + 1] = input[j];
                j = j - 1;
            }
            input[j + 1] = key;
        }
    }

    public static void main(String ... a) {
        int[] arr = { 5, 1, 6, 2, 3, 4 };
        InsertionSort.insertionSort(arr);
        System.out.println(Arrays.toString(arr));
    }
}
