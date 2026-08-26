use testing_system_assignment_1;

-- Question 1: Viết lệnh để lấy ra danh sách nhân viên và thông tin phòng ban của họ
SELECT 
    a.account_id,
    a.email,
    a.user_name,
    a.full_name,
    d.department_id,
    d.department_name
FROM account_table a
LEFT JOIN department d ON a.department_id = d.department_id;


-- Question 2: Viết lệnh để lấy ra thông tin các account được tạo sau ngày 20/12/2010
SELECT * 
FROM account_table 
WHERE create_date > '2010-12-20';


-- Question 3: Viết lệnh để lấy ra tất cả các developer
SELECT 
    a.account_id,
    a.email,
    a.user_name,
    a.full_name,
    p.position_name
FROM account_table a
JOIN position_table p ON a.position_id = p.position_id
WHERE p.position_name = 'Dev';


-- Question 4: Viết lệnh để lấy ra danh sách các phòng ban có > 3 nhân viên
SELECT 
    d.department_id,
    d.department_name,
    COUNT(a.account_id) AS total_accounts
FROM department d
JOIN account_table a ON d.department_id = a.department_id
GROUP BY d.department_id, d.department_name
HAVING COUNT(a.account_id) > 3;


-- Question 5: Viết lệnh để lấy ra danh sách câu hỏi được sử dụng trong đề thi nhiều nhất
SELECT 
    q.question_id,
    q.content,
    COUNT(eq.exam_id) AS total_exams
FROM question q
JOIN exam_question eq ON q.question_id = eq.question_id
GROUP BY q.question_id, q.content
HAVING COUNT(eq.exam_id) = (
    SELECT MAX(exam_count)
    FROM (
        SELECT COUNT(exam_id) AS exam_count
        FROM exam_question
        GROUP BY question_id
    ) AS temp
);


-- Question 6: Thống kê mỗi Category Question được sử dụng trong bao nhiêu Question
SELECT 
    c.category_id,
    c.category_name,
    COUNT(q.question_id) AS total_questions
FROM category_question c
LEFT JOIN question q ON c.category_id = q.category_id
GROUP BY c.category_id, c.category_name;


-- Question 7: Thống kê mỗi Question được sử dụng trong bao nhiêu Exam
SELECT 
    q.question_id,
    q.content,
    COUNT(eq.exam_id) AS total_exams
FROM question q
LEFT JOIN exam_question eq ON q.question_id = eq.question_id
GROUP BY q.question_id, q.content;


-- Question 8: Lấy ra Question có nhiều câu trả lời nhất
SELECT 
    q.question_id,
    q.content,
    COUNT(ans.answer_id) AS total_answers
FROM question q
JOIN answer ans ON q.question_id = ans.question_id
GROUP BY q.question_id, q.content
HAVING COUNT(ans.answer_id) = (
    SELECT MAX(answer_count)
    FROM (
        SELECT COUNT(answer_id) AS answer_count
        FROM answer
        GROUP BY question_id
    ) AS temp
);


-- Question 9: Thống kê số lượng account trong mỗi group
SELECT 
    g.group_id,
    g.group_name,
    COUNT(ga.account_id) AS total_accounts
FROM group_table g
LEFT JOIN group_account ga ON g.group_id = ga.group_id
GROUP BY g.group_id, g.group_name;


-- Question 10: Tìm chức vụ có ít người nhất
SELECT 
    p.position_id,
    p.position_name,
    COUNT(a.account_id) AS total_accounts
FROM position_table p
LEFT JOIN account_table a ON p.position_id = a.position_id
GROUP BY p.position_id, p.position_name
HAVING COUNT(a.account_id) = (
    SELECT MIN(account_count)
    FROM (
        SELECT COUNT(account_id) AS account_count
        FROM position_table p2
        LEFT JOIN account_table a2 ON p2.position_id = a2.position_id
        GROUP BY p2.position_id
    ) AS temp
);


-- Question 11: Thống kê mỗi phòng ban có bao nhiêu dev, test, scrum master, PM
SELECT 
    d.department_id,
    d.department_name,
    p.position_name,
    COUNT(a.account_id) AS total_members
FROM department d
CROSS JOIN position_table p
LEFT JOIN account_table a ON d.department_id = a.department_id AND p.position_id = a.position_id
GROUP BY d.department_id, d.department_name, p.position_name, p.position_id
ORDER BY d.department_id, p.position_id;

-- Question 12: Lấy thông tin chi tiết của câu hỏi bao gồm: thông tin cơ bản của question, loại câu hỏi, ai là người tạo ra câu hỏi, câu trả lời là gì
SELECT 
    q.question_id,
    q.content AS question_content,
    tq.type_name,
    a.full_name AS creator_name,
    ans.content AS answer_content,
    ans.is_correct
FROM question q
LEFT JOIN type_question tq ON q.type_id = tq.type_id
LEFT JOIN account_table a ON q.creator_id = a.account_id
LEFT JOIN answer ans ON q.question_id = ans.question_id;


-- Question 13: Lấy ra số lượng câu hỏi của mỗi loại tự luận hay trắc nghiệm
SELECT 
    tq.type_id,
    tq.type_name,
    COUNT(q.question_id) AS total_questions
FROM type_question tq
LEFT JOIN question q ON tq.type_id = q.type_id
GROUP BY tq.type_id, tq.type_name;


-- Question 14: Lấy ra group không có account nào
SELECT 
    g.group_id,
    g.group_name
FROM group_table g
LEFT JOIN group_account ga ON g.group_id = ga.group_id
WHERE ga.account_id IS NULL;
-- left excluding join


-- Question 15: Lấy ra group không có account nào
SELECT 
    g.group_id,
    g.group_name
FROM group_table g
WHERE g.group_id NOT IN (SELECT DISTINCT group_id FROM group_account);


-- Question 16: Lấy ra question không có answer nào
SELECT 
    q.question_id,
    q.content
FROM question q
LEFT JOIN answer ans ON q.question_id = ans.question_id
WHERE ans.answer_id IS NULL;

-- Question 17:
-- a) Lấy các account thuộc nhóm thứ 1
-- b) Lấy các account thuộc nhóm thứ 2
-- c) Ghép 2 kết quả từ câu a) và câu b) sao cho không có record nào trùng nhau (UNION)
SELECT 
    a.account_id, 
    a.full_name, 
    a.email
FROM account_table a
JOIN group_account ga ON a.account_id = ga.account_id
WHERE ga.group_id = 1

UNION

SELECT 
    a.account_id, 
    a.full_name, 
    a.email
FROM account_table a
JOIN group_account ga ON a.account_id = ga.account_id
WHERE ga.group_id = 2;


-- Question 18:
-- a) Lấy các group có lớn hơn 5 thành viên
-- b) Lấy các group có nhỏ hơn 7 thành viên
-- c) Ghép 2 kết quả từ câu a) và câu b) (UNION)
SELECT 
    g.group_id, 
    g.group_name, 
    COUNT(ga.account_id) AS total_members
FROM group_table g
JOIN group_account ga ON g.group_id = ga.group_id
GROUP BY g.group_id, g.group_name
HAVING COUNT(ga.account_id) > 5

UNION

SELECT 
    g.group_id, 
    g.group_name, 
    COUNT(ga.account_id) AS total_members
FROM group_table g
JOIN group_account ga ON g.group_id = ga.group_id
GROUP BY g.group_id, g.group_name
HAVING COUNT(ga.account_id) < 7;