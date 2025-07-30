package com.mg.hr.dl;
import java.sql.*;
public class AdministratorDAO
{
public AdministratorDTO getByUname(String uname) throws DAOException
{
if(uname==null || uname.trim().length()==0) throw new DAOException("Username Required");
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement ps=connection.prepareStatement("select pwd from administrator where uname=?");
ps.setString(1,uname);
ResultSet rs=ps.executeQuery();
if(rs.next()==false)
{
rs.close();
ps.close();
connection.close();
throw new DAOException("Invalid Username - "+uname);
}
String pwd=rs.getString("pwd");
AdministratorDTO administratorDTO=new AdministratorDTO();
administratorDTO.setUname(uname);
administratorDTO.setPwd(pwd);
rs.close();
ps.close();
connection.close();
return administratorDTO;
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
}