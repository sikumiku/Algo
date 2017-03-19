package homework.hw4;

import java.util.*;

/** This class represents fractions of form n/d where n and d are long integer
 * numbers. Basic operations and arithmetics for fractions are provided.
 * Kasutatud kirjandus:
 * http://stackoverflow.com/questions/13673600/how-to-write-a-simple-java-program-that-finds-the-greatest-common-divisor-betwee
 * https://www.mkyong.com/java/java-how-to-overrides-equals-and-hashcode/
 *
 */
public class Lfraction implements Comparable<Lfraction> {

   /** Main method. Different tests. */
   public static void main (String[] param) {
      Lfraction murd1 = new Lfraction(1, 3);
      System.out.println(murd1);

      long t = getGCD(9, 12);
      System.out.println(t);

      String murd2 = murd1.toString();
      System.out.println(murd2);

      Lfraction murd3 = new Lfraction(1, 2);
      System.out.println(murd1.equals(murd3));

      int hash1 = murd1.hashCode();
      System.out.println(hash1);

      System.out.println(murd1.plus(murd3));
      System.out.println(murd1.times(murd3));
      System.out.println(murd1.minus(murd3));
      System.out.println(murd1.divideBy(murd3));
       System.out.println(murd1.compareTo(murd3));

    Lfraction converted = toLfraction(1.45, 2);
       System.out.println(converted);
       System.out.println(valueOf("6/8"));

   }



    private long numerator;
   private long denominator;

   /** Constructor.
    * @param a numerator
    * @param b denominator > 0
    */

   public Lfraction (long a, long b) {
      numerator = a;
      denominator = b;

      if (denominator != 0) {
         if (numerator < 0) {
            numerator*= -1;
         }
      } else throw new RuntimeException("Denumerator cannot be zero!");

      cancelOut();
   }

   /** Public method to calculate largest common denominator.
    * @return denominator
    */

    public static long getGCD(long a, long b) {
        while(a!=0 && b!=0) // until either one of them is 0
        {
            long c = b;
            b = a%b;
            a = c;
        }
        return a+b;
    }

    private void cancelOut() {
        long divider = getGCD(Math.abs(numerator), Math.abs(denominator));

        numerator = numerator / divider;
        denominator = denominator / divider;
    }

   /** Public method to access the numerator field.
    * @return numerator
    */
   public long getNumerator() {
      return numerator;
   }

   /** Public method to access the denominator field.
    * @return denominator
    */
   public long getDenominator() {
      return denominator;
   }

   /** Conversion to string.
    * @return string representation of the fraction
    */
   @Override
   public String toString() {
      return Long.toString(numerator) + "/" + Long.toString(denominator);
   }

   /** Equality test.
    * @param m second fraction
    * @return true if fractions this and m are equal
    */
   @Override
   public boolean equals (Object m) {
      Lfraction comparableLF = (Lfraction)m;

      if ((numerator == comparableLF.numerator) && (denominator == comparableLF.denominator)) {
         return true;
      } else {
         return false;
      }
   }

   /** Hashcode has to be equal for equal fractions.
    * @return hashcode
    */
   @Override
   public int hashCode() {
      return Objects.hash(numerator, denominator);
   }

   /** Sum of fractions.
    * @param m second addend
    * @return this+m
    */
   public Lfraction plus (Lfraction m) {

      long yhinenimetaja = denominator *m.denominator;
      long yhinetegur = getGCD(denominator, m.denominator);
      long yhinelugeja = (numerator*(denominator /yhinetegur))+(m.numerator*(m.denominator /yhinetegur));

      Lfraction plusLF = new Lfraction(yhinelugeja, yhinenimetaja);
      plusLF.cancelOut();
      return plusLF;
   }

   /** Multiplication of fractions.
    * @param m second factor
    * @return this*m
    */
   public Lfraction times (Lfraction m) {

      long yhinenimetaja = denominator *m.denominator;
      long yhinelugeja = numerator*m.numerator;

      Lfraction timesLF = new Lfraction(yhinelugeja, yhinenimetaja);
      timesLF.cancelOut();
      return timesLF;
   }

   /** Inverse of the fraction. n/d becomes d/n.
    * @return inverse of this fraction: 1/this
    */
   public Lfraction inverse() {
      long newnumerator = denominator;
      long newdenumerator = numerator;

      Lfraction inverseLF = new Lfraction(newnumerator, newdenumerator);

      return inverseLF;
   }

