package homework.hw3;

/*Tunni jooksul parandatud 17.03.2017
Github: https://github.com/sikumiku/Algo
Kasutatud kirjandus:
https://docs.oracle.com/javase/7/docs/api/javax/xml/soap/Node.html
http://stackoverflow.com/questions/28844026/writing-an-equals-method-for-linked-list-object
http://beginnersbook.com/2013/12/linkedlist-in-java-with-example/
http://crunchify.com/how-to-iterate-through-linkedlist-instance-in-java/
http://docs.oracle.com/javase/7/docs/api/java/lang/StringBuilder.html
http://stackoverflow.com/questions/15625629/regex-expressions-in-java-s-vs-s
https://www.cs.cmu.edu/~adamchik/15-121/lectures/Linked%20Lists/linked%20lists.html
http://docs.oracle.com/javase/7/docs/api/java/util/LinkedList.html
http://www.java2s.com/Book/Java/0080__Collections/Get_the_index_of_an_element.htm
 */

import java.util.LinkedList;

public class DoubleStack {

    //For testing purposes
    public static void main(String[] argum) {
        DoubleStack m = new DoubleStack();
        System.out.println(m);
        m.push(1);
        System.out.println(m);
        m.push(3);
        System.out.println(m);
        m.push(6);
        System.out.println(m);
        m.push(2);
        System.out.println(m);
        m.op("/");
        System.out.println(m);
        m.op("*");
        System.out.println(m);
        m.op("-");
        System.out.println(m);
        double result = m.pop();
        System.out.println(m);
        System.out.println(result);
        DoubleStack copy = m;
        System.out.println(copy.equals(m));
        System.out.println(m);
        System.out.println(copy);
        try {
            copy = m.clone();
        } catch (CloneNotSupportedException e) {
        }
        System.out.println(copy.equals(m));
        System.out.println(m);
        System.out.println(copy);
        m.push(6);
        System.out.println(copy.equals(m));
        System.out.println(m);
        System.out.println(copy);
        m.pop();
        System.out.println(copy.equals(m));
        System.out.println(m);
        System.out.println(copy);
        String prog = "2 3 + 4 * 10 /";
        if (argum.length > 0) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < argum.length; i++) {
                sb.append(argum[i]);
                sb.append(" ");
            }
            prog = sb.toString();
        }
        System.out.println(interpret("1 4 / -"));

    }

    private class Node
    {
        private double value;
        private Node   prev;
        private Node   next;
        public Node(double ivalue, Node iprev, Node inext)
        {
            value = ivalue;
            prev  = iprev;
            next  = inext;
        }
    }

    private Node head  = null;
    private Node tail  = null;


    private LinkedList<Double> stack;


    DoubleStack() {
        stack = new LinkedList<>();
    }

    @Override
    public DoubleStack clone() throws CloneNotSupportedException {

        DoubleStack stackclone = new DoubleStack();

        for (int i = stack.size() - 1; i >= 0; i--) {
            stackclone.push(stack.get(i));
        }

        return stackclone;
    }

    public boolean stEmpty() {
        return stack.size() == 0;
    }


    public boolean stackEmpty() {
        return stack.size() <= 0;
    }


    //Push element and assign Tail position
    public void push(double a) {

        stack.push(a);

        if (stack.size() == 1) {
            head = tail = new Node(a, null, null);

        } else {
            tail = tail.next = new Node(a, tail, null);
        }


    }

    //Pop element and decrease tracker
    public double pop() {
        if (stackEmpty())
            throw new IndexOutOfBoundsException("Stack Underflow");

        Double tmp = stack.pop();
        Double tmp2 = stack.peek();

        if (stack.size() == 0) {
            head = tail = null;
        } else {
            tail = tail.prev = new Node (tmp2, tail.prev.prev, tail);
        }

        return tmp;
    }

    //In case of one of four different operands remove 2 elements and use operand on them
    public void op(String s) {
        if (isOperator(s)) {
            if (stack.size() >= 1) {
                double op2 = pop();
                double op1 = pop();

                switch (s) {
                    case "+": push(op1 + op2);
                        break;
                    case "-": push(op1 - op2);
                        break;
                    case "*": push(op1 * op2);
                        break;
                    case "/": push(op1 / op2);
                        break;
                }
                System.out.println(stack);
            }
            else throw new RuntimeException(String.format(
                    "Not enough members to perform calculation."));
        } else {
            throw new RuntimeException(String.format(
                    "Element '%s' is not an Operator.", s));
        }

    }

    //Only check and return element at Head of stack
    public double tos() {
        if (stackEmpty())
            throw new IndexOutOfBoundsException("Stack Underflow");
        return stack.peek();
    }

    @Override
    //First check if markers match in both Stacks. Then iterate through the stack and compare each position with other stack
    public boolean equals(Object o) {
        if (!(o instanceof DoubleStack)) return false;
        DoubleStack ds = (DoubleStack) o;
        int i = 0;
        for (double a : stack) {
            if (a != ds.stack.get(i)) {
                return false;
            }
            i++;
        }
        if (i != ds.stack.size()) return false; //Also compare the sizes of both stacks
        return true;
    }



    @Override
    //Iterate through the Stack and attach each element to StringBuffer
    public String toString() {
        StringBuffer sb = new StringBuffer();

        for (Node n = head; n != null; n = n.next) {
            sb.append(n.value);
        }
        return sb.toString();
    }

    //First check if operator has only 1 character in order to qualify. Then check if that character equals with any of 4 operands.
    private static boolean isOperator(String s) {
        if (s.length() != 1) return false;
        char c = s.charAt(0);
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    public static double interpret(String pol) { //peegeldada string pol'i veateadetes
        if (pol.isEmpty()) throw new RuntimeException("No input string.");
        DoubleStack DSinterpret = new DoubleStack();
        //create an array to handle String elements, \\s+ helps to separate elements by detecting whitespace characters
        String[] elements = pol.split("\\s+");
        //iterate through array to spot operators and try to apply op() using the operator
        for (int i = 0; i < elements.length; ++i) {
            String e = elements[i];
            if (e.isEmpty()) continue;
            if (isOperator(e)) {
                try {
                    DSinterpret.op(e);
                } catch (IndexOutOfBoundsException ex) {
                    throw new RuntimeException(String.format(
                            "Token '%s' in string '%s' is invalid.", e, pol));
                }
            } else {
                //in case not an operator, convert String to double and push to stack
                try {
                    DSinterpret.push(Double.parseDouble(e));
                } catch (NumberFormatException ex) {
                    throw new RuntimeException(String.format(
                            "Unable to push element '%s' in string '%s' if not a number.", e, pol));
                }
            }
        }
        //Check if only one element is remaining
        if (DSinterpret.stack.size() != 1)
            throw new RuntimeException("Invalid expression, unable to calculate final result: " + pol);
        //Return result from stack.
        return DSinterpret.pop();
    }
}

