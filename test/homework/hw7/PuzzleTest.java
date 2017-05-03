package homework.hw7;

import static org.junit.Assert.*;
import org.junit.Test;

/** Testklass.
 * @author jaanus
 */
public class PuzzleTest {

   @Test (timeout=20000)
   public void test1() { 
      Puzzle.main (new String[]{"YKS", "KAKS", "KOLM"}); // 234 solutions
      Puzzle.main (new String[]{"SEND", "MORE", "MONEY"}); // 1 solution
      // Puzzle.main (new String[]{"ABCDEFGHIJAB", "ABCDEFGHIJA", "ACEHJBDFGIAC"});  // 2 solutions
      assertTrue ("There are no tests", true);
   }
}