   /** Opposite of the fraction. n/d becomes -n/d.
    * @return opposite of this fraction: -this
    */
   public Lfraction opposite() {
      long oppositenumerator = numerator;

      Lfraction oppositeLF = new Lfraction(oppositenumerator, denominator);

      return oppositeLF;
   }

   /** Difference of fractions.
    * @param m subtrahend
    * @return this-m
    */
   public Lfraction minus (Lfraction m) {
       long yhinenimetaja = denominator *m.denominator;
       long yhinetegur = getGCD(denominator, m.denominator);
       long yhinelugeja = (numerator*(denominator /yhinetegur))-(m.numerator*(m.denominator /yhinetegur));

       Lfraction minusLF = new Lfraction(yhinelugeja, yhinenimetaja);
       minusLF.cancelOut();

       return minusLF;

   }

   /** Quotient of fractions.
    * @param m divisor
    * @return this/m
    */
   public Lfraction divideBy (Lfraction m) {

       long dividingnumerator = m.denominator;
       long dividingdenumerator = m.numerator;
       long yhinenimetaja = denominator *dividingdenumerator;
       long yhinelugeja = numerator*dividingnumerator;

       Lfraction divideLF = new Lfraction(yhinelugeja, yhinenimetaja);
       divideLF.cancelOut();

       return divideLF;
   }

   /** Comparision of fractions.
    * @param m second fraction
    * @return -1 if this < m; 0 if this==m; 1 if this > m
    */
   @Override
   public int compareTo (Lfraction m) {

       Lfraction comparingLF = this.minus(m);

       int result = 2;

       if (comparingLF.numerator < 0) {
           result = -1;
       } else if (comparingLF.numerator == 0) {
           result = 0;
       } else if (comparingLF.numerator > 0) {
           result = 1;
       } else if (result == 2) {
           throw new RuntimeException(String.format(
                   "Something went wrong with deriving fraction '%s'.", comparingLF));
       }
       return result;
   }

   /** Clone of the fraction.
    * @return new fraction equal to this
    */
   @Override
   public Object clone() throws CloneNotSupportedException {
      long clonednumerator = numerator;
      long cloneddenumerator = denominator;

      Lfraction cloneLF = new Lfraction(clonednumerator, cloneddenumerator);

      return cloneLF;
   }

   /** Integer part of the (improper) fraction. 
    * @return integer part of this fraction
    */
   public long integerPart() {

      long c = numerator% denominator;

      return c;
   }

   /** Extract fraction part of the (improper) fraction
    * (a proper fraction without the integer part).
    * @return fraction part of this fraction
    */
   public Lfraction fractionPart() {

        return new Lfraction(numerator, denominator - integerPart()*numerator);
   }

   /** Approximate value of the fraction.
    * @return numeric value of this fraction
    */
   public double toDouble() {
       double doubleLF = (double)numerator/(double) denominator;

       return doubleLF;
   }

   /** Double value f presented as a fraction with denominator d > 0.
    * @param f real number
    * @param d positive denominator for the result
    * @return f as an approximate fraction of form n/d
    */
   public static Lfraction toLfraction (double f, long d) {

       return new Lfraction(Math.round((double)d*f), d);
   }

   /** Conversion from string to the fraction. Accepts strings of form
    * that is defined by the toString method.
    * @param s string form (as produced by toString) of the fraction
    * @return fraction represented by s
    */
   public static Lfraction valueOf (String s) {

       if (s == null) {
           throw new RuntimeException(String.format("String '%s' is invalid", s));
       }

       String[] fractionelements = s.split("/");

       if (fractionelements.length != 2) {
           throw new RuntimeException(String.format("Please follow the format numerator/denominator, current string '%s' is not valid.", fractionelements));
       }
       if (fractionelements[0].matches("[^0-9]")) {
           throw new RuntimeException(String.format("Numerator '%s' is not in valid number format.", fractionelements[0]));
       } else if (fractionelements[1].matches("[^0-9]")) {
           throw new RuntimeException(String.format("Denominator '%s' is not in valid number format.", fractionelements[1]));
       }

       return new Lfraction(Long.parseLong(fractionelements[0]), Long.parseLong(fractionelements[1]));

   }
}

