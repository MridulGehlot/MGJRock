package com.mg.hr.dl;
import java.util.*;
import java.sql.*;
import com.mg.hr.dl.*;
public class DesignationDAO
{
public void add(DesignationDTO designationDTO) throws DAOException
{
if(designationDTO==null) throw new DAOException("Designation Required");
int code=designationDTO.getCode();
String title=designationDTO.getTitle();
if(code!=0) throw new DAOException("Code Should be zero");
if(title==null || title.trim().length()==0) throw new DAOException("Title Required");
try
{
Connection c=DAOConnection.getConnection();
PreparedStatement ps=c.prepareStatement("select code from designation where title=?");
ps.setString(1,title);
ResultSet rs=ps.executeQuery();
if(rs.next())
{
rs.close();
ps.close();
c.close();
throw new DAOException("Designation "+title+" Exists.");
}
rs.close();
ps.close();
ps=c.prepareStatement("insert into designation (title) values(?)",Statement.RETURN_GENERATED_KEYS);
ps.setString(1,title);
ps.executeUpdate();
rs=ps.getGeneratedKeys();
rs.next();
code=rs.getInt(1);
designationDTO.setCode(code);
rs.close();
ps.close();
c.close();
}catch(Exception e)
{
throw new DAOException(e.getMessage());
}
}
public List<DesignationDTO> getAll() throws DAOException
{
List<DesignationDTO> designations=new ArrayList<>();
try
{
Connection c=DAOConnection.getConnection();
Statement s=c.createStatement();
ResultSet rs=s.executeQuery("select * from designation order by title");
DesignationDTO d;
int code;
String title;
while(rs.next())
{
d=new DesignationDTO();
code=rs.getInt("code");
title=rs.getString("title");
d.setCode(code);
d.setTitle(title);
designations.add(d);
}
rs.close();
s.close();
c.close();
}catch(Exception e)
{
throw new DAOException(e.getMessage());
}
return designations;
}
public DesignationDTO getByCode(int code) throws DAOException
{
if(code<=0)
{
throw new DAOException("Invalid code = "+code);
}
DesignationDTO designationDTO=null;
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement=connection.prepareStatement("select title from designation where code=?");
preparedStatement.setInt(1,code);
ResultSet rs=preparedStatement.executeQuery();
if(rs.next()==false)
{
rs.close();
preparedStatement.close();
connection.close();
}
String title=rs.getString("title").trim(); //VVV imp to trim
designationDTO=new DesignationDTO();
designationDTO.setCode(code);
designationDTO.setTitle(title);
rs.close();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
return designationDTO;
}
public void update(DesignationDTO designationDTO) throws DAOException
{
if(designationDTO==null)
{
throw new DAOException("Designation Required To Update");
}
int code=designationDTO.getCode();
String title=designationDTO.getTitle();
if(code<=0 || title==null)
{
throw new DAOException("Designation Required To Update");
}
if(title.trim().length()==0)
{
throw new DAOException("Designation Required To Update");
}
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement ps=connection.prepareStatement("select * from designation where title=? and code!=?");
ps.setString(1,title);
ps.setInt(2,code);
ResultSet rs=ps.executeQuery();
if(rs.next())
{
rs.close();
ps.close();
connection.close();
throw new DAOException("Designation "+title+" exists.");
}
rs.close();
ps.close();
ps=connection.prepareStatement("update designation set title=? where code=?");
ps.setString(1,title);
ps.setInt(2,code);
ps.executeUpdate();
ps.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
public void deleteByCode(int code) throws DAOException
{
if(code<=0)
{
throw new DAOException("Invalid Code "+code);
}
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement ps=connection.prepareStatement("select * from designation where code=?");
ps.setInt(1,code);
ResultSet rs=ps.executeQuery();
if(rs.next()==false)
{
rs.close();
ps.close();
connection.close();
throw new DAOException("Invalid Code "+code);
}
rs.close();
ps.close();
ps=connection.prepareStatement("select gender from employee where designation_code=?");
ps.setInt(1,code);
rs=ps.executeQuery();
if(rs.next())
{
rs.close();
ps.close();
connection.close();
throw new DAOException("Cannot Delete This Designation as It has Been Alloted to an Employee");
}
rs.close();
ps.close();
ps=connection.prepareStatement("delete from designation where code=?");
ps.setInt(1,code);
ps.executeUpdate();
ps.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
public boolean designationCodeExists(int code) throws DAOException
{
boolean exists=false;
if(code<=0)
{
throw new DAOException("Invalid Code "+code);
}
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement ps=connection.prepareStatement("select code from designation where code=?");
ps.setInt(1,code);
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
}