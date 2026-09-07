USE testing_system_assignment_1;

-- QUESTION 1: Không cho phép người dùng nhập vào Group có ngày tạo trước 1 năm trước
DROP TRIGGER IF EXISTS trg_check_group_created_date;
DELIMITER $$
CREATE TRIGGER trg_check_group_created_date
BEFORE INSERT ON group_table
FOR EACH ROW
BEGIN
    IF NEW.create_date < DATE_SUB(CURRENT_DATE(), INTERVAL 1 YEAR) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Lỗi: Không được phép tạo Group có ngày tạo cũ hơn 1 năm trước!';
    END IF;
END$$
DELIMITER ;

-- QUESTION 2: Không cho phép thêm user vào department "Sale", hiển thị thông báo
DROP TRIGGER IF EXISTS trg_check_department_sale;
DELIMITER $$
CREATE TRIGGER trg_check_department_sale
BEFORE INSERT ON account_table
FOR EACH ROW
BEGIN
    DECLARE v_dept_name VARCHAR(50);
    
    SELECT department_name INTO v_dept_name 
    FROM department 
    WHERE department_id = NEW.department_id;
    
    IF v_dept_name = 'Sale' THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Không thể thêm nhân sự phòng "Sale"';
    END IF;
END$$
DELIMITER ;

-- QUESTION 3: Cấu hình 1 group có nhiều nhất là 5 user
DROP TRIGGER IF EXISTS trg_limit_group_members;
DELIMITER $$
CREATE TRIGGER trg_limit_group_members
BEFORE INSERT ON group_account
FOR EACH ROW
BEGIN
    DECLARE v_count INT;
    
    SELECT COUNT(*) INTO v_count 
    FROM group_account 
    WHERE group_id = NEW.group_id;
    
    IF v_count >= 5 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Lỗi: Một Group chỉ được phép có tối đa 5 thành viên!';
    END IF;
END$$
DELIMITER ;

-- QUESTION 4: Cấu hình 1 bài thi có nhiều nhất là 10 Question
DROP TRIGGER IF EXISTS trg_limit_exam_questions;
DELIMITER $$
CREATE TRIGGER trg_limit_exam_questions
BEFORE INSERT ON exam_question
FOR EACH ROW
BEGIN
    DECLARE v_count INT;
    
    SELECT COUNT(*) INTO v_count 
    FROM exam_question 
    WHERE exam_id = NEW.exam_id;
    
    IF v_count >= 10 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Lỗi: Một bài thi chỉ được chứa tối đa 10 Question!';
    END IF;
END$$
DELIMITER ;

-- QUESTION 5: Không cho phép xóa tài khoản admin@gmail.com, các tài khoản khác được xóa và xóa sạch dữ liệu liên quan
DROP TRIGGER IF EXISTS trg_prevent_delete_admin;
DELIMITER $$
CREATE TRIGGER trg_prevent_delete_admin
BEFORE DELETE ON account_table
FOR EACH ROW
BEGIN
    IF OLD.email = 'admin@gmail.com' THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Lỗi: Không được phép xóa tài khoản Admin hệ thống!';
    ELSE
        DELETE FROM answer 
        WHERE question_id IN (SELECT question_id FROM question WHERE author_id = OLD.account_id);

        DELETE FROM question 
        WHERE author_id = OLD.account_id;

        DELETE FROM exam_question 
        WHERE exam_id IN (SELECT exam_id FROM exam WHERE creator_id = OLD.account_id);

        DELETE FROM exam 
        WHERE creator_id = OLD.account_id;

        DELETE FROM group_account 
        WHERE account_id = OLD.account_id;

        DELETE FROM group_table 
        WHERE creator_id = OLD.account_id;
        
    END IF;
END$$
DELIMITER ;

-- QUESTION 6: Tạo account không điền departmentID thì tự gán vào phòng "waiting Department"
-- Bổ sung thêm phòng "waiting Department" nếu chưa tồn tại
INSERT IGNORE INTO department (department_name) VALUES ('waiting Department');

DROP TRIGGER IF EXISTS trg_default_waiting_department;
DELIMITER $$
CREATE TRIGGER trg_default_waiting_department
BEFORE INSERT ON account_table
FOR EACH ROW
BEGIN
    DECLARE v_waiting_id TINYINT;
    
    IF NEW.department_id IS NULL THEN
        SELECT department_id INTO v_waiting_id 
        FROM department 
        WHERE department_name = 'waiting Department' 
        LIMIT 1;
        
        SET NEW.department_id = v_waiting_id;
    END IF;
