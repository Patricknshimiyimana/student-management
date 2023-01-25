create database student_management;
use student_management;

CREATE TABLE admin (
	id int NOT NULL AUTO_INCREMENT,
    username varchar(45) NOT NULL,
    password varchar(45) NOT NULL,
    PRIMARY KEY (`id`)
    );
    
    INSERT INTO admin (username, password) values ("admin_violette","1234");
    
    CREATE TABLE student (
	id int NOT NULL AUTO_INCREMENT,
    name varchar(45) NOT NULL,
    gender varchar(10) NOT NULL,
    age varchar(2) NOT NULL, 
    email varchar(100) NOT NULL,
    phone varchar(15) NOT NULL,
    father_name varchar(150) NOT NULL,
    mother_name varchar(150) NOT NULL,
    address text NOT NULL,
    image_path varchar(200) NOT NULL,
    PRIMARY KEY (id)
    );
    
	CREATE TABLE course (
    id int NOT NULL AUTO_INCREMENT,
    student_id int default NULL,
    semester int default null,
    course1 varchar(200) default null,
    course2 varchar(200) default null,
    course3 varchar(200) default null,
    primary key (id),
    KEY fk_student_id (student_id),
    constraint fk_student_id foreign key (student_id) references student (id) on delete cascade
    );
    
	CREATE TABLE score (
    id int NOT NULL AUTO_INCREMENT,
    student_id int default NULL,
    semester int default null,
    course1 varchar(200) default null,
    score1 double not NULL,
    course2 varchar(200) default null,
    score2 double not null,
    course3 varchar(200) default null,
    score3 double not null,
    average double not null,
    primary key (id),
    KEY fk_stu_id (student_id),
    constraint fk_stu_id foreign key (student_id) references student (id) on delete cascade
    );