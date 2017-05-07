package homework.hw7;

import java.util.*;

/**Kodut66 7
 * Kasutatud kirjandus:
 * http://stackoverflow.com/questions/599161/best-way-to-convert-an-arraylist-to-a-string
 * http://stackoverflow.com/questions/4240080/generating-all-permutations-of-a-given-string
 * http://stackoverflow.com/questions/2041778/how-to-initialize-hashset-values-by-construction
 */

public class Puzzle {


   public static Set<String> set = new HashSet<>();
    public static ArrayList<String> puzzlelist = new ArrayList<>();
   public static int counter = 0;

   /** Solve the word puzzle.
    * @param args three words (addend1 addend2 sum)
    */

   public static void main (String[] args) {

      String[] u = new String[]{"SEND", "MORE", "MONEY"};
      String output = convertToSingleString(new String[]{"YKS", "KAKS", "KOLM"});
      System.out.println(u[1]);
      System.out.println(output);

      findSolutions(new String[]{"SEND", "MORE", "MONEY"});
   }

   public static void findSolutions (String[] sarray) {
//      isValidPuzzle(sarray);
      String puzzleString = convertToSingleString(sarray); // SEND MORE MONEY -> SENDMOREMONEY
      ArrayList<String> letters = createUniqueCharList(puzzleString); //S, E, N, D, M, O, R, Y
      if (!(isUsableCharList(letters))) {
         throw new RuntimeException("Too many letters to solve the puzzle.");
      }
      createNumberVariations("0123456789", "", letters.size()); //set now contains all possible 8 digit number combos
      for (String s : set) { //iga 8 kohalise numbri kohta
          replaceLettersWithNumbers(s.toCharArray(), letters); //S, E, N, D, M, O, R, Y -> 5,3,9,1,2,0,6,4
          generateAnswersforPuzzle(puzzleString, sarray);
      }


   }

   public static String convertToSingleString(String[] strArray) {
      String singleString = String.join("", strArray); //Loo yks suur string SENDMOREMONEY
      return singleString;
   }

   public static ArrayList<String> createUniqueCharList(String inputstring) {
      ArrayList<String> algneList = new ArrayList<>();
      char[] characters = inputstring.toCharArray(); //Loo t2htede nimekiri S, E, N, D, M, O, R, E, M, O, N, E, Y
      for (char c : characters) {
         if (!algneList.contains(Character.toString(c))) {
            algneList.add(Character.toString(c));
         }
      }
      return algneList;
   }

   public static boolean isUsableCharList(ArrayList<String> inputlist) {
      if (inputlist.size() > 10) {
         return false;
      } else {
         return true;
      }
   }

   public static void createNumberVariations (String numbers, String temp, Integer numberOfLetters) {
      if (numbers.equals("")) {
         set.add(temp.substring(0, numberOfLetters));
      }
      for (int i = 0; i < numbers.length(); i++) {
         char number = numbers.charAt(i);
         if (numbers.indexOf(number, i + 1) != -1) {
            continue;
         }
         createNumberVariations(numbers.substring(0, i) + numbers.substring(i + 1), temp + number, numberOfLetters);
      }
   }

   public static void replaceLettersWithNumbers(char[] numbers, ArrayList<String> letters) {
       int numbercounter = 0;
       for (int i = 0; i < letters.size(); i++) {
           letters.set(i, Character.toString(numbers[numbercounter]));
           numbercounter++;
       }
       puzzlelist = letters;
   }

   public static void generateAnswersforPuzzle(String sinput, String[] puzzle) {
       StringBuilder firstelement = new StringBuilder();
       StringBuilder secondelement = new StringBuilder();
       StringBuilder sum = new StringBuilder();

       char[] chars = sinput.toCharArray();
       int size1 = puzzle[0].length();
       int size2 = puzzle[1].length();
       int totalsize = sinput.length();
       for (int i = 0; i < size1; i++) {
           firstelement.append(puzzlelist.get(puzzlelist.indexOf(Character.toString(chars[i]))));
       }
       for (int i = size1; i < size1 + size2; i++) {
           secondelement.append(puzzlelist.get(puzzlelist.indexOf(Character.toString(chars[i]))));
       }
       for (int i = size2; i < totalsize; i++) {
           sum.append(puzzlelist.get(puzzlelist.indexOf(Character.toString(chars[i]))));
       }
       if (!(firstelement.toString().substring(0,1).equals("0") || secondelement.toString().substring(0,1).equals("0") || sum.toString().substring(0,1).equals("0"))) {
           if ((Long.parseLong(firstelement.toString()) + Long.parseLong(secondelement.toString()) == Long.parseLong(sum.toString()))) {
               System.out.println(firstelement + " + " + secondelement + " = " + sum);
               counter++;
           }
       }
   }





}

