USE testing_system_assignment_1;

-- QUESTION 1: Tạo store nhập vào tên phòng ban và in ra tất cả account thuộc phòng ban đó
DROP PROCEDURE IF EXISTS sp_get_accounts_by_department;
DELIMITER $$
CREATE PROCEDURE sp_get_accounts_by_department(IN in_department_name VARCHAR(50))
BEGIN
    SELECT 
        a.account_id,
        a.email,
        a.user_name,
        a.full_name,
        d.department_name,
        a.create_date
    FROM account_table a
    JOIN department d ON a.department_id = d.department_id
    WHERE d.department_name = in_department_name;
END$$
DELIMITER ;

-- QUESTION 2: Tạo store để in ra số lượng account trong mỗi group
DROP PROCEDURE IF EXISTS sp_count_accounts_in_groups;
DELIMITER $$
CREATE PROCEDURE sp_count_accounts_in_groups()
BEGIN
    SELECT 
        g.group_id,
        g.group_name,
        COUNT(ga.account_id) AS total_accounts
    FROM group_table g
    LEFT JOIN group_account ga ON g.group_id = ga.group_id
    GROUP BY g.group_id, g.group_name;
END$$
DELIMITER ;

-- QUESTION 3: Tạo store thống kê mỗi type question có bao nhiêu question được tạo trong tháng/năm hiện tại
DROP PROCEDURE IF EXISTS sp_count_questions_current_month;
DELIMITER $$
CREATE PROCEDURE sp_count_questions_current_month()
BEGIN
    SELECT 
        tq.type_id,
        tq.type_name,
        COUNT(q.question_id) AS total_questions
    FROM type_question tq
    LEFT JOIN question q ON tq.type_id = q.type_id 
        AND MONTH(q.create_date) = MONTH(CURRENT_DATE()) 
        AND YEAR(q.create_date) = YEAR(CURRENT_DATE())
    GROUP BY tq.type_id, tq.type_name;
END$$
DELIMITER ;

-- QUESTION 4: Tạo store trả ra ID của type question có nhiều câu hỏi nhất (dùng OUT param)
DROP PROCEDURE IF EXISTS sp_get_type_id_most_questions;
DELIMITER $$
CREATE PROCEDURE sp_get_type_id_most_questions(OUT out_type_id TINYINT)
BEGIN
    WITH type_counts AS (
        SELECT type_id, COUNT(question_id) AS total_questions
        FROM question
        GROUP BY type_id
    )
    SELECT type_id INTO out_type_id
    FROM type_counts
    WHERE total_questions = (SELECT MAX(total_questions) FROM type_counts)
    LIMIT 1;
END$$
DELIMITER ;

-- QUESTION 5: Sử dụng store ở Question 4 để tìm ra tên của type question
DROP PROCEDURE IF EXISTS sp_get_type_name_most_questions;
DELIMITER $$
CREATE PROCEDURE sp_get_type_name_most_questions()
BEGIN
    DECLARE v_type_id TINYINT;
    CALL sp_get_type_id_most_questions(v_type_id);
    SELECT type_id, type_name 
    FROM type_question 
    WHERE type_id = v_type_id;
END$$
DELIMITER ;

-- QUESTION 6: Nhập vào chuỗi -> Tìm group có tên chứa chuỗi HOẶC user có username chứa chuỗi
DROP PROCEDURE IF EXISTS sp_search_group_or_user;
DELIMITER $$
CREATE PROCEDURE sp_search_group_or_user(IN in_search_str VARCHAR(50))
BEGIN
    SELECT group_id AS id, group_name AS name, 'Group' AS type 
    FROM group_table 
    WHERE group_name LIKE CONCAT('%', in_search_str, '%')
    
    UNION ALL
    
    SELECT account_id AS id, user_name AS name, 'User' AS type 
    FROM account_table 
    WHERE user_name LIKE CONCAT('%', in_search_str, '%');
END$$
DELIMITER ;

