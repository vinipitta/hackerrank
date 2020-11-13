package net.viniciusaraujo.hackerrank;

public class RepeatedStringSolution {

    static long repeatedString(String repeatString, long numberOfCharsToConsider) {
        return new LetterCounter(repeatString, numberOfCharsToConsider).count();
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Cenário 1:" + repeatedString("aba", 10));
        System.out.println("Cenário 2:" + repeatedString("aba", 2));
        System.out.println("Cenário 3:" + repeatedString("a", 10000));
    }

    public static class LetterCounter {
        public static final char TARGET_LETTER = 'a';

        private final String repeatString;
        private final long charsToConsider;

        public LetterCounter(String repeatString, long charsToConsider) {
            this.repeatString = repeatString;
            this.charsToConsider = charsToConsider;
        }

        public long count() {
            
            final long ocurrencesInRepeatString = this.countLetterOcurrencesInString(TARGET_LETTER, repeatString);       
            final int lengthRepeatString = repeatString.length();

            if (ocurrencesInRepeatString == 0) {
                
                return 0;
            } else if (this.charsToConsider <= lengthRepeatString) {

                return this.countLetterOcurrencesInString(
                    TARGET_LETTER, 
                    repeatString.substring(0, (int) this.charsToConsider));
            } else {
                
                final long countCompleteRepeatStrings = Math.floorDiv(charsToConsider, lengthRepeatString);
                final int charsNotAccounted = (int) Math.floorMod(charsToConsider, lengthRepeatString);
    
                long totalOcurrencesOfTargetLetter = countCompleteRepeatStrings * ocurrencesInRepeatString;
    
                if (charsNotAccounted > 0) {
                    totalOcurrencesOfTargetLetter += countLetterOcurrencesInString(
                        TARGET_LETTER, repeatString.substring(0, charsNotAccounted));
                } 
    
                return totalOcurrencesOfTargetLetter;
            }
        }

        private long countLetterOcurrencesInString(char letterToCount, String source) {
            int countOfTargetLetter = 0;
    
            for (int i = 0; i < source.length(); i++) {
                final char letter = source.charAt(i);
    
                if (letter == letterToCount) {
                    countOfTargetLetter++;
                }
            }
    
            return countOfTargetLetter;
        }
    }
}