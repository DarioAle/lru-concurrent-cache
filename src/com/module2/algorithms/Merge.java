package com.module2.algorithms;

import java.util.ArrayList;
import java.util.Arrays;

public class Merge {
    public static <T extends Comparable<T>> void merge(
            T[] a, T[] l, T[] r, int left, int right) {

        System.out.println("Merging from " + left + " to " + right );
        int i = 0, j = 0, k = 0;
        while (i < left && j < right) {
            if (l[i].compareTo(r[j]) <= 0) {
                a[k++] = l[i++];
            }
            else {
                a[k++] = r[j++];
            }
        }
        while (i < left) {
            a[k++] = l[i++];
        }
        while (j < right) {
            a[k++] = r[j++];
        }
    }

    // Main function that sorts arr[l..r] using
    // merge()
    public static  <T extends Comparable<T>> void mergeSort(T[] a, int n) {
        if (n < 2) {
            return;
        }
        int mid = n / 2;
        T[] l = (T[]) new ArrayList<T>(mid).toArray();
        T[] r = (T[]) new ArrayList<T>(n - mid).toArray();

        for (int i = 0; i < mid; i++) {
            l[i] = a[i];
        }
        for (int i = mid; i < n; i++) {
            r[i - mid] = a[i];
        }
        mergeSort(l, mid);
        mergeSort(r, n - mid);

        merge(a, l, r, mid, n - mid);
    }


    public static void main(String ... a) {
        Integer[] arr = { 5, 1, 6, 2, 3, 4 };
        Merge.mergeSort(arr, arr.length);
        System.out.println(Arrays.toString(arr));
    }
}
