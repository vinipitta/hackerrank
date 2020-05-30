package net.viniciusaraujo.hackerhank;

import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LeftRotation {

    public static void main(String[] args) {
        test(new int[] {1, 2, 3, 4, 5}, 3);
    }

    static int[] rotLeft(int[] arrayToRotate, int rotations) {
        final int arrayLength =  arrayToRotate.length;
        int[] rotatedArray = new int[arrayLength];

        if (rotations == arrayLength) {
            rotatedArray = arrayToRotate;
        } else {
            final Function<Integer, Integer> numberToRotateIndex = (index) -> index + (arrayLength - rotations);
            final Function<Integer, Integer> numberToShiftLeftIndex = (index) -> index - rotations; 

            for (int i = 0; i < arrayLength; i ++) {
                final int rotatedArrayCalculatedIndex = (i < rotations) 
                    ? numberToRotateIndex.apply(i)
                    : numberToShiftLeftIndex.apply(i);
                
                rotatedArray[rotatedArrayCalculatedIndex] = arrayToRotate[i];
            }
        }

        return rotatedArray;
    }

    static void test(int[] arr, int rotations) {
        Function<int[], String> arrayPrinter = 
            (arrayToPrint) ->  IntStream.of(arrayToPrint)
                        .mapToObj(n -> String.format("%d", n))
                        .collect(Collectors.joining(" "));

        System.out.printf("[%s] -> [%s] %n", 
            arrayPrinter.apply(arr), 
            arrayPrinter.apply( rotLeft(arr, rotations) ));
    }
}