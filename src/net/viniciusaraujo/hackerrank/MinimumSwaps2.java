package net.viniciusaraujo.hackerhank;

public class MinimumSwaps2 {
    
    public static void main(String[] args) {
        testar(new int[] { 7, 1, 3, 2, 4, 5, 6 }, 5);
        testar(new int[] { 4, 3, 1, 2 }, 3);
        testar(new int[] { 2, 3, 4, 1, 5 }, 3);
        testar(new int[] { 1, 3, 5, 2, 4, 6, 7 }, 3);
    }

    static void testar(int[] desordenado, int valorEsperado) {
        int valorEncontrado = minimumSwaps(desordenado);
        System.out.println(
            String.format(
                "[%s] Valor esperado: %d, valor encontrado: %d",
                (valorEncontrado == valorEsperado ? "PASSOU" : "FALHOU"),
                valorEsperado,
                valorEncontrado));
    }

    static int minimumSwaps(int[] arr) {
       return new MininumSwapsCalculator(arr).calculate();
    }

    static class MininumSwapsCalculator {
        private int[] arrayToOrder;
        private int currentIndex = 0;
        private int swaps = 0;

        public MininumSwapsCalculator(int[] arrayToOrder) {
            this.arrayToOrder = arrayToOrder;
        }

        public int calculate() {

            while (this.notFinished()) {
                if (this.isCurrentPositionOutOfOrder()) {
                    this.swap();
                    this.swaps++;
                } else {
                    this.next();
                }
            }

            return swaps;
        }

        private boolean isCurrentPositionOutOfOrder() {
            return this.arrayToOrder[this.currentIndex] != (this.currentIndex + 1);
        }

        private void swap() {
            final int currentValue = this.arrayToOrder[this.currentIndex];
            final int targetIndex = currentValue - 1;

            this.arrayToOrder[this.currentIndex] = this.arrayToOrder[targetIndex];
            this.arrayToOrder[targetIndex] = currentValue;
        }

        private void next() {
            this.currentIndex++;
        }

        private boolean notFinished() {
            return this.currentIndex < this.arrayToOrder.length;
        }
    }
}