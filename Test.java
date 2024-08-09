import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        try (Scanner userIn = new Scanner(System.in)) {
            int myInt_1 = userIn.nextInt();
            int myInt_2 = userIn.nextInt();
            int myInt_3 = userIn.nextInt();
            if (myInt_2 == 2)
                System.out.print(myInt_1 + myInt_2 + myInt_3);
        }
    }
}
