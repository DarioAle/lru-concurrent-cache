package com.module2.algorithms;

public class BinarySearch {
    public static <T extends Comparable<T>> int binarySearch(T[] array, T value) {
        int l = 0;
        int r = array.length - 1;

        while (l <= r) {
            int mid = l + (r - l) / 2;

            int cmp = array[mid].compareTo(value);

            if (cmp < 0) {
                l = mid + 1;
            }
            else if (cmp > 0) {
                r = mid - 1;
            }
            else {
                return mid;
            }
        }
        return -1;
    }

    public static <T extends  Comparable<T>> int binarySearch(T[] array, T value, int l, int r) {
        if(l > r)
            return -1;
        int mid = l + (r - l) / 2;

        int cmp = array[mid].compareTo(value);

        if(cmp < 0)
            return binarySearch(array, value, mid + 1, r);

        if(cmp > 0)
            return binarySearch(array, value, l, mid - 1);

        return mid;

    }

    public static void main(String ... a) {
        Integer [] arr = new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 80, 100, 500};

        int r = binarySearch(arr, Integer.valueOf(5000), 0, arr.length - 1);
        System.out.println(r);
    }
}
