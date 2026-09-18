import java.time.LocalDate;

public class Exercise1 {

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

    public static void question10() {
        for (int i = 0; i < accounts.length; i++) {
            System.out.println("Thông tin account thứ " + (i + 1) + " là:");
            System.out.println("Email: " + accounts[i].getEmail());
            System.out.println("Full name: " + accounts[i].getFullName());
            System.out.println("Phòng ban: " + accounts[i].getDepartment().getName());
        }
    }

    public static void question11() {
        for (int i = 0; i < departments.length; i++) {
            System.out.println("Thông tin department thứ " + (i + 1) + " là:");
            System.out.println("Id: " + departments[i].getid());
            System.out.println("Name: " + departments[i].getName());
        }
    }

    public static void question12() {
        for (int i = 0; i < 2 && i < departments.length; i++) {
            System.out.println("Thông tin department thứ " + (i + 1) + " là:");
            System.out.println("Id: " + departments[i].getid());
            System.out.println("Name: " + departments[i].getName());
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
        }
    }

    public static void question14() {
        for (int i = 0; i < accounts.length; i++) {
            if (accounts[i].getid() < 4) {
                System.out.println("Thông tin account thứ " + (i + 1) + " là:");
                System.out.println("Email: " + accounts[i].getEmail());
                System.out.println("Full name: " + accounts[i].getFullName());
                System.out.println("Phòng ban: " + accounts[i].getDepartment().getName());
            }
        }
    }

    public static void question15() {
        for (int i = 0; i <= 20; i++) {
            if (i % 2 == 0) {
                System.out.print(i + " ");
            }
        }
        System.out.println();
    }

    public static void question16() {
        System.out.println();
        int i10 = 0;
        while (i10 < accounts.length) {
            System.out.println("Thông tin account thứ " + (i10 + 1) + " là:");
            System.out.println("Email: " + accounts[i10].getEmail());
            System.out.println("Full name: " + accounts[i10].getFullName());
            System.out.println("Phòng ban: " + accounts[i10].getDepartment().getName());
            i10++;
        }

        int i11 = 0;
        while (i11 < departments.length) {
            System.out.println("Thông tin department thứ " + (i11 + 1) + " là:");
            System.out.println("Id: " + departments[i11].getid());
            System.out.println("Name: " + departments[i11].getName());
            i11++;
        }

        int i12 = 0;
        while (i12 < departments.length) {
            if (i12 == 2) {
                break;
            }
            System.out.println("Thông tin department thứ " + (i12 + 1) + " là:");
            System.out.println("Id: " + departments[i12].getid());
            System.out.println("Name: " + departments[i12].getName());
            i12++;
        }

        int i13 = 0;
        while (i13 < accounts.length) {
            if (i13 == 1) {
                i13++;
                continue;
            }
            System.out.println("Thông tin account thứ " + (i13 + 1) + " là:");
            System.out.println("Email: " + accounts[i13].getEmail());
            System.out.println("Full name: " + accounts[i13].getFullName());
            System.out.println("Phòng ban: " + accounts[i13].getDepartment().getName());
            i13++;
        }

        int i14 = 0;
        while (i14 < accounts.length) {
            if (accounts[i14].getid() >= 4) {
                i14++;
                continue;
            }
            System.out.println("Thông tin account thứ " + (i14 + 1) + " là:");
            System.out.println("Email: " + accounts[i14].getEmail());
            System.out.println("Full name: " + accounts[i14].getFullName());
            System.out.println("Phòng ban: " + accounts[i14].getDepartment().getName());
            i14++;
        }

        int i15 = 0;
        while (i15 <= 20) {
            if (i15 % 2 != 0) {
                i15++;
                continue;
            }
            System.out.print(i15 + " ");
            i15++;
        }
        System.out.println();
    }

    public static void question17() {
        System.out.println();
        int j10 = 0;
        if (accounts.length > 0) {
            do {
                System.out.println("Thông tin account thứ " + (j10 + 1) + " là:");
                System.out.println("Email: " + accounts[j10].getEmail());
                System.out.println("Full name: " + accounts[j10].getFullName());
                System.out.println("Phòng ban: " + accounts[j10].getDepartment().getName());
                j10++;
            } while (j10 < accounts.length);
        }

        int j11 = 0;
        if (departments.length > 0) {
            do {
                System.out.println("Thông tin department thứ " + (j11 + 1) + " là:");
                System.out.println("Id: " + departments[j11].getid());
                System.out.println("Name: " + departments[j11].getName());
                j11++;
            } while (j11 < departments.length);
        }

        int j12 = 0;
        if (departments.length > 0) {
            do {
                if (j12 == 2) {
                    break;
                }
                System.out.println("Thông tin department thứ " + (j12 + 1) + " là:");
                System.out.println("Id: " + departments[j12].getid());
                System.out.println("Name: " + departments[j12].getName());
                j12++;
            } while (j12 < departments.length);
        }

        int j13 = 0;
        if (accounts.length > 0) {
            do {
                if (j13 == 1) {
                    j13++;
                    continue;
                }
                System.out.println("Thông tin account thứ " + (j13 + 1) + " là:");
                System.out.println("Email: " + accounts[j13].getEmail());
                System.out.println("Full name: " + accounts[j13].getFullName());
                System.out.println("Phòng ban: " + accounts[j13].getDepartment().getName());
                j13++;
            } while (j13 < accounts.length);
        }

        int j14 = 0;
        if (accounts.length > 0) {
            do {
                if (accounts[j14].getid() >= 4) {
                    j14++;
                    continue;
                }
                System.out.println("Thông tin account thứ " + (j14 + 1) + " là:");
                System.out.println("Email: " + accounts[j14].getEmail());
                System.out.println("Full name: " + accounts[j14].getFullName());
                System.out.println("Phòng ban: " + accounts[j14].getDepartment().getName());
                j14++;
            } while (j14 < accounts.length);
        }

        int j15 = 0;
        do {
            if (j15 % 2 != 0) {
                j15++;
                continue;
            }
            System.out.print(j15 + " ");
            j15++;
        } while (j15 <= 20);
        System.out.println();
    }

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