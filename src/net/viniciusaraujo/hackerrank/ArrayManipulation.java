package net.viniciusaraujo.hackerrank;

public class ArrayManipulation {
    
    public static void main(String[] args) {
        System.out.println(
            "Resultado: " + 
            arrayManipulation(5, new int[][] {
                new int[] { 1, 2, 100 },
                new int[] { 2, 5, 100 },
                new int[] { 3, 4, 100 }
            }));
    }

    public static final int START_INDEX = 0;
    public static final int END_INDEX = 1;
    public static final int VALUE_INDEX = 2;

    static long arrayManipulation(int n, int[][] queries) {
        final long[] manipulatedArray = new long[n];        
        long max = 0;

        for (int i = 0; i < queries.length; i++) {

            final int[] operation = queries[i];
            final int operationValue = operation[VALUE_INDEX];

            for (int j = operation[START_INDEX]; j <= operation[END_INDEX]; j++) {
                final int actualIndex = j - 1;
                final long positionValue = (manipulatedArray[actualIndex] += operationValue);

                max = Math.max(max, positionValue);
            }
        }   

        return max;
    }
}