package net.viniciusaraujo.hackerrank;

public class HourglassSumSolution {
    
    public static void main(String[] args) {
        System.out.println("Cenário1 : " + 
            hourglassSum(
                new int[][] {
                    new int[] { 1, 1, 1, 0, 0, 0 },
                    new int[] { 0, 1, 0, 0, 0, 0 },
                    new int[] { 1, 1, 1, 0, 0, 0 },
                    new int[] { 0, 0, 2, 4, 4, 0 },
                    new int[] { 0, 0, 0, 2, 0, 0 },
                    new int[] { 0, 0, 1, 2, 4, 0 }
                }));
    }

    // Complete the hourglassSum function below.
    static int hourglassSum(int[][] arr) {

        return new HeaviestHourglassFinder(arr).getHeaviest();
    }

    static class HeaviestHourglassFinder {
        private static final int HOURGLASS_WIDTH = 3;
        private static final int HOURGLASS_HEIGHT = 3;
        private static final int FIRST_IN_ROW = 0;

        private final int[][] hourglassMap;
        private final int maxStartX;
        private final int maxStartY;
        
        private int currentHourglassX = 0;
        private int currentHourglassY = 0;

        private Integer calculatedHeaviestHourglass = null;

        HeaviestHourglassFinder(int[][] hourglassMap) {
            this.hourglassMap = hourglassMap;
            this.maxStartY = this.hourglassMap.length - HOURGLASS_HEIGHT;
            this.maxStartX = this.hourglassMap[0].length - HOURGLASS_WIDTH;
        }

        public int getHeaviest() {
            if (this.calculatedHeaviestHourglass == null) {
                this.calculatedHeaviestHourglass = this.findHeaviest();
            }

            return this.calculatedHeaviestHourglass;
        }


        private int findHeaviest() {
            
            int heaviest = Integer.MIN_VALUE;

            while (this.hasNextHourglass()) {
                heaviest = Math.max(heaviest, this.getCurrentHourGlassSum());
                this.next();
            }

            return heaviest;
        }

        private boolean hasNextHourglass() {
            return this.currentHourglassX <= this.maxStartX 
                && this.currentHourglassY <= this.maxStartY; 
        }

        private void next() {
            if (this.isLastHourglassInTheRow()) {
                this.moveVertically();
            } else {
                this.moveHorizontally();                
            }
        }

        private void moveHorizontally() {
            this.currentHourglassX++;
        }

        private void moveVertically() {
            this.currentHourglassX = FIRST_IN_ROW;
            this.currentHourglassY++;
        }

        private boolean isLastHourglassInTheRow() {
            return this.currentHourglassX == this.maxStartX;
        }

        private int getCurrentHourGlassSum() {
            final int x = this.currentHourglassX;
            final int y = this.currentHourglassY;
            return this.getHourglassTopSum(x, y) 
                + this.getHourglassMiddle(x, y) 
                + this.getHourglassBottomSum(x, y);
        }

        private int value(int x, int y) {
            return this.hourglassMap[y][x];
        }

        private int getHourglassTopSum(int x, int y) {
            return this.value(x, y) + this.value(x + 1, y) + this.value(x + 2, y);
        }

        private int getHourglassMiddle(int x, int y) {
            return this.value(x + 1 , y + 1);
        }

        private int getHourglassBottomSum(int x, int y) {
            final int bottom = y + 2;
            return this.value(x, bottom) + this.value(x + 1, bottom) + this.value(x + 2, bottom);
        }
    }

}