-- QUESTION 7: Nhập full_name, email -> Auto gán username, positionID = Dev, departmentID = Phòng chờ
DROP PROCEDURE IF EXISTS sp_insert_account_auto_fields;
DELIMITER $$
CREATE PROCEDURE sp_insert_account_auto_fields(
    IN in_full_name VARCHAR(50),
    IN in_email VARCHAR(50)
)
BEGIN
    DECLARE v_user_name VARCHAR(50);
    DECLARE v_position_id TINYINT;
    DECLARE v_department_id TINYINT;

    -- Cắt lấy chuỗi trước ký tự @
    SET v_user_name = SUBSTRING_INDEX(in_email, '@', 1);

    -- Lấy ID của vị trí 'Dev'
    SELECT position_id INTO v_position_id 
    FROM position_table 
    WHERE position_name = 'Dev' 
    LIMIT 1;

    -- Tìm hoặc tự động tạo phòng 'Phòng chờ' nếu chưa có
    SELECT department_id INTO v_department_id 
    FROM department 
    WHERE department_name = 'Phòng chờ' 
    LIMIT 1;

    IF v_department_id IS NULL THEN
        INSERT INTO department (department_name) VALUES ('Phòng chờ');
        SET v_department_id = LAST_INSERT_ID();
    END IF;

    -- Thêm mới bản ghi vào bảng account
    INSERT INTO account_table (email, user_name, full_name, department_id, position_id, create_date)
    VALUES (in_email, v_user_name, in_full_name, v_department_id, v_position_id, CURRENT_DATE());

    SELECT * 
    FROM account_table 
    WHERE account_id = LAST_INSERT_ID();
END$$
DELIMITER ;

-- QUESTION 8: Nhập 'Essay' hoặc 'Multiple-Choice' -> Thống kê câu hỏi loại đó có content dài nhất
DROP PROCEDURE IF EXISTS sp_get_longest_question_by_type;
DELIMITER $$
CREATE PROCEDURE sp_get_longest_question_by_type(IN in_type_name VARCHAR(50))
BEGIN
    SELECT 
        q.question_id, 
        q.content, 
        CHAR_LENGTH(q.content) AS content_length,
        tq.type_name
    FROM question q
    JOIN type_question tq ON q.type_id = tq.type_id
    WHERE tq.type_name = in_type_name
      AND CHAR_LENGTH(q.content) = (
          SELECT MAX(CHAR_LENGTH(q2.content))
          FROM question q2
          JOIN type_question tq2 ON q2.type_id = tq2.type_id
          WHERE tq2.type_name = in_type_name
      );
END$$
DELIMITER ;

-- QUESTION 9: Viết store cho phép xóa Exam dựa vào ID
DROP PROCEDURE IF EXISTS sp_delete_exam_by_id;
DELIMITER $$
CREATE PROCEDURE sp_delete_exam_by_id(IN in_exam_id INT)
BEGIN
    -- Xóa liên kết ở bảng phụ trước
    DELETE FROM exam_question WHERE exam_id = in_exam_id;
    -- Xóa đề thi ở bảng chính
    DELETE FROM exam WHERE exam_id = in_exam_id;
END$$
DELIMITER ;

-- QUESTION 10: Xóa exam tạo từ 3 năm trước (dùng store câu 9) & in ra số record đã remove
DROP PROCEDURE IF EXISTS sp_delete_exams_older_than_3_years;
DELIMITER $$
CREATE PROCEDURE sp_delete_exams_older_than_3_years()
BEGIN
    DECLARE v_exam_id INT;
    DECLARE v_done INT DEFAULT FALSE;
    DECLARE v_exam_count INT DEFAULT 0;
    DECLARE v_exam_question_count INT DEFAULT 0;

    -- Con trỏ duyệt các exam quá 3 năm
    DECLARE cur_exam CURSOR FOR 
        SELECT exam_id FROM exam WHERE create_date <= DATE_SUB(CURRENT_DATE(), INTERVAL 3 YEAR);
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET v_done = TRUE;

    -- Đếm tổng số bản ghi sẽ bị xóa trước khi thực hiện
    SELECT COUNT(exam_id) INTO v_exam_count 
    FROM exam 
    WHERE create_date <= DATE_SUB(CURRENT_DATE(), INTERVAL 3 YEAR);

    SELECT COUNT(eq.exam_id) INTO v_exam_question_count 
    FROM exam_question eq
    JOIN exam e ON eq.exam_id = e.exam_id
    WHERE e.create_date <= DATE_SUB(CURRENT_DATE(), INTERVAL 3 YEAR);

    -- Mở con trỏ duyệt xóa từng exam bằng SP câu 9
    OPEN cur_exam;
    read_loop: LOOP
        FETCH cur_exam INTO v_exam_id;
        IF v_done THEN
            LEAVE read_loop;
        END IF;
        CALL sp_delete_exam_by_id(v_exam_id);
    END LOOP;
    CLOSE cur_exam;

    -- In kết quả số lượng record đã xóa
    SELECT 
        v_exam_count AS removed_exams_count,
        v_exam_question_count AS removed_exam_questions_count,
        'Hoàn tất xóa các kỳ thi cũ hơn 3 năm!' AS status_message;
