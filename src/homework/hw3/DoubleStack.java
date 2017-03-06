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
       String prog = "- 2. 5.";
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

    private class Node
    {
        public double Value;
        public Node   Prev;
        public Node   Next;
        public Node(double value, Node prev, Node next)
        {
            Value = value;
            Prev  = prev;
            Next  = next;
        }
    }

    private Node Head  = null;
    private Node Tail  = null;
    private int  Count = 0;


    private LinkedList<Object> stack;

    private int sp;

   DoubleStack() {
       stack = new LinkedList<>();
       sp = stack.size() - 1;
   }

    DoubleStack (DoubleStack ds)
    {
        for (Node n = ds.Head; n != null; n = n.Next)
            this.push(n.Value);
    }

   @Override
   public DoubleStack clone() throws CloneNotSupportedException {
       return new DoubleStack(this);
   }

   public boolean stEmpty() {
       return Count == 0;
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
       return (double) stack.peek();
   }

   @Override
   public boolean equals (Object o) {
       if (!(o instanceof DoubleStack)) return false;
       DoubleStack ds = (DoubleStack)o;
       if (sp != ds.sp) return false;

       Node n1 = Head;
       Node n2 = ds.Head;
       while (n1 != null) {
           if (n1.Value != n2.Value) {
               return false;
           }
           n1 = n1.Next;
           n2 = n2.Next;
       }

       return true;
   }

   @Override
   public String toString() {

       StringBuilder sb = new StringBuilder();
       for (Node n = Head; n != null; n = n.Next)
           sb.append(n.Value).append(" ");
       return sb.toString();

   }

    private static boolean isOperator(String s) {
        if (s.length() != 1) return false;
        char c = s.charAt(0);
        return c == '+' || c == '-' || c == '*' || c == '/';
    }


   public static double interpret (String pol) {
       if (pol.isEmpty()) throw new RuntimeException("Interpret failed: Input string was empty");

       DoubleStack s = new DoubleStack();
       String[] tokens = pol.split("\\s+");
       for (int i = 0; i < tokens.length; ++i)
       {
           String t = tokens[i];
           if (t.isEmpty()) continue;
           if (isOperator(t))
           {
               try
               {
                   s.op(t);
               }
               catch (IndexOutOfBoundsException ex)
               {
                   throw new RuntimeException(String.format(
                           "Token '%s' at pos %d is invalid: Not enough tokens to apply operator", t, i));
               }
           }
           else
           {
               try
               {
                   s.push(Double.parseDouble(t));
               }
               catch (NumberFormatException ex)
               {
                   throw new RuntimeException(String.format(
                           "Expected number but got: '%s' in expression '%s'", t, pol));
               }
           }
       }
       if (s.stack.size() != 1)
           throw new RuntimeException("Invalid expression: "+pol);
       return s.pop();
   }
}

