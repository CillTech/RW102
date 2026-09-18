import java.time.LocalDate;
import java.util.Random;

public class Exercise4 {
    static Random random = new Random();

    public static void question1() {
        System.out.println();
        System.out.println(random.nextInt());
    }

    public static void question2() {
        System.out.println();
        System.out.println(random.nextFloat());
    }

    public static void question3() {
        System.out.println();
        String[] students = {"An", "Bình", "Cường", "Dung", "Hoa"};
        int randomIndex = random.nextInt(students.length);
        System.out.println(students[randomIndex]);
    }

    public static void question4() {
        System.out.println();
        int minDay = (int) LocalDate.of(1995, 7, 24).toEpochDay();
        int maxDay = (int) LocalDate.of(1995, 12, 20).toEpochDay();
        long randomDay = minDay + random.nextInt(maxDay - minDay);
        LocalDate randomDate = LocalDate.ofEpochDay(randomDay);

        System.out.println(randomDate);
    }

    public static void question5() {
        System.out.println();
        int now = (int) LocalDate.now().toEpochDay();
        int oneYearAgo = (int) LocalDate.now().minusYears(1).toEpochDay();
        long randomDay = oneYearAgo + random.nextInt(now - oneYearAgo);
        LocalDate randomDate = LocalDate.ofEpochDay(randomDay);

        System.out.println(randomDate);
    }

    public static void question6() {
        System.out.println();
        int maxDay = (int) LocalDate.now().minusDays(1).toEpochDay();
        long randomDay = random.nextInt(maxDay);
        LocalDate randomDate = LocalDate.ofEpochDay(randomDay);

        System.out.println(randomDate);
    }

    public static void question7() {
        System.out.println();
        int randomNumber = random.nextInt(900) + 100;
        System.out.println(randomNumber);
    }
}