import java.time.LocalDate;
import java.util.Scanner;

public class Exercise4 {

    static Scanner scanner = new Scanner(System.in);

    static Group group1 = new Group(1, "Java Fresher", null, LocalDate.now());
    static Group group2 = new Group(2, "C# Fresher", null, LocalDate.now());
    static Group group3 = new Group(3, "Java", null, LocalDate.now());
    static Group[] groups = {group1, group2, group3};

    public static void question1() {
        String str = scanner.nextLine().trim();
        if (str.isEmpty()) {
            System.out.println(0);
        } else {
            System.out.println(str.split("\\s+").length);
        }
    }

    public static void question2() {
        String s1 = scanner.nextLine();
        String s2 = scanner.nextLine();
        System.out.println(s1 + s2);
    }

    public static void question3() {
        String name = scanner.nextLine();
        if (name.length() > 0) {
            String firstChar = name.substring(0, 1).toUpperCase();
            String rest = name.substring(1);
            System.out.println(firstChar + rest);
        }
    }

    public static void question4() {
        String name = scanner.nextLine().toUpperCase();
        for (int i = 0; i < name.length(); i++) {
            System.out.println("Ký tự thứ " + (i + 1) + " là: " + name.charAt(i));
        }
    }

    public static void question5() {
        String ho = scanner.nextLine();
        String ten = scanner.nextLine();
        System.out.println(ho + " " + ten);
    }

    public static void question6() {
        String fullName = scanner.nextLine().trim();
        String[] words = fullName.split("\\s+");

        System.out.println("Họ là: " + words[0]);

        String middle = "";
        for (int i = 1; i < words.length - 1; i++) {
            middle += words[i] + " ";
        }
        System.out.println("Tên đệm là: " + middle.trim());

        System.out.println("Tên là: " + words[words.length - 1]);
    }

    public static void question7() {
        String fullName = scanner.nextLine().trim();
        String[] words = fullName.split("\\s+");
        String result = "";
        for (String word : words) {
            result += word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase() + " ";
        }
        System.out.println(result.trim());
    }

    public static void question8() {
        for (Group group : groups) {
            if (group.getName().contains("Java")) {
                System.out.println(group.getName());
            }
        }
    }

    public static void question9() {
        for (Group group : groups) {
            if (group.getName().equals("Java")) {
                System.out.println(group.getName());
            }
        }
    }

    public static void question10() {
        String s1 = scanner.nextLine();
        String s2 = scanner.nextLine();
        String reverseS1 = new StringBuilder(s1).reverse().toString();
        if (reverseS1.equals(s2)) {
            System.out.println("OK");
        } else {
            System.out.println("KO");
        }
    }

    public static void question11() {
        String s = scanner.nextLine();
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == 'a') {
                count++;
            }
        }
        System.out.println(count);
    }

    public static void question12() {
        String s = scanner.nextLine();
        String reverse = "";
        for (int i = s.length() - 1; i >= 0; i--) {
            reverse += s.charAt(i);
        }
        System.out.println(reverse);
    }

    public static void question13() {
        String s = scanner.nextLine();
        boolean hasDigit = false;
        if (s != null) {
            for (int i = 0; i < s.length(); i++) {
                if (Character.isDigit(s.charAt(i))) {
                    hasDigit = true;
                    break;
                }
            }
        }
        System.out.println(!hasDigit);
    }

    public static void question14() {
        String str = scanner.nextLine();
        System.out.println(str.replace('e', '*'));
    }

    public static void question15() {
        String str = scanner.nextLine().trim();
        String[] words = str.split("\\s+");
        String result = "";
        for (int i = words.length - 1; i >= 0; i--) {
            result += words[i] + " ";
        }
        System.out.println(result.trim());
    }

    public static void question16() {
        String str = scanner.next();
        int n = scanner.nextInt();
        scanner.nextLine();

        if (n <= 0 || str.length() % n != 0 || str.isEmpty()) {
            System.out.println("KO");
        } else {
            for (int i = 0; i < str.length(); i += n) {
                System.out.println(str.substring(i, i + n));
            }
        }
    }
}