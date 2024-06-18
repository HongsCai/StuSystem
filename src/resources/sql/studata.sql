-- 创建数据库并使用 utf8mb4 字符集和排序规则
CREATE DATABASE studata CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE studata;

-- 创建 user_stu 表
CREATE TABLE `user_stu` (
    `username` VARCHAR(30) PRIMARY KEY,
    `password` VARCHAR(30) NOT NULL
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 创建 user_tch 表
CREATE TABLE `user_tch` (
    `username` VARCHAR(30) PRIMARY KEY,
    `password` VARCHAR(30) NOT NULL
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 创建 user_admin 表
CREATE TABLE `user_admin` (
    `username` VARCHAR(30) PRIMARY KEY,
    `password` VARCHAR(30) NOT NULL
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 创建 stu 表
CREATE TABLE `stu` (
    `name` VARCHAR(20) NOT NULL,
    `gender` CHAR(2) NOT NULL,
    `sno` CHAR(12) PRIMARY KEY,
    `major` VARCHAR(12) DEFAULT NULL
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 创建 course 表
CREATE TABLE `course` (
    `cno` CHAR(4) PRIMARY KEY,
    `cname` VARCHAR(20) DEFAULT NULL,
    `cteacher` VARCHAR(20) DEFAULT NULL,
    `credit` INT(11) DEFAULT NULL
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 创建 sc 表
CREATE TABLE `sc` (
    `sno` CHAR(12) NOT NULL,
    `cno` CHAR(4) NOT NULL,
    `score` INT(11) DEFAULT NULL,
    PRIMARY KEY (`sno`, `cno`),
    CONSTRAINT `fk_sc_cno` FOREIGN KEY (`cno`) REFERENCES `course` (`cno`) ON DELETE CASCADE,
    CONSTRAINT `fk_sc_sno` FOREIGN KEY (`sno`) REFERENCES `stu` (`sno`) ON DELETE CASCADE
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 插入 user_admin 表数据
INSERT INTO `user_admin` (`username`, `password`) VALUES
('admin', '123');

-- 插入 user_tch 表数据
INSERT INTO `user_tch` (`username`, `password`) VALUES
('tch', '123');

-- 插入 user_stu 表数据
INSERT INTO `user_stu` (`username`, `password`) VALUES
('stu', '123');

-- 插入 course 表数据
INSERT INTO `course` (`cno`, `cname`, `cteacher`, `credit`) VALUES
('0001', '计算机应用基础', '张老师', 1),
('0002', 'C++程序设计', '李老师', 2),
('0003', '数据库', '王老师', 3),
('0004', '计算机操作系统', '赵老师', 2),
('0005', '英语', '陈老师', 2),
('0006', '高等数学', '刘老师', 1),
('0007', '软件工程', '孙老师', 2),
('0008', '算法导论', '周老师', 3),
('0009', 'Java面向对象程序设计', '吴老师', 4),
('0010', '数据结构', '杨老师', 3),
('0011', '计算机网络', '黄老师', 3);

-- 插入 stu 表数据
INSERT INTO `stu` (`name`, `gender`, `sno`, `major`) VALUES
('王大勇', '男', '202220010401', '计算机科学与技术'),
('李志成', '男', '202220010402', '软件工程'),
('赵小明', '男', '202220010403', '计算机科学与技术'),
('刘慧敏', '女', '202220010404', '物联网工程'),
('陈光明', '男', '202220010405', '智能科学与技术'),
('吴小丽', '女', '202220010406', '智能科学与技术'),
('林美玲', '女', '202220010407', '计算机科学与技术'),
('赵晓宇', '男', '202220010408', '软件工程'),
('孙小芳', '女', '202220010409', '计算机科学与技术'),
('马光辉', '男', '202220010410', '物联网工程'),
('刘晓华', '女', '202220010411', '智能科学与技术');

-- 插入 sc 表数据
INSERT INTO `sc` (`sno`, `cno`, `score`) VALUES
('202220010401', '0001', 85),
('202220010401', '0002', 78),
('202220010401', '0003', 92),
('202220010402', '0001', 79),
('202220010402', '0004', 85),
('202220010403', '0002', 88),
('202220010403', '0005', 90),
('202220010404', '0003', 87),
('202220010404', '0006', 95),
('202220010405', '0002', 82),
('202220010405', '0007', 88),
('202220010406', '0003', 90),
('202220010406', '0008', 92),
('202220010407', '0003', 85),
('202220010407', '0009', 89),
('202220010408', '0010', 76),
('202220010409', '0011', 84),
('202220010410', '0010', 92),
('202220010411', '0011', 88);