DROP DATABASE IF EXISTS testing_system_assignment_1;
CREATE DATABASE testing_system_assignment_1;
USE testing_system_assignment_1;

-- Table 1: Department
DROP TABLE IF EXISTS department;
CREATE TABLE department(
    department_id TINYINT AUTO_INCREMENT UNIQUE PRIMARY KEY,
    department_name VARCHAR(50) UNIQUE
);

-- Table 2: Position
DROP TABLE IF EXISTS position_table;
CREATE TABLE position_table(
    position_id TINYINT AUTO_INCREMENT UNIQUE PRIMARY KEY,
    position_name ENUM('Dev', 'Test', 'Scrum Master', 'PM')
);

-- Table 3: Account
DROP TABLE IF EXISTS account_table;
CREATE TABLE account_table(
    account_id TINYINT AUTO_INCREMENT UNIQUE PRIMARY KEY,
    email VARCHAR(30) UNIQUE,
    username VARCHAR(15) UNIQUE,
    full_name VARCHAR(30),
    department_id TINYINT,
    position_id TINYINT,
    create_date DATE,
    CONSTRAINT fk_department FOREIGN KEY (department_id) REFERENCES department(department_id),
    CONSTRAINT fk_position_table FOREIGN KEY (position_id) REFERENCES position_table(position_id)
);

-- Table 4: Group
DROP TABLE IF EXISTS group_table;
CREATE TABLE group_table(
    group_id TINYINT AUTO_INCREMENT UNIQUE PRIMARY KEY,
    group_name VARCHAR(15) UNIQUE,
    creator_id TINYINT,
    create_date DATE,
    CONSTRAINT fk_account_table FOREIGN KEY (creator_id) REFERENCES account_table(account_id)
);

-- Table 5: GroupAccount
DROP TABLE IF EXISTS group_account;
CREATE TABLE group_account(
    group_id TINYINT,
    account_id TINYINT,
    join_date DATE,
    CONSTRAINT fk_group_table FOREIGN KEY (group_id) REFERENCES group_table(group_id),
    CONSTRAINT fk_account FOREIGN KEY (account_id) REFERENCES account_table(account_id),
    CONSTRAINT pk_group_account PRIMARY KEY (group_id, account_id)
);

-- Table 6: TypeQuestion
DROP TABLE IF EXISTS type_question;
CREATE TABLE type_question(
    type_id TINYINT AUTO_INCREMENT UNIQUE PRIMARY KEY,
    type_name ENUM('Essay', 'Multiple-Choice') UNIQUE
);

-- Table 7: CategoryQuestion
DROP TABLE IF EXISTS category_question;
CREATE TABLE category_question(
    category_id TINYINT AUTO_INCREMENT UNIQUE PRIMARY KEY,
    category_name VARCHAR(15) UNIQUE
);

-- Table 8: Question
DROP TABLE IF EXISTS question;
CREATE TABLE question(
    question_id INT AUTO_INCREMENT UNIQUE PRIMARY KEY,
    content TEXT,
    category_id TINYINT,
    type_id TINYINT,
    creator_id TINYINT,
    create_date DATE,
    CONSTRAINT fk_type_question FOREIGN KEY (type_id) REFERENCES type_question(type_id),
    CONSTRAINT fk_category_question FOREIGN KEY (category_id) REFERENCES category_question(category_id),
    CONSTRAINT fk_account_table_question FOREIGN KEY (creator_id) REFERENCES account_table(account_id)
);

-- Table 9: Answer
DROP TABLE IF EXISTS answer;
CREATE TABLE answer(
    answer_id INT AUTO_INCREMENT UNIQUE PRIMARY KEY,
    content TEXT,
    question_id INT,
    is_correct BOOLEAN,
    CONSTRAINT fk_question FOREIGN KEY (question_id) REFERENCES question(question_id)
);

-- Table 10: Exam
DROP TABLE IF EXISTS exam;
CREATE TABLE exam(
    exam_id INT AUTO_INCREMENT UNIQUE PRIMARY KEY,
    code_name VARCHAR(15) UNIQUE,
    title VARCHAR(100),
    category_id TINYINT,
    duration TIME,
    creator_id TINYINT,
    create_date DATE,
    CONSTRAINT fk_category_question_exam FOREIGN KEY (category_id) REFERENCES category_question(category_id),
    CONSTRAINT fk_account_table_exam FOREIGN KEY (creator_id) REFERENCES account_table(account_id)
);

