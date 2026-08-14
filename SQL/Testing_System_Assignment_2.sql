drop database if exists testing_system_assignment_1;
create database testing_system_assignment_1;
use testing_system_assignment_1;

-- Table 1:Department  
-- DepartmentID:  định danh của phòng ban (auto increment) 
-- DepartmentName: tên đầy đủ của phòng ban (VD: sale, marketing, …) 
drop table if exists department;
create table department(
	DepartmentID tinyint auto_increment unique primary key,
    DepartmentName varchar(50) unique
);

-- Table 2: Position  
-- PositionID:  định danh của chức vụ (auto increment) 
-- PositionName: tên chức vụ (Dev, Test, Scrum Master, PM) 
drop table if exists position_table;
create table position_table(
	PositionID tinyint auto_increment unique primary key,
    PositionName enum("Dev", "Test", "Scrum Master", "PM")
);

-- Table 3: Account  
-- AccountID:  định danh của User (auto increment) 
-- Email:  Địa chỉ email
-- Username:  tên đăng nhập
-- FullName:  tên đầy đủ
-- DepartmentID: phòng ban của user trong hệ thống 
-- PositionID: chức vụ của User 
-- CreateDate: ngày tạo tài khoản 
drop table if exists account_table;
create table account_table(
	AccountID tinyint auto_increment unique primary key,
    Email varchar(30) unique,
    Username varchar(15) unique,
    FullName varchar(30),
    DepartmentID tinyint,
    PositionID tinyint,
    CreateDate date,
    constraint fk_department foreign key (DepartmentID) references department(DepartmentID),
    constraint fk_position_table foreign key (PositionID) references position_table(PositionID)
);

-- Table 4: Group  
-- GroupID:  định danh của nhóm (auto increment) 
-- GroupName:  tên nhóm 
-- CreatorID: id của người tạo group 
-- CreateDate: ngày tạo group 
drop table if exists group_table;
create table group_table(
	GroupID tinyint auto_increment unique primary key,
    GroupName varchar(15) unique,
    CreatorID tinyint,
    CreateDate date,
    constraint fk_account_table foreign key (CreatorID) references account_table(AccountID)
);

-- Table 5: GroupAccount  
-- GroupID:  định danh của nhóm 
-- AccountID:  định danh của User 
-- JoinDate: Ngày user tham gia vào nhóm 
drop table if exists group_account;
create table group_account(
	GroupID tinyint,
    AccountID tinyint,
    JoinDate date,
    constraint fk_group_table foreign key (GroupID) references group_table(GroupID),
    constraint fk_account foreign key (AccountID) references account_table(AccountID),
    constraint pk_group_account primary key (GroupID, AccountID)
);

-- Table 6: TypeQuestion  
-- TypeID:  định danh của loại câu hỏi (auto increment) 
-- TypeName:  tên của loại câu hỏi (Essay, Multiple-Choice) 
drop table if exists type_question;
create table type_question(
	TypeID tinyint auto_increment unique primary key,
    TypeName enum("Essay", "Multiple-Choice") unique
);

-- Table 7: CategoryQuestion  
-- CategoryID:  định danh của chủ đề câu hỏi (auto increment) 
-- CategoryName:  tên của chủ đề câu hỏi (Java, .NET, SQL, Postman, Ruby, …) 
drop table if exists category_question;
create table category_question(
	CategoryID tinyint auto_increment unique primary key,
    CategoryName varchar(15) unique
);

-- Table 8: Question  
-- QuestionID:  định danh của câu hỏi (auto increment) 
-- Content:  nội dung của câu hỏi 
-- CategoryID:  định danh của chủ đề câu hỏi 
-- TypeID:  định danh của loại câu hỏi 
-- CreatorID: id của người tạo câu hỏi 
-- CreateDate: ngày tạo câu hỏi 
drop table if exists question;
create table question(
	QuestionID int auto_increment unique primary key,
    Content text,
    CategoryID tinyint,
    TypeID tinyint,
    CreatorID tinyint,
    CreateDate date,
    constraint fk_type_question foreign key (TypeID) references type_question(TypeID),
    constraint fk_category_question foreign key (CategoryID) references category_question(CategoryID),
    constraint fk_account_table_question foreign key (CreatorID) references account_table(AccountID)
);

