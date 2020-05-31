package net.viniciusaraujo.hackerhank;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.Stack;
import java.util.function.Consumer;

public class ArrayManipulation2 {
    public static void main(String[] args) throws IOException {
        //expected 2490686975
        final String path = "D:\\Projetos\\hackerrank\\src\\input\\";

        Scanner scanner = new Scanner(
            new File(path + "arraymanipulation-testcase13.txt"));

        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(path + "result.txt")));

        String[] nm = scanner.nextLine().split(" ");

        int n = Integer.parseInt(nm[0]);

        int m = Integer.parseInt(nm[1]);

        int[][] queries = new int[m][3];

        for (int i = 0; i < m; i++) {
            String[] queriesRowItems = scanner.nextLine().split(" ");
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            for (int j = 0; j < 3; j++) {
                int queriesItem = Integer.parseInt(queriesRowItems[j]);
                queries[i][j] = queriesItem;
            }
        }
        try {
            long result = arrayManipulation(n, queries);
            bufferedWriter.write(String.valueOf(result));
            bufferedWriter.newLine();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        bufferedWriter.close();

        scanner.close();
    }

    public static void main2(String[] args) {
        System.out.println(
            "Resultado 1: " + 
            arrayManipulation(5, new int[][] {
                new int[] { 1, 2, 100 },
                new int[] { 2, 5, 100 },
                new int[] { 3, 4, 100 }
            }));

        System.out.println(
            "Resultado 2: " +
            arrayManipulation(10, new int[][] {
                { 1, 5, 3 }, 
                { 4, 8, 7 },
                { 6, 9, 1}
            })
        );

        System.out.println(
            "Resultado 3: " +
            arrayManipulation(10, new int[][] {
                { 2, 6, 8 },
                { 3, 5, 7 },
                { 1, 8, 1 },
                { 5, 9, 15}
            })
        );

    }

    static long arrayManipulation(int n, int[][] queries) {
       

        return new MaxOperationsValueCalculator(n, queries).calculate();
    }

    static class Range {
        private final int start;
        private final int end;

        public Range(int start, int end) {
            this.start = start;
            this.end = end;
        }

        public int start() {
            return this.start;
        }

        public int end() {
            return this.end;
        }
    }

    static class RangeSplitter {
        private final Range range;

        public RangeSplitter(Range range) {
            this.range = range;
        }

        public List<Range> splitFromOperationRange(Range operationRange) {
            final ArrayList<Range> subranges = new ArrayList<>();
            final Consumer<Range> addSubRange = r -> subranges.add(r);
            
            this.leftRangeFromOperation(operationRange).ifPresent(addSubRange);
            this.intersectingRangeFromOperation(operationRange).ifPresent(addSubRange);
            this.rigthRangeFromOperation(operationRange).ifPresent(addSubRange);

            return subranges;
        }

        private Optional<Range> leftRangeFromOperation(Range operationRange) {
            return operationRange.start() > range.start() 
                ? Optional.of(
                    new Range(
                        range.start(),
                        Math.min( range.end(), (operationRange.start() - 1) )  
                    ))
                : Optional.empty();
        }

        private Optional<Range> intersectingRangeFromOperation(Range opRange) {
            final boolean hasIntersection = 
                ( opRange.start() < range.start() && opRange.end() >= range.start() )
                || ( opRange.start() >= range.start()  && opRange.start() <= range.end() );

            return hasIntersection
                ? Optional.of(
                    new Range(
                        Math.max(opRange.start(), range.start()),
                        Math.min(opRange.end(), range.end())
                    ))
                : Optional.empty();
        }

        private Optional<Range> rigthRangeFromOperation(Range operationRange) {
            return operationRange.end() < range.end()
                ? Optional.of(
                    new Range(
                        Math.max( (operationRange.end() + 1), range.start() ),
                        range.end()
                    ))
                : Optional.empty();
        }
    }

    static class MaxOperationsValueCalculator {
        public static final int START_INDEX = 0;
        public static final int END_INDEX = 1;
        public static final int VALUE_INDEX = 2;

        private final int maxOperationRange;
        private final int[][] operations;

        public MaxOperationsValueCalculator(int n, int[][] queries) {
            this.maxOperationRange = n;
            this.operations = queries;
        }

        public long calculate() {
            final int lastOperation = this.operations.length - 1;
            final Range fullRange = this.createFullRange();

            return this.getMaxValueForRangeAtLevel(fullRange, lastOperation);
        }

        private Range createFullRange() {
            return new Range(1, this.maxOperationRange);
        }

        private long getMaxValueForRangeAtLevel(Range range, int operation) {
            long maxValueForRange = 0;
            final Range operationRange = this.getOperationRange(operation);
            final List<Range> splitedRanges = new RangeSplitter(range).splitFromOperationRange(operationRange);
            final boolean hasMoreOperationsToCalc = operation > 0;
            
            for (Range subRange : splitedRanges) {
                final long operationValueToAdd = 
                    this.isRangeInsideOperationRange(subRange, operationRange)
                        ? this.value(operation)
                        : 0 ;
                final long acumulatedValue = hasMoreOperationsToCalc 
                        ? this.getMaxValueForRangeAtLevel(subRange, this.operationNextLevel(operation))
                        : 0 ;
                final long subRangeMaxValue = operationValueToAdd + acumulatedValue;

                maxValueForRange = Math.max(maxValueForRange, subRangeMaxValue);
            }

            return maxValueForRange;
        }

        private boolean isRangeInsideOperationRange(Range range, Range operationRange) {
            return range.start() >= operationRange.start() 
                && range.end() <= operationRange.end();
        }

        private int value(int operation) {
            return this.operations[operation][VALUE_INDEX];
        }

        private int operationNextLevel(int operation) {
            return operation - 1;
        }

        private Range getOperationRange(int operation) {
            final int[] operationData = this.operations[operation];
            return new Range(
                operationData[START_INDEX],
                operationData[END_INDEX]);
        }
    }
}