package net.viniciusaraujo.hackerhank;

public class JumpingOnCloudsSolution {
    
    // Complete the jumpingOnClouds function below.
    static int jumpingOnClouds(int[] c) {
        return new ShortestPathCalculator(c).calculateShortestPath();
    }

    public static void main(String[] args) {
        System.out.println("Cenário 1: " + jumpingOnClouds(new int[] { 0, 0, 1, 0, 0, 1, 0 }));
        System.out.println("Cenário 2: " + jumpingOnClouds(new int[] { 0, 0, 0, 0, 1, 0 }));
    }

    static class ShortestPathCalculator {
        public static final int SAFE_CLOUD = 0;
        private static final int BIG_JUMP = 2;
        private static final int REGULAR_JUMP = 1;
        private final int[] path;
        private final int lastPosition;
        
        private int currentPosition = 0;
        private int jumps = 0;

        public ShortestPathCalculator(int[] path) {
            this.path = path;
            this.lastPosition = (this.path.length - 1);
        }

        public int calculateShortestPath() {
            while (this.isPathNotFinished()) {
                this.currentPosition += isPossibleToJumpBig() ? BIG_JUMP : REGULAR_JUMP;
                this.jumps++;
            }

            return this.jumps;
        }

        private boolean isPossibleToJumpBig() {
            final int bigJumpPosition = this.currentPosition + BIG_JUMP;
            return bigJumpPosition < this.path.length && this.path[bigJumpPosition] == SAFE_CLOUD;
        }

        private boolean isPathNotFinished() {
            return this.currentPosition < this.lastPosition;
        }
    } 
}