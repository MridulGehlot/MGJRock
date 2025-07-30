package com.mg.hr.dl;
import com.mg.hr.dl.*;
import java.sql.*;
public class DAOConnection
{
private DAOConnection(){}
public static Connection getConnection() throws DAOException
{
Connection c=null;
try
{
Class.forName("com.mysql.cj.jdbc.Driver");
c=DriverManager.getConnection("jdbc:mysql://localhost:3307/styleone","styleone","styleone");
}catch(Exception e)
{
System.out.println(e.getMessage());
}
return c;
}
}