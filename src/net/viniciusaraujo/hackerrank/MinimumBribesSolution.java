package net.viniciusaraujo.hackerhank;

public class MinimumBribesSolution {
    
    public static void main(String[] args) {
        System.out.println("Cenário 1 (esperado: 3)");
        minimumBribes(new int[] { 2, 1, 5, 3, 4 });
                            //    1  2  3  4  5
                            //   -1 +1 -2 +1 +1                           

        System.out.println("Cenário 2 (esperado: Too chaotic)");
        minimumBribes(new int[] { 2, 5, 1, 3, 4 });

        System.out.println("Cenário 3 (esperado: Too chaotic)");
        minimumBribes(new int[] { 5, 1, 2, 3, 7, 8, 6, 4 });
        
        System.out.println("Cenário 4 (esperado: 7)");
        minimumBribes(new int[] { 1, 2, 5, 3, 7, 8, 6, 4 });
                                //1  2  3  4  5  6  7  8
                                //1  2  5  3  
                                //0  0 -2 +1 -2 -2 +1 +4
                                //0  0 -2 -1 -3 -5 -4  0
    }

    static void minimumBribes(int[] q) {
        try {
            System.out.println(new MinimumBribesCalculator(q).calculateMinimumBribes());
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }

    static class MinimumBribesCalculator{
        private final int[] finalLine;

        public MinimumBribesCalculator(int[] queue) {
            this.finalLine = queue;
        }

        public int calculateMinimumBribes() {
            int[] currentLine = this.createOriginalLine();
            int bribes = 0;

            for (int i = 0; i < finalLine.length; i++) {
                final int stickerFinalLine = this.finalLine[i];
                final int currentSticker = currentLine[i];

                if (currentLine[i] != stickerFinalLine) {
                    final int nextSticker = currentLine[i + 1];

                    if (nextSticker == stickerFinalLine) {
                        bribes++;
                        currentLine[i + 1] = currentSticker;
                        currentLine[i] = nextSticker;
                    } else if (currentLine[i + 2] == stickerFinalLine) {
                        bribes+= 2;
                        currentLine[i + 2] = nextSticker;
                        currentLine[i + 1] = currentSticker;
                        currentLine[i] = stickerFinalLine;
                    } else {
                        throw this.createImpossibleArrangeException();
                    }
                }
            }

            return bribes;
        }

        private int[] createOriginalLine() {
            final int[] originalQueue = new int[this.finalLine.length];
            
            for (int i = 0; i < originalQueue.length; i++) {
                final int originalPosition = i + 1;
                originalQueue[i] = originalPosition;
            }

            return originalQueue;
        }

        private IllegalStateException createImpossibleArrangeException() {
            throw new IllegalStateException("Too chaotic");
        }
    }

}