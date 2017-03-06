package homework.hw3;

import java.util.LinkedList;
public class DoubleStack {

   public static void main (String[] argum) {
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
           copy = (DoubleStack) m.clone();
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

    private LinkedList<Object> stack;

    private int sp;

   DoubleStack() {
       stack = new LinkedList<>();
       sp = stack.size() - 1;
   }

   @Override
   public Object clone() throws CloneNotSupportedException {
      return this; // TODO!!! Your code here!
   }

   public boolean stEmpty() {
      return (sp < stack.size() - 1);
   }

    public boolean stackEmpty() {
        return (sp < stack.size() - 1);
    }

    public boolean stackOverflow() {
        return (sp >= stack.size());
    }

   public void push (double a) {
      if (stackOverflow())
          throw new IndexOutOfBoundsException("Stack Overflow");
       sp += 1;
       stack.push(a);
   }

   public double pop() {
      if (stackEmpty())
          throw new IndexOutOfBoundsException("Stack Underflow");
       sp -= 1;
       Double tmp = (Double) stack.pop();
       return tmp;
   } // pop

   public void op (String s) {
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
  
   public double tos() {
      if (stackEmpty())
          throw new IndexOutOfBoundsException("Stack Underflow");
       return (double) stack.getFirst();
   }

   @Override
   public boolean equals (Object o) {
      if (((DoubleStack) o).sp != sp)
          return false;
       for (int i = 0; i <= sp; i++)
           if (((DoubleStack) o).stack.get(i) != stack.get(i))
               return false;
       return true;
   }

   @Override
   public String toString() {
      if (stackEmpty())
          return "Empty";
       StringBuffer b = new StringBuffer();
       for (int i = 0; i <= sp; i++)
           b.append(String.valueOf(stack.get(i)) + " ");
       return b.toString();

   }


   public static double interpret (String pol) {
      return 0.; // TODO!!! Your code here!
   }
}

