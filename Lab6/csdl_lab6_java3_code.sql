-- Tạo bảng Departments
CREATE DATABASE lab6_java3_csdl
use lab6_java3_csdl
CREATE TABLE Departments (
    id CHAR(3) NOT NULL PRIMARY KEY,
    Name NVARCHAR(50) NOT NULL,
    Description NVARCHAR(255) NULL
);
GO

-- Tạo bảng Employees
CREATE TABLE Employees (
    Id VARCHAR(20) NOT NULL PRIMARY KEY,
    Password NVARCHAR(50) NOT NULL,
    Fullname NVARCHAR(50) NOT NULL,
    Photo NVARCHAR(50) NOT NULL,
    Gender BIT NOT NULL,
    Birthday DATE NOT NULL,
    Salary FLOAT NOT NULL,
    DepartmentId CHAR(3) NOT NULL,
    FOREIGN KEY (DepartmentId) REFERENCES Departments(id) 
    ON UPDATE CASCADE 
    ON DELETE CASCADE
);
GO

-- Thêm dữ liệu cho bảng Departments
INSERT INTO Departments (id, Name, Description) 
VALUES 
('001', 'Sales', 'Sales Department'),
('002', 'HR', 'Human Resources Department'),
('003', 'IT', 'Information Technology Department');

-- Thêm dữ liệu cho bảng Employees
INSERT INTO Employees (Id, Password, Fullname, Photo, Gender, Birthday, Salary, DepartmentId) 
VALUES 
('E001', 'pass123', 'John Doe', 'john.jpg', 1, '1990-05-15', 50000, '001'),
('E002', 'pass234', 'Jane Smith', 'jane.jpg', 0, '1988-07-23', 60000, '002'),
('E003', 'pass345', 'Michael Johnson', 'michael.jpg', 1, '1985-02-12', 55000, '003');
GO
