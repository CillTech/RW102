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
    foreign key (DepartmentID) references department(DepartmentID),
    foreign key (PositionID) references position_table(PositionID)
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
    foreign key (CreatorID) references account_table(AccountID)
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
    foreign key (GroupID) references group_table(GroupID),
    foreign key (AccountID) references account_table(AccountID),
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
    foreign key (TypeID) references type_question(TypeID),
    foreign key (CategoryID) references category_question(CategoryID),
    foreign key (CreatorID) references account_table(AccountID)
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
    foreign key (QuestionID) references question(QuestionID)
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
    foreign key (CategoryID) references category_question(CategoryID),
    foreign key (CreatorID) references account_table(AccountID)
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






