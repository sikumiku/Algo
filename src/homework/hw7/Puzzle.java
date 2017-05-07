package homework.hw7;

import java.util.*;

/**
 * Kodut66 7
 * Kasutatud kirjandus:
 * http://stackoverflow.com/questions/599161/best-way-to-convert-an-arraylist-to-a-string
 * http://stackoverflow.com/questions/4240080/generating-all-permutations-of-a-given-string
 * https://www.tutorialspoint.com/java/java_hashset_class.htm
 * http://stackoverflow.com/questions/2041778/how-to-initialize-hashset-values-by-construction
 * http://stackoverflow.com/questions/5238491/check-if-string-contains-only-letters
 * http://beginnersbook.com/2013/12/java-string-indexof-method-example/
 * http://stackoverflow.com/questions/4861803/how-do-i-make-java-ignore-the-number-of-spaces-in-a-string-when-splitting
 */

public class Puzzle {

    public static Set<String> numberhashset = new HashSet<>();
    public static ArrayList<String> puzzlelist = new ArrayList<>();
    public static int solutioncounter = 0;

    /**
     * Solve the word puzzle.
     *
     * @param args three words (addend1 addend2 sum)
     */

    public static void main(String[] args) {
        resetGlobalVariables();

        //for console input uncomment following line:
        //readUserInput();

        //for testing purposes uncomment following line:
        //findSolutions(new String[]{"SEND", "MORE", "MONEY"});

        //for running PuzzleTest uncomment following line:
        findSolutions(args);
    }

    /**
     * Main method to find solutions for given String array.
     *
     * @param sarray array that ideally contains 3 uppercase words
     */
    public static void findSolutions(String[] sarray) {
        isValidPuzzle(sarray);
        String puzzleString = convertToSingleString(sarray); // SEND MORE MONEY -> SENDMOREMONEY
        StringBuilder puzzleinput = convertArrayIntoReadableInput(sarray);

        System.out.println("Calculating possible solutions for puzzle: " + puzzleinput);

        ArrayList<String> letters = createUniqueCharList(puzzleString); //S, E, N, D, M, O, R, Y
        if (!(isUsableCharList(letters))) {
            throw new RuntimeException("Too many letters to solve the puzzle.");
        }
        createNumberVariations("0123456789", "", letters.size()); //numberhashset muutujas on nyyd k6ikv6imalikud numbrikombinatsioonid
        for (String s : numberhashset) { //iga 8 kohalise numbri kohta
            puzzlelist = addNumbersforLetters(s.toCharArray(), letters); //S,E,N,D,M,O,R,Y -> S,E,N,D,M,O,R,Y,5,3,9,1,2,0,6,4
            generateAnswersforPuzzle(puzzlelist, puzzleString, sarray); //prindib v2lja vastuse, kui seda on
        }
        if (solutioncounter == 0) {
            System.out.println("No possible solutions for these words.");
        }

        System.out.println("Number of possible answers: " + solutioncounter);
    }

    /**
     * Reads input from console, solves input puzzle and lets user solve another puzzle.
     */
    public static void readUserInput() {
        System.out.println("Please enter 3 words in this format 'FIRSTWORD SECONDWORD ANSWERWORD'.");
        Scanner inputreader = new Scanner(System.in);
        String input = inputreader.nextLine();

        if (input.trim().length() == 0 || input == null) {
            throw new RuntimeException("There are no elements to solve puzzle with.");
        }

        String[] sarray = input.split(" +");

        findSolutions(sarray);
        resetGlobalVariables();
        readUserInput();
    }

    /**
     * Shows readable input for an input array
     *
     * @param sarray array with 3 uppercase words
     * @return StringBuilder that contains 3 uppercase in one continuous String with spaces inbetween
     */
    public static StringBuilder convertArrayIntoReadableInput(String[] sarray) {
        StringBuilder input = new StringBuilder();
        for (String e : sarray) {
            input.append(e);
            input.append(" ");
        }
        return input;
    }

    /**
     * Resets global variables
     */
    public static void resetGlobalVariables() {
        solutioncounter = 0;
        numberhashset.clear();
        puzzlelist.clear();
    }

    /**
     * Converts array to one long String.
     *
     * @param strarray array with 3 uppercase words
     * @return one long String made of 3 input Strings
     */
    public static String convertToSingleString(String[] strarray) {
        String singleString = String.join("", strarray); //Loo yks suur string SENDMOREMONEY
        return singleString;
    }

    /**
     * Find all unique letters within given string and add them to String type ArrayList
     *
     * @param inputstring one long string created by convertToSingleString() method
     * @return ArrayList consisting of a String of unique letters
     */
    public static ArrayList<String> createUniqueCharList(String inputstring) {
        ArrayList<String> algneList = new ArrayList<>();
        char[] characters = inputstring.toCharArray(); //Loo t2htede nimekiri S, E, N, D, M, O, R, E, M, O, N, E, Y
        for (char c : characters) {
            if (!algneList.contains(Character.toString(c))) {
                algneList.add(Character.toString(c)); //Loo unikaalsete t2htede nimekiri S, E, N, D, M, O, R, Y
            }
        }
        return algneList;
    }

