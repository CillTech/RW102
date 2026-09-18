import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Exercise3 {

    static LocalDate examCreateDate = LocalDate.now();

    public static void question1() {
        System.out.println();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.println(examCreateDate.format(formatter));
    }

    public static void question2() {
        System.out.println();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
        System.out.println(examCreateDate.atStartOfDay().format(formatter));
    }

    public static void question3() {
        System.out.println();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy");
        System.out.println(examCreateDate.format(formatter));
    }

    public static void question4() {
        System.out.println();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-yyyy");
        System.out.println(examCreateDate.format(formatter));
    }

    public static void question5() {
        System.out.println();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd");
        System.out.println(examCreateDate.format(formatter));
    }
}