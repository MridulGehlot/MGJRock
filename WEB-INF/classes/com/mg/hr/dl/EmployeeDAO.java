package com.mg.hr.dl;
import java.util.*;
import java.sql.*;
import com.mg.hr.dl.*;
import java.math.*;
public class EmployeeDAO
{
public void add(EmployeeDTO employeeDTO) throws DAOException
{
if(employeeDTO==null) throw new DAOException("No Employee Found To Be Added");
String name=employeeDTO.getName();
if(name==null || name.trim().length()==0) throw new DAOException("Name is NULL");
int designationCode=employeeDTO.getDesignationCode();
if(designationCode<=0) throw new DAOException("This Designation Doesn't Exists");
Connection connection=null;
PreparedStatement preparedStatement;
ResultSet resultSet;
try
{
connection=DAOConnection.getConnection();
preparedStatement=connection.prepareStatement("select code from designation where code=?");
preparedStatement.setInt(1,designationCode);
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid Code");
}
resultSet.close();
preparedStatement.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
java.util.Date dateOfBirth=employeeDTO.getDateOfBirth();
if(dateOfBirth==null)
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("Date Of Birth Is NULL");
}
String gender=employeeDTO.getGender();
boolean isIndian=employeeDTO.getIsIndian();
BigDecimal basicSalary=employeeDTO.getBasicSalary();
if(basicSalary==null)
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
 throw new DAOException("Basic Salary is Null");
}
if(basicSalary.signum()==-1) 
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
} 
throw new DAOException("Basic Salary Is Negative");
}
String panNumber=employeeDTO.getPANNumber();
if(panNumber==null || panNumber.trim().length()==0)
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
 throw new DAOException("PAN Number is NULL");
}
String aadharCardNumber=employeeDTO.getAadharCardNumber();
if(aadharCardNumber==null || aadharCardNumber.trim().length()==0) 
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("Aadhar Card Number Is NULL");
}
try
{
boolean panNumberExists;
preparedStatement=connection.prepareStatement("select gender from employee where pan_number=?");
preparedStatement.setString(1,panNumber);
resultSet=preparedStatement.executeQuery();
panNumberExists=resultSet.next();
resultSet.close();
preparedStatement.close();
boolean aadharCardNumberExists;
preparedStatement=connection.prepareStatement("select gender from employee where aadhar_card_number=?");
preparedStatement.setString(1,aadharCardNumber);
resultSet=preparedStatement.executeQuery();
aadharCardNumberExists=resultSet.next();
resultSet.close();
preparedStatement.close();
if(panNumberExists && aadharCardNumberExists)
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("PAN Number ("+panNumber+") exists and Aadhar Card Number ("+aadharCardNumber+") Exists.");
}
if(panNumberExists)
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("PAN Number ("+panNumber+") exists.");
}
if(aadharCardNumberExists)
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("Aadhar Card Number ("+aadharCardNumber+") Exists.");
}
preparedStatement=connection.prepareStatement("insert into employee (name,designation_code,date_of_birth,gender,is_indian,basic_salary,pan_number,aadhar_card_number) values(?,?,?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
preparedStatement.setString(1,name);
preparedStatement.setInt(2,designationCode);
java.sql.Date sqlDate=new java.sql.Date(dateOfBirth.getYear(),dateOfBirth.getMonth(),dateOfBirth.getDate());
preparedStatement.setDate(3,sqlDate);
preparedStatement.setString(4,gender);
preparedStatement.setBoolean(5,isIndian);
preparedStatement.setBigDecimal(6,basicSalary);
preparedStatement.setString(7,panNumber);
preparedStatement.setString(8,aadharCardNumber);
preparedStatement.executeUpdate();
resultSet=preparedStatement.getGeneratedKeys();
resultSet.next();
int generatedEmployeeId=resultSet.getInt(1);
resultSet.close();
preparedStatement.close();
connection.close();
employeeDTO.setEmployeeId("A"+(1000000+generatedEmployeeId));
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
public List<EmployeeDTO> getAll() throws DAOException
{
List<EmployeeDTO> employees=new ArrayList<>();
try
{
Connection c=DAOConnection.getConnection();
Statement s=c.createStatement();
ResultSet rs=s.executeQuery("select employee.id,employee.name,employee.designation_code,designation.title,employee.date_of_birth,employee.gender,employee.is_indian,employee.basic_salary,employee.pan_number,employee.aadhar_card_number from employee inner join designation on employee.designation_code=designation.code order by employee.name");
EmployeeDTO emp=null;
int id;
String name;
int designationCode;
String title;
java.sql.Date dateOfBirth;
String gender;
Boolean isIndian;
BigDecimal basicSalary;
String panNumber;
String aadharCardNumber;
while(rs.next())
{
id=rs.getInt("id");
name=rs.getString("name").trim();
designationCode=rs.getInt("designation_code");
title=rs.getString("title").trim();
dateOfBirth=rs.getDate("date_of_birth");
gender=rs.getString("gender");
isIndian=rs.getBoolean("is_indian");
basicSalary=rs.getBigDecimal("basic_salary");
panNumber=rs.getString("pan_number").trim();
aadharCardNumber=rs.getString("aadhar_card_number").trim();

emp=new EmployeeDTO();
emp.setEmployeeId("A"+id);
emp.setName(name);
emp.setDesignationCode(designationCode);
emp.setDesignation(title);
emp.setDateOfBirth(dateOfBirth);
emp.setGender(gender);
emp.setIsIndian(isIndian);
emp.setBasicSalary(basicSalary);
emp.setPANNumber(panNumber);
emp.setAadharCardNumber(aadharCardNumber);

employees.add(emp);
}
rs.close();
s.close();
c.close();
}catch(Exception e)
{
throw new DAOException(e.getMessage());
}
return employees;
}
public boolean PANNumberExists(String panNumber) throws DAOException
{
boolean exists=false;
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement ps=connection.prepareStatement("select gender from employee where pan_number=?");
ps.setString(1,panNumber);
ResultSet rs=ps.executeQuery();
exists=rs.next();
rs.close();
ps.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
return exists;
}
public boolean aadharCardNumberExists(String aadharCardNumber) throws DAOException
{
boolean exists=false;
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement ps=connection.prepareStatement("select gender from employee where aadhar_card_number=?");
ps.setString(1,aadharCardNumber);
ResultSet rs=ps.executeQuery();
exists=rs.next();
rs.close();
ps.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
return exists;
}
public void deleteByEmployeeId(String employeeId) throws DAOException
{
try
{
employeeId=employeeId.substring(1);
int actualEmployeeId=Integer.parseInt(employeeId);
Connection connection=DAOConnection.getConnection();
PreparedStatement ps=connection.prepareStatement("select gender from employee where id=?");
ps.setInt(1,actualEmployeeId);
ResultSet rs=ps.executeQuery();
if(rs.next()==false)
{
rs.close();
ps.close();
connection.close();
throw new DAOException("Invalid Employee Id. = "+employeeId);
}
rs.close();
ps.close();
ps=connection.prepareStatement("delete from employee where id=?");
ps.setInt(1,actualEmployeeId);
ps.executeUpdate();
ps.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
public EmployeeDTO getByEmployeeId(String employeeId) throws DAOException
{
EmployeeDTO emp=null;
try
{
employeeId=employeeId.substring(1);
int actualEmployeeId=Integer.parseInt(employeeId);
Connection connection=DAOConnection.getConnection();
PreparedStatement ps=connection.prepareStatement("select gender from employee where id=?");
ps.setInt(1,actualEmployeeId);
ResultSet rs=ps.executeQuery();
if(rs.next()==false)
{
rs.close();
ps.close();
connection.close();
throw new DAOException("Invalid Employee Id. = "+employeeId);
}
rs.close();
ps.close();
ps=connection.prepareStatement("select employee.id,employee.name,employee.designation_code,designation.title,employee.date_of_birth,employee.gender,employee.is_indian,employee.basic_salary,employee.pan_number,employee.aadhar_card_number from employee inner join designation on employee.designation_code=designation.code where id=?");
ps.setInt(1,actualEmployeeId);
rs=ps.executeQuery();
int id;
String name;
int designationCode;
String title;
java.sql.Date dateOfBirth;
String gender;
Boolean isIndian;
BigDecimal basicSalary;
String panNumber;
String aadharCardNumber;
while(rs.next())
{
id=rs.getInt("id");
name=rs.getString("name").trim();
designationCode=rs.getInt("designation_code");
title=rs.getString("title").trim();
dateOfBirth=rs.getDate("date_of_birth");
gender=rs.getString("gender");
isIndian=rs.getBoolean("is_indian");
basicSalary=rs.getBigDecimal("basic_salary");
panNumber=rs.getString("pan_number").trim();
aadharCardNumber=rs.getString("aadhar_card_number").trim();

emp=new EmployeeDTO();
emp.setEmployeeId("A"+id);
emp.setName(name);
emp.setDesignationCode(designationCode);
emp.setDesignation(title);
emp.setDateOfBirth(dateOfBirth);
emp.setGender(gender);
emp.setIsIndian(isIndian);
emp.setBasicSalary(basicSalary);
emp.setPANNumber(panNumber);
emp.setAadharCardNumber(aadharCardNumber);

}
rs.close();
ps.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
return emp;
}
public void update(EmployeeDTO employeeDTO) throws DAOException
{
if(employeeDTO==null) throw new DAOException("No Employee Found To Be Added");
String employeeId=employeeDTO.getEmployeeId();
if(employeeId==null || employeeId.trim().length()==0) throw new DAOException("employeeId is NULL");
int actualEmployeeId=Integer.parseInt(employeeId.substring(1));
String name=employeeDTO.getName();
if(name==null || name.trim().length()==0) throw new DAOException("Name is NULL");
int designationCode=employeeDTO.getDesignationCode();
if(designationCode<=0) throw new DAOException("This Designation Doesn't Exists");
Connection connection=null;
PreparedStatement preparedStatement;
ResultSet resultSet;
try
{
connection=DAOConnection.getConnection();
preparedStatement=connection.prepareStatement("select code from designation where code=?");
preparedStatement.setInt(1,designationCode);
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid Designation");
}
resultSet.close();
preparedStatement.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
java.util.Date dateOfBirth=employeeDTO.getDateOfBirth();
if(dateOfBirth==null)
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("Date Of Birth Is NULL");
}
String gender=employeeDTO.getGender();
boolean isIndian=employeeDTO.getIsIndian();
BigDecimal basicSalary=employeeDTO.getBasicSalary();
if(basicSalary==null)
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
 throw new DAOException("Basic Salary is Null");
}
if(basicSalary.signum()==-1) 
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
} 
throw new DAOException("Basic Salary Is Negative");
}
String panNumber=employeeDTO.getPANNumber();
if(panNumber==null || panNumber.trim().length()==0)
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
 throw new DAOException("PAN Number is NULL");
}
String aadharCardNumber=employeeDTO.getAadharCardNumber();
if(aadharCardNumber==null || aadharCardNumber.trim().length()==0) 
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("Aadhar Card Number Is NULL");
}
try
{
boolean panNumberExists;
preparedStatement=connection.prepareStatement("select gender from employee where pan_number=? and id<>?");
preparedStatement.setString(1,panNumber);
preparedStatement.setInt(2,actualEmployeeId);
resultSet=preparedStatement.executeQuery();
panNumberExists=resultSet.next();
resultSet.close();
preparedStatement.close();
boolean aadharCardNumberExists;
preparedStatement=connection.prepareStatement("select gender from employee where aadhar_card_number=? and id<>?");
preparedStatement.setString(1,aadharCardNumber);
preparedStatement.setInt(2,actualEmployeeId);
resultSet=preparedStatement.executeQuery();
aadharCardNumberExists=resultSet.next();
resultSet.close();
preparedStatement.close();
if(panNumberExists && aadharCardNumberExists)
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("PAN Number ("+panNumber+") exists and Aadhar Card Number ("+aadharCardNumber+") Exists.");
}
if(panNumberExists)
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("PAN Number ("+panNumber+") exists.");
}
if(aadharCardNumberExists)
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("Aadhar Card Number ("+aadharCardNumber+") Exists.");
}
preparedStatement=connection.prepareStatement("update employee set name=?,designation_code=?,date_of_birth=?,gender=?,is_indian=?,basic_salary=?,pan_number=?,aadhar_card_number=? where id=?");
preparedStatement.setString(1,name);
preparedStatement.setInt(2,designationCode);
java.sql.Date sqlDate=new java.sql.Date(dateOfBirth.getYear(),dateOfBirth.getMonth(),dateOfBirth.getDate());
preparedStatement.setDate(3,sqlDate);
preparedStatement.setString(4,gender);
preparedStatement.setBoolean(5,isIndian);
preparedStatement.setBigDecimal(6,basicSalary);
preparedStatement.setString(7,panNumber);
preparedStatement.setString(8,aadharCardNumber);
preparedStatement.setInt(9,actualEmployeeId);
preparedStatement.executeUpdate();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
}