-- Table 11: ExamQuestion
DROP TABLE IF EXISTS exam_question;
CREATE TABLE exam_question(
    exam_id INT,
    question_id INT,
    FOREIGN KEY (exam_id) REFERENCES exam(exam_id),
    FOREIGN KEY (question_id) REFERENCES question(question_id),
    CONSTRAINT pk_exam_question PRIMARY KEY (exam_id, question_id)
);

-- ---------------------------------------------------------
-- Question 1: Thêm ít nhất 10 record vào mỗi table

INSERT INTO department (department_name) VALUES
    ('Sale'), 
    ('Marketing'), 
    ('IT'), 
    ('Human Resources'), 
    ('Finance'),
    ('Operations'), 
    ('Research & Development'), 
    ('Customer Service'), 
    ('Legal'), 
    ('Administration');

INSERT INTO position_table (position_name) VALUES
    ('Dev'), 
    ('Test'), 
    ('Scrum Master'), 
    ('PM');

INSERT INTO account_table (email, username, full_name, department_id, position_id, create_date) VALUES
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

INSERT INTO group_table (group_name, creator_id, create_date) VALUES
    ('Group A', 1, '2023-02-01'),
    ('Group B', 2, '2023-02-02'),
    ('Group C', 3, '2023-02-03'),
    ('Group D', 4, '2023-02-04'),
    ('Group E', 5, '2023-02-05'),
    ('Group F', 6, '2023-02-06'),
    ('Group G', 7, '2023-02-07'),
    ('Group H', 8, '2023-02-08'),
    ('Group I', 9, '2023-02-09'),
    ('Group K', 10, '2023-02-10');

INSERT INTO group_account (group_id, account_id, join_date) VALUES
    (1, 1, '2023-03-01'),
    (1, 2, '2019-12-10'),
    (2, 3, '2023-03-03'),
    (3, 4, '2023-03-04'),
    (4, 5, '2023-03-05'),
    (5, 6, '2023-03-06'),
    (6, 7, '2019-01-18'),
    (7, 8, '2023-03-08'),
    (8, 9, '2023-03-09'),
    (9, 10, '2023-03-10');

INSERT INTO type_question (type_name) VALUES
    ('Essay'), 
    ('Multiple-Choice');

INSERT INTO category_question (category_name) VALUES
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

INSERT INTO question (content, category_id, type_id, creator_id, create_date) VALUES
    ('Câu hỏi về OOP', 1, 1, 1, '2023-04-01'),
    ('Câu hỏi về Entity Framework', 2, 2, 2, '2023-04-02'),
    ('Cách viết câu truy vấn JOIN trong SQL', 3, 1, 3, '2023-04-03'),
    ('Cách truyền Bearer Token trong Postman', 4, 2, 4, '2023-04-04'),
    ('Giải thích mô hình MVC trong Ruby on Rails', 5, 1, 5, '2023-04-05'),
    ('List và tuple trong Python là gì', 6, 2, 6, '2023-04-06'),
    ('Giải thích về con trỏ trong C++', 7, 1, 7, '2023-04-07'),
    ('Hooks trong ReactJS là gì', 8, 2, 8, '2023-04-08'),
    ('Event loop hoạt động như thế nào trong NodeJS', 9, 1, 9, '2023-04-09'),
    ('Sự khác biệt giữa ID và Class trong CSS', 10, 2, 10, '2023-04-10');

INSERT INTO answer (content, question_id, is_correct) VALUES
    ('OOP có nghĩa là Lập trình hướng đối tượng', 1, TRUE),
    ('OOP có nghĩa là Xử lý hướng đối tượng', 1, FALSE),
    ('Entity Framework là một ORM trong .NET', 2, TRUE),
    ('Sử dụng SELECT * JOIN table_name', 3, FALSE),
    ('Thiết lập trong thẻ Authorization', 4, TRUE),
    ('Model View Controller', 5, TRUE),
    ('List là kiểu dữ liệu không thể thay đổi (immutable) trong Python', 6, FALSE),
    ('Tuple là kiểu dữ liệu không thể thay đổi (immutable) trong Python', 6, TRUE),
    ('Hooks cho phép sử dụng state bên trong functional components', 8, TRUE),
    ('Class sử dụng # và ID sử dụng . trong CSS', 10, FALSE);

