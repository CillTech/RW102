USE testing_system_assignment_1;

-- Question 1: Tạo view có chứa danh sách nhân viên thuộc phòng ban sale
CREATE OR REPLACE VIEW v_sale_employees AS
SELECT a.*
FROM account_table a
JOIN department d ON a.department_id = d.department_id
WHERE d.department_name = 'Sale';


-- Question 2: Tạo view có chứa thông tin các account tham gia vào nhiều group nhất
CREATE OR REPLACE VIEW v_account_most_groups AS
WITH group_counts AS (
    SELECT account_id, COUNT(group_id) AS total_groups
    FROM group_account
    GROUP BY account_id
)
SELECT a.*, gc.total_groups
FROM account_table a
JOIN group_counts gc ON a.account_id = gc.account_id
WHERE gc.total_groups = (SELECT MAX(total_groups) FROM group_counts);


-- Question 3: Tạo view chứa câu hỏi có content quá 300 từ và xóa nó đi
-- ** Từ > 300
CREATE OR REPLACE VIEW v_long_questions AS
SELECT *
FROM question
WHERE (
    -- REGEXP_REPLACE(..., '[[:space:]]+', ' ') gộp nhiều khoảng trắng liền kề thành 1 khoảng trắng
    CHAR_LENGTH(TRIM(REGEXP_REPLACE(content, '[[:space:]]+', ' '))) 
    - CHAR_LENGTH(REPLACE(TRIM(REGEXP_REPLACE(content, '[[:space:]]+', ' ')), ' ', '')) 
    + 1) > 300
    -- (Lấy chuỗi đã chuẩn hóa - chuỗi đã chuẩn hóa và xóa khoảng trắng = số khoảng trắng, vì số từ = số khoảng trắng ở giữa + 1)
AND TRIM(content) != ''; -- Đảm bảo chuỗi không rỗng

-- ** Kí tự > 300
CREATE OR REPLACE VIEW v_long_questions AS
SELECT *
FROM question
WHERE CHAR_LENGTH(content) > 300;

DELETE FROM v_long_questions;

-- Question 4: Tạo view có chứa danh sách các phòng ban có nhiều nhân viên nhất
CREATE OR REPLACE VIEW v_department_most_employees AS
WITH dept_counts AS (
    SELECT department_id, COUNT(account_id) AS total_members
    FROM account_table
    GROUP BY department_id
)
SELECT d.*, dc.total_members
FROM department d
JOIN dept_counts dc ON d.department_id = dc.department_id
WHERE dc.total_members = (SELECT MAX(total_members) FROM dept_counts);


-- Question 5: Tạo view có chứa tất cả các câu hỏi do user họ Nguyễn tạo
CREATE OR REPLACE VIEW v_questions_by_nguyen AS
SELECT q.*, a.full_name
FROM question q
JOIN account_table a ON q.creator_id = a.account_id
WHERE a.full_name LIKE 'Nguyen %' OR a.full_name LIKE 'Nguyễn %';