package homework.hw3;

/*Kasutatud kirjandus:
https://docs.oracle.com/javase/7/docs/api/javax/xml/soap/Node.html
http://stackoverflow.com/questions/28844026/writing-an-equals-method-for-linked-list-object
http://beginnersbook.com/2013/12/linkedlist-in-java-with-example/
http://crunchify.com/how-to-iterate-through-linkedlist-instance-in-java/
http://docs.oracle.com/javase/7/docs/api/java/lang/StringBuilder.html
http://stackoverflow.com/questions/15625629/regex-expressions-in-java-s-vs-s
https://www.cs.cmu.edu/~adamchik/15-121/lectures/Linked%20Lists/linked%20lists.html
http://docs.oracle.com/javase/7/docs/api/java/util/LinkedList.html
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
        System.out.println(prog + "\n "
                + String.valueOf(interpret(prog)));

    }


    private int Count = 0;
    private LinkedList<Double> stack;
    //Marker sp is for tracking stack content
    private int sp;

    DoubleStack() {
        stack = new LinkedList<>();
        //LinkedList marker is marked at the end of the stack to track last element position
        sp = stack.size() - 1;
    }

    @Override
    public DoubleStack clone() throws CloneNotSupportedException {
        DoubleStack stackclone = new DoubleStack();
        try {
            //iterate from back to front in order to push to new Stack
            for (int i = sp; i >= 0; i--) {
                stackclone.push(stack.get(i));
            }
        } catch (Exception e) {
            throw new CloneNotSupportedException("Cloning unsuccessful.");
        }
        return stackclone;

    }

    public boolean stEmpty() {
        return Count == 0;
    }

    //Stack is only empty is tracker is smaller than the supposed last element position
    public boolean stackEmpty() {
        return (sp < stack.size() - 1);
    }

    //Stack is only overflown if tracker equals or succeeds stack's size (should always be 1 behind)
    public boolean stackOverflow() {
        return (sp >= stack.size());
    }

    //Push element and increase tracker
    public void push(double a) {
        if (stackOverflow())
            throw new IndexOutOfBoundsException("Stack Overflow");
        sp += 1;
        stack.push(a);
    }

    //Pop element and decrease tracker
    public double pop() {
        if (stackEmpty())
            throw new IndexOutOfBoundsException("Stack Underflow");
        sp -= 1;
        Double tmp = stack.pop();
        return tmp;
    } // pop

    //In case of one of four different operands remove 2 elements and use operand on them
    public void op(String s) {
        double op2 = pop();
        double op1 = pop();
        if (s.equals("+"))
            push(op1 + op2);
        if (s.equals("-"))
            push(op1 - op2);
        if (s.equals("*"))
            push(op1 * op2);
        if (s.equals("/"))
            push(op1 / op2);
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
        if (sp != ds.sp) return false;
        int i = 0;
        for (double a : stack) {
            if (a != ds.stack.get(i)) {
                return false;
            }
            i++;
        }
        return true;
    }

    @Override
    //Iterate through the Stack and attach each element to StringBuffer
    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (int i = sp; i >= 0; i--) {
            sb.append(stack.get(i));
        }
        return sb.toString();
    }

    //First check if operator has only 1 character in order to qualify. Then check if that character equals with any of 4 operands.
    private static boolean isOperator(String s) {
        if (s.length() != 1) return false;
        char c = s.charAt(0);
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    public static double interpret(String pol) {
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
                            "Token '%s' at is invalid.", e));
                }
            } else {
                //in case not an operator, convert String to double and push to stack
                try {
                    DSinterpret.push(Double.parseDouble(e));
                } catch (NumberFormatException ex) {
                    throw new RuntimeException(String.format(
                            "Unable to push element '%s' if not a number.", e));
                }
            }
        }
        //Check if only one element is remaining
        if (DSinterpret.stack.size() != 1)
            throw new RuntimeException("Invalid expression: " + pol);
        //Return result from stack.
        return DSinterpret.pop();
    }
}