-- Table 9: Answer  
-- AnswerID:  định danh của câu trả lời (auto increment) 
-- Content:  nội dung của câu trả lời 
-- QuestionID:  định danh của câu hỏi  
-- isCorrect: câu trả lời này đúng hay sai 
drop table if exists answer;
create table answer(
	AnswerID int auto_increment unique primary key,
    Content text,
    QuestionID int,
    isCorrect boolean,
    constraint fk_question foreign key (QuestionID) references question(QuestionID)
);

-- Table 10: Exam  
-- ExamID:  định danh của đề thi (auto increment) 
-- Code: mã đề thi 
-- Title: tiêu đề của đề thi 
-- CategoryID:  định danh của chủ đề thi 
-- Duration: thời gian thi 
-- CreatorID: id của người tạo đề thi 
-- CreateDate: ngày tạo đề thi 
drop table if exists exam;
create table exam(
	ExamID int auto_increment unique primary key,
    Code_name varchar(15) unique,
    Title varchar(100),
    CategoryID tinyint,
    Duration time,
    CreatorID tinyint,
    CreateDate date,
    constraint fk_category_question_exam foreign key (CategoryID) references category_question(CategoryID),
    constraint fk_account_table_exam foreign key (CreatorID) references account_table(AccountID)
);

-- Table 11: ExamQuestion  
-- ExamID:  định danh của đề thi 
-- QuestionID:  định danh của câu hỏi  
drop table if exists exam_question;
create table exam_question(
	ExamID int,
    QuestionID int,
    foreign key (ExamID) references exam(ExamID),
    foreign key (QuestionID) references question(QuestionID),
    constraint pk_exam_question primary key (ExamID, QuestionID)
);

-- ---------------------------------------------------------
-- Question 1: Thêm ít nhất 10 record vào mỗi table
INSERT INTO department (DepartmentName) VALUES
    ('Sales'), 
    ('Marketing'), 
    ('IT'), 
    ('Human Resources'), 
    ('Finance'),
    ('Operations'), 
    ('Research & Development'), 
    ('Customer Service'), 
    ('Legal'), 
    ('Administration');

INSERT INTO position_table (PositionName) VALUES
    ('Dev'), 
    ('Test'), 
    ('Scrum Master'), 
    ('PM');

INSERT INTO account_table (Email, Username, FullName, DepartmentID, PositionID, CreateDate) VALUES
    ('nguyenvana@gmail.com', 'vana', 'Nguyen Van A', 1, 1, '2023-01-01'),
    ('tranthib@gmail.com', 'thib', 'Tran Thi B', 2, 2, '2023-01-02'),
    ('levanc@gmail.com', 'vanc', 'Le Van C', 3, 3, '2023-01-03'),
    ('phamthid@gmail.com', 'thid', 'Pham Thi D', 4, 4, '2023-01-04'),
    ('hoangvane@gmail.com', 'vane', 'Hoang Van E', 5, 1, '2023-01-05'),
    ('vuthif@gmail.com', 'thif', 'Vu Thi F', 6, 2, '2023-01-06'),
    ('dovang@gmail.com', 'vang', 'Do Van G', 7, 3, '2023-01-07'),
    ('buithih@gmail.com', 'thih', 'Bui Thi H', 8, 4, '2023-01-08'),
    ('dangvani@gmail.com', 'vani', 'Dang Van I', 9, 1, '2023-01-09'),
    ('ngothik@gmail.com', 'thik', 'Ngo Thi K', 10, 2, '2023-01-10');

