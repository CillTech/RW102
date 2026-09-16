import java.time.LocalDate;

public class Program {

    static Department dept1 = new Department(1, "Sale");
    static Department dept2 = new Department(2, "Marketing");
    static Department dept3 = new Department(3, "Boss");

    static Position pos1 = new Position(1, PositionName.DEV);
    static Position pos2 = new Position(2, PositionName.TEST);
    static Position pos3 = new Position(3, PositionName.PM);

    static Account acc1 = new Account(1, "nguyenvanA@gmail.com", "nguyenvana", "Nguyen Van A", dept1, pos1, LocalDate.now());
    static Account acc2 = new Account(2, "tranthiB@gmail.com", "tranthib", "Tran Thi B", dept2, pos2, LocalDate.now());
    static Account acc3 = new Account(3, "admin@gmail.com", "admin", "Admin System", dept3, pos3, LocalDate.now());

    static Group group1 = new Group(1, "Java Fresher", acc3, LocalDate.now());
    static Group group2 = new Group(2, "C# Fresher", acc1, LocalDate.now());
    static Group group3 = new Group(3, "Testing System", acc2, LocalDate.now());

    static GroupAccount ga1 = new GroupAccount(group1, acc1, LocalDate.now());
    static GroupAccount ga2 = new GroupAccount(group2, acc2, LocalDate.now());
    static GroupAccount ga3 = new GroupAccount(group1, acc2, LocalDate.now());
    static GroupAccount ga4 = new GroupAccount(group3, acc3, LocalDate.now());

    static Account[] accounts = {acc1, acc2, acc3};
    static Department[] departments = {dept1, dept2, dept3};
    static GroupAccount[] groupAccounts = {ga1, ga2, ga3, ga4};

    public static void main(String[] args) {
        System.out.println("--- QUESTION 1 ---"); question1();
        System.out.println("\n--- QUESTION 2 ---"); question2();
        System.out.println("\n--- QUESTION 3 ---"); question3();
        System.out.println("\n--- QUESTION 4 ---"); question4();
        System.out.println("\n--- QUESTION 5 ---"); question5();
        System.out.println("\n--- QUESTION 6 ---"); question6();
        System.out.println("\n--- QUESTION 7 ---"); question7();
        System.out.println("\n--- QUESTION 8 ---"); question8();
        System.out.println("\n--- QUESTION 9 ---"); question9();
        System.out.println("\n--- QUESTION 10 ---"); question10();
        System.out.println("\n--- QUESTION 11 ---"); question11();
        System.out.println("\n--- QUESTION 12 ---"); question12();
        System.out.println("\n--- QUESTION 13 ---"); question13();
        System.out.println("\n--- QUESTION 14 ---"); question14();
        System.out.println("\n--- QUESTION 15 ---"); question15();
    }

    // ================== LỆNH IF ==================

    public static void question1() {
        if (acc2.getDepartment() == null) {
            System.out.println("Nhân viên này chưa có phòng ban");
        } else {
            System.out.println("Phòng ban của nhân viên này là " + acc2.getDepartment().getName());
        }
    }

    public static void question2() {
        int groupCount = countGroupsOfAccount(acc2);
        if (groupCount == 0) {
            System.out.println("Nhân viên này chưa có group");
        } else if (groupCount == 1 || groupCount == 2) {
            System.out.println("Group của nhân viên này là Java Fresher, C# Fresher");
        } else if (groupCount == 3) {
            System.out.println("Nhân viên này là người quan trọng, tham gia nhiều group");
        } else if (groupCount >= 4) {
            System.out.println("Nhân viên này là người hóng chuyện, tham gia tất cả các group");
        }
    }

    // ================== TOÁN TỬ TERNARY ==================

    public static void question3() {
        String result = (acc2.getDepartment() == null)
                ? "Nhân viên này chưa có phòng ban"
                : "Phòng ban của nhân viên này là " + acc2.getDepartment().getName();
        System.out.println(result);
    }

    public static void question4() {
        String result = (acc1.getPosition().getName() == PositionName.DEV)
                ? "Đây là Developer"
                : "Người này không phải là Developer";
        System.out.println(result);
    }

    // ================== LỆNH SWITCH CASE ==================

