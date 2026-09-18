import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Exercise2 {

    public static void question1() {
        System.out.println();
        int a = 5;
        System.out.printf("%d%n", a);
    }

    public static void question2() {
        System.out.println();
        int b = 100000000;
        System.out.printf("%,d%n", b);
    }

    public static void question3() {
        System.out.println();
        float c = 5.567098f;
        System.out.printf("%.4f%n", c);
    }

    public static void question4() {
        System.out.println();
        String name = "Nguyễn Văn A";
        System.out.printf("Tên tôi là \"%s\" và tôi đang độc thân.%n", name);
    }

    public static void question5() {
        System.out.println();
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH'h':mm'p':ss's'");
        System.out.println(now.format(formatter));
    }

    public static void question6() {
        System.out.println();
        String[] emails = {"nguyenvana@gmail.com", "tranthib@gmail.com"};
        String[] fullNames = {"Nguyen Van A", "Tran Thi B"};
        String[] deptNames = {"Sale", "Marketing"};

        System.out.printf("%-25s | %-20s | %-15s%n", "Email", "Full Name", "Department");
        System.out.println("------------------------------------------------------------------");

        for (int i = 0; i < emails.length; i++) {
            System.out.printf("%-25s | %-20s | %-15s%n", emails[i], fullNames[i], deptNames[i]);
        }
    }
}