import java.time.LocalDate;

public class Program {
    public static void main(String[] args) {

        Department dept1 = new Department(1, "Sale");
        Department dept2 = new Department(2, "Marketing");
        Department dept3 = new Department(3, "Boss");

        Position pos1 = new Position(1, PositionName.DEV);
        Position pos2 = new Position(2, PositionName.TEST);
        Position pos3 = new Position(3, PositionName.PM);

        Account acc1 = new Account(1, "nguyenvanA@gmail.com", "nguyenvana", "Nguyen Van A", dept1, pos1, LocalDate.now());
        Account acc2 = new Account(2, "tranthiB@gmail.com", "tranthib", "Tran Thi B", dept2, pos2, LocalDate.now());
        Account acc3 = new Account(3, "admin@gmail.com", "admin", "Admin System", dept3, pos3, LocalDate.now());

        Group group1 = new Group(1, "Java Fresher", acc3, LocalDate.now());
        Group group2 = new Group(2, "C# Fresher", acc1, LocalDate.now());
        Group group3 = new Group(3, "Testing System", acc2, LocalDate.now());

        GroupAccount ga1 = new GroupAccount(group1, acc1, LocalDate.now());
        GroupAccount ga2 = new GroupAccount(group2, acc2, LocalDate.now());
        GroupAccount ga3 = new GroupAccount(group3, acc3, LocalDate.now());

        TypeQuestion tq1 = new TypeQuestion(1, TypeName.ESSAY);
        TypeQuestion tq2 = new TypeQuestion(2, TypeName.MULTIPLE_CHOICE);
        TypeQuestion tq3 = new TypeQuestion(3, TypeName.ESSAY);

        CategoryQuestion cq1 = new CategoryQuestion(1, "Java");
        CategoryQuestion cq2 = new CategoryQuestion(2, "SQL");
        CategoryQuestion cq3 = new CategoryQuestion(3, ".NET");

        Question q1 = new Question(1, "Java là gì?", cq1, tq1, acc1, LocalDate.now());
        Question q2 = new Question(2, "Câu lệnh SELECT dùng làm gì?", cq2, tq2, acc2, LocalDate.now());
        Question q3 = new Question(3, "OOP có mấy tính chất?", cq3, tq3, acc3, LocalDate.now());

        Answer ans1 = new Answer(1, "Java là ngôn ngữ lập trình hướng đối tượng", q1, true);
        Answer ans2 = new Answer(2, "SELECT dùng để truy vấn dữ liệu", q2, true);
        Answer ans3 = new Answer(3, "OOP có 3 tính chất", q3, false);

        Exam ex1 = new Exam(1, "EX_01", "Kiểm tra Java", cq1, 60, acc3, LocalDate.now());
        Exam ex2 = new Exam(2, "EX_02", "Kiểm tra SQL", cq2, 45, acc1, LocalDate.now());
        Exam ex3 = new Exam(3, "EX_03", "Kiểm tra .NET", cq3, 90, acc2, LocalDate.now());

        ExamQuestion eq1 = new ExamQuestion(ex1, q1);
        ExamQuestion eq2 = new ExamQuestion(ex2, q2);
        ExamQuestion eq3 = new ExamQuestion(ex3, q3);

        System.out.println(dept1.toString());
        System.out.println(pos1.toString());
        System.out.println(acc1.toString());
        System.out.println(group1.toString());
        System.out.println(ga1.toString());
        System.out.println(tq1.toString());
        System.out.println(cq1.toString());
        System.out.println(q1.toString());
        System.out.println(ans1.toString());
        System.out.println(ex1.toString());
        System.out.println(eq1.toString());
    }
}