import java.util.Arrays;
import java.util.Comparator;

public class Exercise5 {

    static Department dept1 = new Department(1, "Sale");
    static Department dept2 = new Department(2, "Marketing");
    static Department dept3 = new Department(3, "Boss of director");
    static Department dept4 = new Department(4, "Waiting room");
    static Department dept5 = new Department(5, "Accounting");

    static Department[] departments = {dept1, dept2, dept3, dept4, dept5};

    public static void question5() {
        if (dept1.getName().equals(dept2.getName())) {
            System.out.println("Bằng nhau");
        } else {
            System.out.println("Không bằng nhau");
        }
    }

    public static void question6() {
        Arrays.sort(departments, new Comparator<Department>() {
            @Override
            public int compare(Department d1, Department d2) {
                return d1.getName().compareToIgnoreCase(d2.getName());
            }
        });

        for (Department d : departments) {
            System.out.println(d.getName());
        }
    }

    public static void question7() {
        Arrays.sort(departments, new Comparator<Department>() {
            @Override
            public int compare(Department d1, Department d2) {
                return d1.getName().compareToIgnoreCase(d2.getName());
            }
        });

        for (Department d : departments) {
            System.out.println(d.getName());
        }
    }
}