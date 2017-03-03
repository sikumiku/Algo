package homework.hw1;

public class ColorSort {

   public enum Color {
       red, green, blue;
   }

   public static Color[] balls = null;
   
   public static void main (String[] param) {
      // for debugging
   }

    public static void reorder(Color[] balls)
    {
        Color[] sortedBalls = new Color[balls.length];

        int j2rg = 0;

        for (int i = 0; i < balls.length; i++) {
            if (balls[i] == Color.red) {
                sortedBalls[j2rg] = balls[i];
                j2rg ++;
            }
        }
        for (int i = 0; i < balls.length; i++) {
            if (balls[i] == Color.green) {
                sortedBalls[j2rg] = balls[i];
                j2rg ++;
            }
        }

        for (int i = 0; i < balls.length; i++) {
            if (balls[i] == Color.blue) {
                sortedBalls[j2rg] = balls[i];
                j2rg ++;
            }
        }

        // sorting magic happens here

        System.arraycopy(sortedBalls, 0, balls, 0, balls.length);
    }



}