    public static void question5() {
        int accountCount = countAccountsInGroup(group1);
        switch (accountCount) {
            case 1:
                System.out.println("Nhóm có một thành viên");
                break;
            case 2:
                System.out.println("Nhóm có hai thành viên");
                break;
            case 3:
                System.out.println("Nhóm có ba thành viên");
                break;
            default:
                System.out.println("Nhóm có nhiều thành viên");
                break;
        }
    }

    public static void question6() {
        int groupCount = countGroupsOfAccount(acc2);
        switch (groupCount) {
            case 0:
                System.out.println("Nhân viên này chưa có group");
                break;
            case 1:
            case 2:
                System.out.println("Group của nhân viên này là Java Fresher, C# Fresher");
                break;
            case 3:
                System.out.println("Nhân viên này là người quan trọng, tham gia nhiều group");
                break;
            default:
                System.out.println("Nhân viên này là người hóng chuyện, tham gia tất cả các group");
                break;
        }
    }

    public static void question7() {
        switch (acc1.getPosition().getName()) {
            case DEV:
                System.out.println("Đây là Developer");
                break;
            default:
                System.out.println("Người này không phải là Developer");
                break;
        }
    }

    // ================== LỆNH FOREACH ==================

    public static void question8() {
        for (Account acc : accounts) {
            System.out.println("Email: " + acc.getEmail() + " | FullName: " + acc.getFullName() +
                    " | Phòng ban: " + acc.getDepartment().getName());
        }
    }

    public static void question9() {
        for (Department dept : departments) {
            System.out.println("ID: " + dept.getid() + " | Name: " + dept.getName());
        }
    }

    // ================== LỆNH FOR ==================

    public static void question10() {
        for (int i = 0; i < accounts.length; i++) {
            System.out.println("Thông tin account thứ " + (i + 1) + " là:");
            System.out.println("Email: " + accounts[i].getEmail());
            System.out.println("Full name: " + accounts[i].getFullName());
            System.out.println("Phòng ban: " + accounts[i].getDepartment().getName());
            System.out.println("-------------------------");
        }
    }

    public static void question11() {
        for (int i = 0; i < departments.length; i++) {
            System.out.println("Thông tin department thứ " + (i + 1) + " là:");
            System.out.println("Id: " + departments[i].getid());
            System.out.println("Name: " + departments[i].getName());
            System.out.println("-------------------------");
        }
    }

    public static void question12() {
        for (int i = 0; i < 2 && i < departments.length; i++) {
            System.out.println("Thông tin department thứ " + (i + 1) + " là:");
            System.out.println("Id: " + departments[i].getid());
            System.out.println("Name: " + departments[i].getName());
            System.out.println("-------------------------");
        }
    }

    public static void question13() {
        for (int i = 0; i < accounts.length; i++) {
            if (i == 1) {
                continue;
            }
            System.out.println("Thông tin account thứ " + (i + 1) + " là:");
            System.out.println("Email: " + accounts[i].getEmail());
            System.out.println("Full name: " + accounts[i].getFullName());
            System.out.println("Phòng ban: " + accounts[i].getDepartment().getName());
            System.out.println("-------------------------");
        }
    }

    public static void question14() {
        for (int i = 0; i < accounts.length; i++) {
            if (accounts[i].getid() < 4) {
                System.out.println("Thông tin account (ID < 4) thứ " + (i + 1) + " là:");
                System.out.println("Email: " + accounts[i].getEmail());
                System.out.println("Full name: " + accounts[i].getFullName());
                System.out.println("-------------------------");
            }
        }
    }

    public static void question15() {
        System.out.print("Các số chẵn nhỏ hơn hoặc bằng 20: ");
        for (int i = 0; i <= 20; i++) {
            if (i % 2 == 0) {
                System.out.print(i + " ");
            }
        }
        System.out.println();
    }

    // ================== HÀM BỔ TRỢ ==================

    private static int countGroupsOfAccount(Account acc) {
        int count = 0;
        for (GroupAccount ga : groupAccounts) {
            if (ga.getAccount().getid() == acc.getid()) {
                count++;
            }
        }
        return count;
    }

    private static int countAccountsInGroup(Group grp) {
        int count = 0;
        for (GroupAccount ga : groupAccounts) {
            if (ga.getGroup().getid() == grp.getid()) {
                count++;
            }
        }
        return count;
    }
}