package homework.hw5;

import java.util.*;

/**
 * This class represents Treenode.
 * Kasutatud kirjandus:
 * http://stackoverflow.com/questions/2072222/regular-expression-for-positive-and-a-negative-decimal-value-in-java
 * https://www.tutorialspoint.com/java/util/stringtokenizer_nextelement.htm
 * https://www.tutorialspoint.com/java/java_string_trim.htm
 */

public class Tnode {

    //muutujad
    private String symbol;
    private Tnode firstChild;
    private Tnode nextSibling;

    //konstruktor
    public Tnode(String token) {
        symbol = token;
    }

    //kontrolli kas protsessitav symbol on neg. v6i pos. integer
    private static boolean isInteger(String input) {
        if (input.matches("[+-]?\\d+")) {
            return true;
        } else {
            return false;
        }
    }

    //kontrolli, kas protsessitav symbol on operaator
    private static boolean isOperator(String s) {
        if (s.length() != 1) return false;
        char c = s.charAt(0);
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    //kontrolli, kas stringis yldse on midagi
    private static boolean isStringEmpty(String s) {
        if (s.trim().isEmpty() || s == null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        StringBuffer b = new StringBuffer();
        if (isInteger(symbol)) {
            b.append(symbol);
        } else {
            b.append(symbol);
            b.append("(");
            b.append(firstChild);
            b.append(",");
            b.append(nextSibling);
            b.append(")");
        }
        return b.toString();
    }

    public static Tnode buildFromRPN(String pol) {
        if (isStringEmpty(pol)) {
            throw new RuntimeException("Sisendid puuduvad puu loomiseks. Palun andke sisendiks p66ratud poola kujuga tehe.");
        }

        Tnode root;
        StringTokenizer stringtokens = new StringTokenizer(pol, " ");
        Stack<Tnode> tempStack = new Stack<>();
        int i = 1;
        int tokensize = stringtokens.countTokens();

        while (stringtokens.hasMoreElements()) {
            String token = (String) stringtokens.nextElement();
            //kontrollime, kas on vaid 1 element (number)
            if (isInteger(token) && tokensize == 1) {
                root = new Tnode(token);
                return root;
            }

            //kontrollime, kas on tehtem2rk
            if (isOperator(token)) {
                if (tempStack.size() == 0) {
                    throw new RuntimeException(
                            String.format("Tehte jaoks on vaja kahte arvu, avaldis %s on vigane.", pol)
                    );
                } else if (tempStack.size() == 1) {
                    throw new RuntimeException(
                            String.format("Yks arv ei ole tehte jaoks piisav, vaja on kahte arvu. Avaldis %s on vigane.", pol)
                    );
                } else {
                    //juureks saab tehtem2rk, mis viitab j2rgmisele "siblingule" ja esimesele "childile", asendame stacki juurega
                    root = new Tnode(token);
                    root.nextSibling = tempStack.pop();
                    root.firstChild = tempStack.pop();
                    tempStack.push(root);
                }
            } else {
                if (i == tokensize && isInteger(token)) {
                    throw new RuntimeException(
                            String.format("Avaldis %s on vigane, viimane element ei tohi olla number(%s). Palun kontrollige, kas avaldises on piisavalt tehtem2rke.", pol, token)
                    );
                } else if (isInteger(token)) {
                    Tnode node = new Tnode(token);
                    tempStack.push(node);
                } else {
                    throw new RuntimeException(
                            String.format("%s on keelatud argument. Avaldises %s peaks olema ainult numbrid v6i tehtem2rgid.", token, pol)
                    );
                }
            }
            i++;
        }
        root = tempStack.pop();
        return root;
    }

    public static void main(String[] param) {
        String rpn = "1 2 +";
        System.out.println("RPN: " + rpn);
        Tnode res = buildFromRPN(rpn);
        System.out.println("Tree: " + res);
        String rpn2 = "5 7 * 2 /";
        System.out.println("RPN:" + rpn2);
        Tnode res2 = buildFromRPN(rpn2);
        System.out.println("Tree: " + res2);
        String rpn3 = "-115 44 + -11 -";
        System.out.println("RPN:" + rpn3);
        Tnode res3 = buildFromRPN(rpn3);
        System.out.println("Tree: " + res3);
        String rpn4 = " ";
        System.out.println("RPN:" + rpn4);
        Tnode res4 = buildFromRPN(rpn4);
        System.out.println("Tree: " + res4);
    }
}