END$$
DELIMITER ;

-- QUESTION 11: Xóa phòng ban theo tên, chuyển account thuộc phòng đó về 'Phòng chờ'
DROP PROCEDURE IF EXISTS sp_delete_department_and_reassign;
DELIMITER $$
CREATE PROCEDURE sp_delete_department_and_reassign(IN in_dept_name VARCHAR(50))
BEGIN
    DECLARE v_del_dept_id TINYINT;
    DECLARE v_waiting_dept_id TINYINT;

    -- Tìm ID của phòng cần xóa
    SELECT department_id INTO v_del_dept_id 
    FROM department 
    WHERE department_name = in_dept_name 
    LIMIT 1;

    IF v_del_dept_id IS NOT NULL THEN
        -- Tìm hoặc tạo mới 'Phòng chờ'
        SELECT department_id INTO v_waiting_dept_id 
        FROM department 
        WHERE department_name = 'Phòng chờ' 
        LIMIT 1;

        IF v_waiting_dept_id IS NULL THEN
            INSERT INTO department (department_name) VALUES ('Phòng chờ');
            SET v_waiting_dept_id = LAST_INSERT_ID();
        END IF;

        -- Chuyển nhân viên sang phòng chờ
        UPDATE account_table 
        SET department_id = v_waiting_dept_id 
        WHERE department_id = v_del_dept_id;

        -- Xóa phòng ban được chỉ định
        DELETE FROM department WHERE department_id = v_del_dept_id;
        
        SELECT CONCAT('Đã xóa phòng ban "', in_dept_name, '" và chuyển nhân viên về phòng chờ.') AS result_message;
    ELSE
        SELECT CONCAT('Phòng ban "', in_dept_name, '" không tồn tại.') AS result_message;
    END IF;
END$$
DELIMITER ;

-- QUESTION 12: In ra mỗi tháng có bao nhiêu câu hỏi được tạo trong năm nay (12 tháng)
DROP PROCEDURE IF EXISTS sp_count_questions_per_month_this_year;
DELIMITER $$
CREATE PROCEDURE sp_count_questions_per_month_this_year()
BEGIN
    WITH RECURSIVE all_months AS (
        SELECT 1 AS month_no
        UNION ALL
        SELECT month_no + 1 FROM all_months WHERE month_no < 12
    )
    SELECT 
        m.month_no AS `month`,
        YEAR(CURRENT_DATE()) AS `year`,
        COUNT(q.question_id) AS total_questions
    FROM all_months m
    LEFT JOIN question q ON m.month_no = MONTH(q.create_date) 
        AND YEAR(q.create_date) = YEAR(CURRENT_DATE())
    GROUP BY m.month_no
    ORDER BY m.month_no;
END$$
DELIMITER ;

-- QUESTION 13: In ra mỗi tháng có bao nhiêu câu hỏi trong 6 tháng gần nhất (nếu không có in thông báo)
DROP PROCEDURE IF EXISTS sp_count_questions_last_6_months;
DELIMITER $$
CREATE PROCEDURE sp_count_questions_last_6_months()
BEGIN
    WITH RECURSIVE last_6_months AS (
        SELECT 0 AS n
        UNION ALL
        SELECT n + 1 FROM last_6_months WHERE n < 5
    ),
    months_series AS (
        SELECT 
            DATE_FORMAT(DATE_SUB(CURRENT_DATE(), INTERVAL n MONTH), '%Y-%m') AS year_month_label,
            MONTH(DATE_SUB(CURRENT_DATE(), INTERVAL n MONTH)) AS `month`,
            YEAR(DATE_SUB(CURRENT_DATE(), INTERVAL n MONTH)) AS `year`
        FROM last_6_months
    )
    SELECT 
        ms.year_month_label,
        CASE 
            WHEN COUNT(q.question_id) = 0 THEN 'không có câu hỏi nào trong tháng'
            ELSE CAST(COUNT(q.question_id) AS CHAR)
        END AS question_status
    FROM months_series ms
    LEFT JOIN question q ON ms.month = MONTH(q.create_date) AND ms.year = YEAR(q.create_date)
    GROUP BY ms.year_month_label
    ORDER BY ms.year_month_label DESC;
END$$
DELIMITER ;