END$$
DELIMITER ;

-- QUESTION 7: 1 question tối đa 4 answers, trong đó tối đa 2 đáp án đúng (is_correct = TRUE)
DROP TRIGGER IF EXISTS trg_limit_answers_per_question;
DELIMITER $$
CREATE TRIGGER trg_limit_answers_per_question
BEFORE INSERT ON answer
FOR EACH ROW
BEGIN
    DECLARE v_total_answers INT;
    DECLARE v_correct_answers INT;
    
    -- Đếm tổng số câu trả lời hiện tại của question này
    SELECT COUNT(*) INTO v_total_answers 
    FROM answer 
    WHERE question_id = NEW.question_id;
    
    IF v_total_answers >= 4 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Lỗi: Một Question tối đa chỉ được phép có 4 câu trả lời!';
    END IF;
    
    -- Nếu câu trả lời mới thêm vào là đúng (TRUE), kiểm tra xem đã đủ 2 đáp án đúng chưa
    IF NEW.is_correct = TRUE THEN
        SELECT COUNT(*) INTO v_correct_answers 
        FROM answer 
        WHERE question_id = NEW.question_id AND is_correct = TRUE;
        
        IF v_correct_answers >= 2 THEN
            SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Lỗi: Một Question tối đa chỉ được phép có 2 đáp án đúng!';
        END IF;
    END IF;
END$$
DELIMITER ;

-- QUESTION 8: Chuẩn hóa dữ liệu gender (nam, nữ, chưa xác định -> M, F, U)
-- Thêm cột gender vào account_table nếu chưa có
ALTER TABLE account_table ADD COLUMN gender ENUM('M', 'F', 'U');

DROP TRIGGER IF EXISTS trg_normalize_gender;
DELIMITER $$
CREATE TRIGGER trg_normalize_gender
BEFORE INSERT ON account_table
FOR EACH ROW
BEGIN
    IF NEW.gender = 'nam' THEN
        SET NEW.gender = 'M';
    ELSEIF NEW.gender = 'nữ' THEN
        SET NEW.gender = 'F';
    ELSEIF NEW.gender = 'chưa xác định' OR NEW.gender IS NULL THEN
        SET NEW.gender = 'U';
    END IF;
END$$
DELIMITER ;

-- QUESTION 9: Không cho phép người dùng xóa bài thi (exam) mới tạo được 2 ngày
DROP TRIGGER IF EXISTS trg_prevent_delete_recent_exam;
DELIMITER $$
CREATE TRIGGER trg_prevent_delete_recent_exam
BEFORE DELETE ON exam
FOR EACH ROW
BEGIN
    IF OLD.create_date >= DATE_SUB(CURRENT_DATE(), INTERVAL 2 DAY) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Lỗi: Không được phép xóa bài thi mới được tạo trong vòng 2 ngày gần đây!';
    END IF;
END$$
DELIMITER ;

-- QUESTION 10: Chỉ cho phép update/delete question khi question đó CHƯA nằm trong exam nào
-- Kiểm tra trước khi UPDATE question
DROP TRIGGER IF EXISTS trg_prevent_update_question;
DELIMITER $$
CREATE TRIGGER trg_prevent_update_question
BEFORE UPDATE ON question
FOR EACH ROW
BEGIN
    DECLARE v_exam_count INT;
    
    SELECT COUNT(*) INTO v_exam_count 
    FROM exam_question 
    WHERE question_id = OLD.question_id;
    
    IF v_exam_count > 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Lỗi: Không thể chỉnh sửa Question vì đã được sử dụng trong bài thi (Exam)!';
    END IF;
END$$
DELIMITER ;

-- Kiểm tra trước khi DELETE question
DROP TRIGGER IF EXISTS trg_prevent_delete_question;
DELIMITER $$
CREATE TRIGGER trg_prevent_delete_question
BEFORE DELETE ON question
FOR EACH ROW
BEGIN
    DECLARE v_exam_count INT;
    
    SELECT COUNT(*) INTO v_exam_count 
    FROM exam_question 
    WHERE question_id = OLD.question_id;
    
    IF v_exam_count > 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Lỗi: Không thể xóa Question vì đã được sử dụng trong bài thi (Exam)!';
    END IF;
END$$
DELIMITER ;