INSERT INTO group_table (GroupName, CreatorID, CreateDate) VALUES
    ('Group Alpha', 1, '2023-02-01'),
    ('Group Beta', 2, '2023-02-02'),
    ('Group Gamma', 3, '2023-02-03'),
    ('Group Delta', 4, '2023-02-04'),
    ('Group Epsilon', 5, '2023-02-05'),
    ('Group Zeta', 6, '2023-02-06'),
    ('Group Eta', 7, '2023-02-07'),
    ('Group Theta', 8, '2023-02-08'),
    ('Group Iota', 9, '2023-02-09'),
    ('Group Kappa', 10, '2023-02-10');

INSERT INTO group_account (GroupID, AccountID, JoinDate) VALUES
    (1, 1, '2023-03-01'),
    (1, 2, '2023-03-02'),
    (2, 3, '2023-03-03'),
    (3, 4, '2023-03-04'),
    (4, 5, '2023-03-05'),
    (5, 6, '2023-03-06'),
    (6, 7, '2023-03-07'),
    (7, 8, '2023-03-08'),
    (8, 9, '2023-03-09'),
    (9, 10, '2023-03-10');

INSERT INTO type_question (TypeName) VALUES
    ('Essay'), 
    ('Multiple-Choice');

INSERT INTO category_question (CategoryName) VALUES
    ('Java'), 
    ('.NET'), 
    ('SQL'), 
    ('Postman'), 
    ('Ruby'),
    ('Python'), 
    ('C++'), 
    ('ReactJS'), 
    ('NodeJS'), 
    ('HTML_CSS');

INSERT INTO question (Content, CategoryID, TypeID, CreatorID, CreateDate) VALUES
    ('Question about OOP in Java', 1, 1, 1, '2023-04-01'),
    ('Question about Entity Framework', 2, 2, 2, '2023-04-02'),
    ('How to write a JOIN query in SQL', 3, 1, 3, '2023-04-03'),
    ('How to pass Bearer Token in Postman', 4, 2, 4, '2023-04-04'),
    ('Explain MVC in Ruby on Rails', 5, 1, 5, '2023-04-05'),
    ('What are lists and tuples in Python', 6, 2, 6, '2023-04-06'),
    ('Explain pointers in C++', 7, 1, 7, '2023-04-07'),
    ('What are hooks in ReactJS', 8, 2, 8, '2023-04-08'),
    ('How does event loop work in NodeJS', 9, 1, 9, '2023-04-09'),
    ('Difference between ID and Class in CSS', 10, 2, 10, '2023-04-10');

INSERT INTO answer (Content, QuestionID, isCorrect) VALUES
    ('OOP means Object-Oriented Programming', 1, TRUE),
    ('OOP means Object-Oriented Processing', 1, FALSE),
    ('Entity Framework is an ORM in .NET', 2, TRUE),
    ('Use SELECT * JOIN table_name', 3, FALSE),
    ('Set in Authorization tab', 4, TRUE),
    ('Model View Controller', 5, TRUE),
    ('Lists are immutable in Python', 6, FALSE),
    ('Tuples are immutable in Python', 6, TRUE),
    ('Hooks allow state inside functional components', 8, TRUE),
    ('Class uses # and ID uses . in CSS', 10, FALSE);

INSERT INTO exam (Code_name, Title, CategoryID, Duration, CreatorID, CreateDate) VALUES
    ('EXAM01', 'Java Basic Test', 1, '00:45:00', 1, '2023-05-01'),
    ('EXAM02', 'C# .NET Midterm', 2, '01:00:00', 2, '2023-05-02'),
    ('EXAM03', 'SQL Advanced Exam', 3, '01:30:00', 3, '2023-05-03'),
    ('EXAM04', 'Postman Testing Basics', 4, '00:30:00', 4, '2023-05-04'),
    ('EXAM05', 'Ruby on Rails Final', 5, '01:00:00', 5, '2023-05-05'),
    ('EXAM06', 'Python for AI', 6, '01:30:00', 6, '2023-05-06'),
    ('EXAM07', 'C++ Mastery', 7, '02:00:00', 7, '2023-05-07'),
    ('EXAM08', 'ReactJS Frontend Quiz', 8, '01:00:00', 8, '2023-05-08'),
    ('EXAM09', 'NodeJS Backend Quiz', 9, '01:00:00', 9, '2023-05-09'),
    ('EXAM10', 'HTML CSS Essentials', 10, '00:45:00', 10, '2023-05-10');