INSERT INTO exam (code_name, title, category_id, duration, creator_id, create_date) VALUES
    ('EXAM01', 'Java Basic Test', 1, '00:45:00', 1, '2019-10-01'),
    ('EXAM02', 'C# .NET Midterm', 2, '01:00:00', 2, '2019-11-01'),
    ('EXAM03', 'SQL Advanced Exam', 3, '01:30:00', 3, '2020-01-01'),
    ('EXAM04', 'Postman Testing Basics', 4, '00:30:00', 4, '2020-02-01'),
    ('EXAM05', 'Ruby on Rails Final', 5, '01:00:00', 5, '2020-03-01'),
    ('EXAM06', 'Python for AI', 6, '01:30:00', 6, '2020-04-01'),
    ('EXAM07', 'C++ Mastery', 7, '02:00:00', 7, '2020-05-01'),
    ('EXAM08', 'ReactJS Frontend Quiz', 8, '01:00:00', 8, '2020-06-01'),
    ('EXAM09', 'NodeJS Backend Quiz', 9, '01:00:00', 9, '2020-07-01'),
    ('EXAM10', 'HTML CSS Essentials', 10, '00:45:00', 10, '2020-08-01');

INSERT INTO exam_question (exam_id, question_id) VALUES
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
SELECT department_id FROM department WHERE department_name = 'Sale';

-- Question 4: Lấy ra thông tin account có full name dài nhất
SELECT * FROM account_table 
WHERE LENGTH(full_name) = (SELECT MAX(LENGTH(full_name)) FROM account_table);

-- Question 5: Lấy ra thông tin account có full name dài nhất và thuộc phòng ban có id = 3
SELECT * FROM account_table 
WHERE department_id = 3 
AND LENGTH(full_name) = (SELECT MAX(LENGTH(full_name)) FROM account_table WHERE department_id = 3);

-- Question 6: Lấy ra tên group đã tham gia trước ngày 20/12/2019
SELECT g.group_name 
FROM group_table g
JOIN group_account ga ON g.group_id = ga.group_id
WHERE ga.join_date < '2019-12-20';

-- Question 7: Lấy ra ID của question có >= 4 câu trả lời
SELECT question_id 
FROM answer 
GROUP BY question_id 
HAVING COUNT(answer_id) >= 4;

-- Question 8: Lấy ra các mã đề thi có thời gian thi >= 60 phút và được tạo trước ngày 20/12/2019
SELECT code_name 
FROM exam 
WHERE duration >= '01:00:00' AND create_date < '2019-12-20';

-- Question 9: Lấy ra 5 group được tạo gần đây nhất
SELECT * FROM group_table 
ORDER BY create_date DESC 
LIMIT 5;

-- Question 10: Đếm số nhân viên thuộc department có id = 2
SELECT COUNT(account_id) 
FROM account_table 
WHERE department_id = 2;

-- Question 11: Lấy ra nhân viên có tên bắt đầu bằng chữ "D" và kết thúc bằng chữ "o"
SELECT * FROM account_table 
WHERE full_name LIKE 'D%o';

-- Question 12: Xóa tất cả các exam được tạo trước ngày 20/12/2019
DELETE FROM exam_question WHERE exam_id IN (SELECT exam_id FROM (SELECT * FROM exam) AS e WHERE create_date < '2019-12-20');
DELETE FROM exam WHERE create_date < '2019-12-20';

-- Question 13: Xóa tất cả các question có nội dung bắt đầu bằng từ "câu hỏi"
DELETE FROM exam_question WHERE question_id IN (SELECT question_id FROM (SELECT * FROM question) AS q WHERE content LIKE 'câu hỏi%');
DELETE FROM answer WHERE question_id IN (SELECT question_id FROM (SELECT * FROM question) AS q WHERE content LIKE 'câu hỏi%');
DELETE FROM question WHERE content LIKE 'câu hỏi%';

-- Question 14: Update thông tin của account có id = 5 thành tên "Nguyễn Bá Lộc" và email thành loc.nguyenba@vti.com.vn
UPDATE account_table 
SET full_name = 'Nguyễn Bá Lộc', email = 'loc.nguyenba@vti.com.vn' 
WHERE account_id = 5;

-- Question 15: update account có id = 5 sẽ thuộc group có id = 4
UPDATE group_account 
SET group_id = 4 
WHERE account_id = 5;