    /**
     * Check whether the unique character count is not over 10 which is the maximum amount of individual numbers
     *
     * @param inputlist String type ArrayList that contains all unique letters in the puzzle
     */
    public static boolean isUsableCharList(ArrayList<String> inputlist) {
        if (inputlist.size() > 10) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Find all possible variations of unique numbers with as many numbers as there are unique letters using
     * recursive call.
     *
     * @param numbers usable numbers
     * @param temp creatable string
     * @param numberOfLetters number of different letters in given words
     */
    public static void createNumberVariations(String numbers, String temp, Integer numberOfLetters) {
        if (numbers.equals("")) {
            numberhashset.add(temp.substring(0, numberOfLetters));
        }
        for (int i = 0; i < numbers.length(); i++) {
            char number = numbers.charAt(i);
            if (numbers.indexOf(number, i + 1) != -1) {
                continue;
            }
            createNumberVariations(numbers.substring(0, i) + numbers.substring(i + 1), temp + number, numberOfLetters);
        }
    }

    /**
     * Check if given inputs are valid for solving.
     *
     * @param stringarray Array of ideally 3 different uppercase Strings(words)
     */
    public static void isValidPuzzle(String[] stringarray) {
        String s = convertToSingleString(stringarray);
        StringBuilder input = convertArrayIntoReadableInput(stringarray);
        char[] chars = s.toCharArray();
        int count = 0;
        for (int i = 0; i < stringarray.length; i++) {
            count++;
        }

        //Kontrolli, kas on sisendeid
        if (count == 0) {
            throw new RuntimeException("There are no elements to solve puzzle with.");
        }

        //Kontrolli, kas sisendid on uppercase t2hed
        for (char c : chars) {
            if (!Character.isLetter(c) || !Character.isUpperCase(c)) {
                throw new RuntimeException(
                        String.format("Given input %s includes other characters besides letters or the letters are lower case.", input)
                );
            }
        }

        //Kontrolli, kas iga sisend ei yleta 18 t2hem2rki
        for (String str : stringarray) {
            if (str.length() > 18) {
                throw new RuntimeException(
                        String.format("Word %s exceeds maximum allowed character count which is 18.", str)
                );
            }
        }

        //Kontrolli, ks sisendeid on 3
        if (count != 3) {
            throw new RuntimeException(
                    String.format("You need 3 words to solve puzzle, given input included only %s.", count)
            );
        }

    }

    /**
     * Add random set of numbers in same order to ArrayList of unique letters in order to match them later.
     *
     * @param numbers usable numbers
     * @param letters unique letters
     * @return ArrayList consisting of unique letters + usable numbers
     */
    public static ArrayList<String> addNumbersforLetters(char[] numbers, ArrayList<String> letters) {
        ArrayList<String> algnelist = (ArrayList<String>) letters.clone();
        int numbercounter = 0;
        for (int i = 0; i < letters.size(); i++) {
            algnelist.add(Character.toString(numbers[numbercounter]));
            numbercounter++;
        }
        return algnelist;

    }

    /**
     * Finds matching numbers for the letters in the 3 words and prints answers if there are any.
     *
     * @param alist list of unique letters and matching numbers
     * @param sinput 3 input words combined into one string
     * @param puzzle array of 3 words
     */
    public static void generateAnswersforPuzzle(ArrayList<String> alist, String sinput, String[] puzzle) {
        StringBuilder firstelement = new StringBuilder();
        StringBuilder secondelement = new StringBuilder();
        StringBuilder sum = new StringBuilder();

        char[] chars = sinput.toCharArray();
        int size1 = puzzle[0].length();
        int size2 = puzzle[1].length();
        int totalsize = sinput.length();
        //3 erineva stringi iga t2he puhul leitakse, mis on vastav number t2hele ja lisatakse vastavale StringBuilder muutujale
        //N2ide: SENDi teine t2ht on E ja asub indeksil 1, indeksile liidetakse kogu 3 stringi pikkus ja leitakse vastav number
        for (int i = 0; i < size1; i++) {
            firstelement.append(alist.get(alist.indexOf(Character.toString(chars[i])) + alist.size() / 2));
        }
        for (int i = size1; i < size1 + size2; i++) {
            secondelement.append(alist.get(alist.indexOf(Character.toString(chars[i])) + alist.size() / 2));
        }
        for (int i = size1 + size2; i < totalsize; i++) {
            sum.append(alist.get(alist.indexOf(Character.toString(chars[i])) + alist.size() / 2));
        }
        //kontroll, kas esimene number Stringis on 0 ning kui ei ole, siis kas esimene liige + teine liige v6rdub summaga
        //eduka kontrolli puhul prinditakse tulemus kujul "number1 + number2 = summa". Lahendusi loetakse kokku.
        if (!(firstelement.toString().substring(0, 1).equals("0") || secondelement.toString().substring(0, 1).equals("0") || sum.toString().substring(0, 1).equals("0"))) {
            if ((Long.parseLong(firstelement.toString()) + Long.parseLong(secondelement.toString()) == Long.parseLong(sum.toString()))) {
                System.out.println(firstelement + " + " + secondelement + " = " + sum);
                solutioncounter++;
            }
        }
    }


}