INSERT INTO exam_question (ExamID, QuestionID) VALUES
    (1, 1),
    (2, 2),
    (3, 3),
    (4, 4),
    (5, 5),
    (6, 6),
    (7, 7),
    (8, 8),
    (9, 9),
    (10, 10);

-- Question 2: Lấy ra tất cả các phòng ban 
SELECT * FROM department;

-- Question 3: Lấy ra id của phòng ban "Sale" 
SELECT DepartmentID FROM department WHERE DepartmentName = 'Sale';

-- Question 4: Lấy ra thông tin account có full name dài nhất 
SELECT * FROM account_table 
WHERE LENGTH(FullName) = (SELECT MAX(LENGTH(FullName)) FROM account_table);

-- Question 5: Lấy ra thông tin account có full name dài nhất và thuộc phòng ban có id = 3 
SELECT * FROM account_table 
WHERE DepartmentID = 3 
AND LENGTH(FullName) = (SELECT MAX(LENGTH(FullName)) FROM account_table WHERE DepartmentID = 3);

-- Question 6: Lấy ra tên group đã tham gia trước ngày 20/12/2019 
SELECT g.GroupName 
FROM group_table g
JOIN group_account ga ON g.GroupID = ga.GroupID
WHERE ga.JoinDate < '2019-12-20';

-- Question 7: Lấy ra ID của question có >= 4 câu trả lời 
SELECT QuestionID 
FROM answer 
GROUP BY QuestionID 
HAVING COUNT(AnswerID) >= 4;

-- Question 8: Lấy ra các mã đề thi có thời gian thi >= 60 phút và được tạo trước ngày 20/12/2019 
SELECT Code_name 
FROM exam 
WHERE Duration >= '01:00:00' AND CreateDate < '2019-12-20';

-- Question 9: Lấy ra 5 group được tạo gần đây nhất 
SELECT * FROM group_table 
ORDER BY CreateDate DESC 
LIMIT 5;

-- Question 10: Đếm số nhân viên thuộc department có id = 2 
SELECT COUNT(AccountID) 
FROM account_table 
WHERE DepartmentID = 2;

-- Question 11: Lấy ra nhân viên có tên bắt đầu bằng chữ "D" và kết thúc bằng chữ "o" 
SELECT * FROM account_table 
WHERE FullName LIKE 'D%o';

-- Question 12: Xóa tất cả các exam được tạo trước ngày 20/12/2019  
DELETE FROM exam_question WHERE ExamID IN (SELECT ExamID FROM exam WHERE CreateDate < '2019-12-20');
DELETE FROM exam WHERE CreateDate < '2019-12-20';

-- Question 13: Xóa tất cả các question có nội dung bắt đầu bằng từ "câu hỏi" 
DELETE FROM exam_question WHERE QuestionID IN (SELECT QuestionID FROM question WHERE Content LIKE 'câu hỏi%');
DELETE FROM answer WHERE QuestionID IN (SELECT QuestionID FROM question WHERE Content LIKE 'câu hỏi%');
DELETE FROM question WHERE Content LIKE 'câu hỏi%';

-- Question 14: Update thông tin của account có id = 5 thành tên "Nguyễn Bá Lộc" và email thành loc.nguyenba@vti.com.vn 
UPDATE account_table 
SET FullName = 'Nguyễn Bá Lộc', Email = 'loc.nguyenba@vti.com.vn' 
WHERE AccountID = 5;

-- Question 15: update account có id = 5 sẽ thuộc group có id = 4 
UPDATE group_account 
SET GroupID = 4 
WHERE AccountID = 5;




