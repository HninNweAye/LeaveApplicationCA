

-- Role 
INSERT INTO `ca_project`.`role`(`role_id`,`role_name`)VALUES(1,'manager');
INSERT INTO `ca_project`.`role`(`role_id`,`role_name`)VALUES(2,'staff');
INSERT INTO `ca_project`.`role`(`role_id`,`role_name`)VALUES(3,'adminn');

INSERT INTO `ca_project`.`employee`(`emp_id`,`email`,`full_name`,`password`,`user_name`,`reports_to`,`role_id`)
VALUES(1,'aa@gmail.com','AAA','aaa','aaa',1,1);
INSERT INTO `ca_project`.`employee`(`emp_id`,`email`,`full_name`,`password`,`user_name`,`reports_to`,`role_id`)
VALUES(2,'tom@gmail.com','Tom','tom','tom',1,2);
INSERT INTO `ca_project`.`employee`(`emp_id`,`email`,`full_name`,`password`,`user_name`,`reports_to`,`role_id`)
VALUES(3,'jennifer@gmail.com','Jennifer','jennifer','jennifer',1,2);
INSERT INTO `ca_project`.`employee`(`emp_id`,`email`,`full_name`,`password`,`user_name`,`reports_to`,`role_id`)
VALUES(4,'anna@gmail.com','anna','anna','anna',1,3);
INSERT INTO `ca_project`.`employee`(`emp_id`,`email`,`full_name`,`password`,`user_name`,`reports_to`,`role_id`)
VALUES(5,'smith@gmail.com','Smith','smith','smith',1,1);
INSERT INTO `ca_project`.`employee`(`emp_id`,`email`,`full_name`,`password`,`user_name`,`reports_to`,`role_id`)
VALUES(6,'kelvin@gmail.com','Kelvin','kelvin','kelvin',5,2);


-- Leave Type 
INSERT INTO `ca_project`.`leave_type`(`leave_type_id`,`type`)VALUES(1,"AnnualLeave");
INSERT INTO `ca_project`.`leave_type`(`leave_type_id`,`type`)VALUES(2,"MedicalLeave");
INSERT INTO `ca_project`.`leave_type`(`leave_type_id`,`type`)VALUES(3,"CompensationLeave");

-- For Annual Leave 
INSERT INTO `ca_project`.`role_leave_type`(`role_leave_id`,`no_of_days`,`leave_type_id`,`role_id`)
VALUES(1,10,1,1);
INSERT INTO `ca_project`.`role_leave_type`(`role_leave_id`,`no_of_days`,`leave_type_id`,`role_id`)
VALUES(2,20,1,2);
INSERT INTO `ca_project`.`role_leave_type`(`role_leave_id`,`no_of_days`,`leave_type_id`,`role_id`)
VALUES(3,18,1,3);
-- For Medical Leave
INSERT INTO `ca_project`.`role_leave_type`(`role_leave_id`,`no_of_days`,`leave_type_id`,`role_id`)
VALUES(4,70,2,1);
INSERT INTO `ca_project`.`role_leave_type`(`role_leave_id`,`no_of_days`,`leave_type_id`,`role_id`)
VALUES(5,60,2,2);
INSERT INTO `ca_project`.`role_leave_type`(`role_leave_id`,`no_of_days`,`leave_type_id`,`role_id`)
VALUES(6,60,2,3);

-- Leave Entitlement
INSERT INTO `ca_project`.`leave_entitled`(`leave_entitled_id`,`remaining_leave`,`employee_id`,`leave_type_id`)
VALUES(1,10,1,1);
INSERT INTO `ca_project`.`leave_entitled`(`leave_entitled_id`,`remaining_leave`,`employee_id`,`leave_type_id`)
VALUES(2,20,2,1);
INSERT INTO `ca_project`.`leave_entitled`(`leave_entitled_id`,`remaining_leave`,`employee_id`,`leave_type_id`)
VALUES(3,15,3,1);
INSERT INTO `ca_project`.`leave_entitled`(`leave_entitled_id`,`remaining_leave`,`employee_id`,`leave_type_id`)
VALUES(4,18,4,1);
INSERT INTO `ca_project`.`leave_entitled`(`leave_entitled_id`,`remaining_leave`,`employee_id`,`leave_type_id`)
VALUES(5,70,1,2);
INSERT INTO `ca_project`.`leave_entitled`(`leave_entitled_id`,`remaining_leave`,`employee_id`,`leave_type_id`)
VALUES(6,60,2,2);
INSERT INTO `ca_project`.`leave_entitled`(`leave_entitled_id`,`remaining_leave`,`employee_id`,`leave_type_id`)
VALUES(7,60,3,2);
INSERT INTO `ca_project`.`leave_entitled`(`leave_entitled_id`,`remaining_leave`,`employee_id`,`leave_type_id`)
VALUES(8,60,4,2);

-- leave_history_details
 INSERT INTO `ca_project`.`leave_history_details`(`leave_history_id`,`applying_reason`,`end_date`,`rejection_reason`,`start_date`,
 `leave_status`,`work_desemination`,`emp_id`,`leave_type_id`)
VALUES
(1,'ill','2019-05-23','','2019-05-20','APPLIED','',2,1);
INSERT INTO `ca_project`.`leave_history_details`(`leave_history_id`,`applying_reason`,`end_date`,`rejection_reason`,`start_date`,
 `leave_status`,`work_desemination`,`emp_id`,`leave_type_id`)
VALUES
(2,'medical checkup','2019-05-24','','2019-05-22','APPLIED','',3,2);


SELECT * FROM ca_project.employee;
SELECT * FROM ca_project.role;
SELECT * FROM ca_project.leave_type;
SELECT * FROM ca_project.role_leave_type;
SELECT * FROM ca_project.leave_entitled;
SELECT * FROM ca_project.leave_history_details;

