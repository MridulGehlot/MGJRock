package com.mg.hr.servlets;
import com.mg.hr.dl.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import com.google.gson.*;
public class ServletTwo extends HttpServlet
{
public void doGet(HttpServletRequest request,HttpServletResponse response)
{
try
{
PrintWriter pw=response.getWriter();
int code=Integer.parseInt(request.getParameter("code"));
response.setContentType("application/json");
DesignationDAO designationDAO=new DesignationDAO();
Gson gson=new Gson();
try
{
DesignationDTO designation=designationDAO.getByCode(code);
pw.print(gson.toJson(designation));
}catch(DAOException ee)
{
pw.print(gson.toJson("INVALID"));
}
}catch(Exception e)
{
try
{
response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
}catch(Exception ee)
{
//do nothing
}
}
}
public void doPost(HttpServletRequest request,HttpServletResponse response)
{
try
{
response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
}catch(Exception ee)
{
//do nothing
}
}
}