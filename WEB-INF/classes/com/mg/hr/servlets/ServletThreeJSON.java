package com.mg.hr.servlets;
import com.mg.hr.dl.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.google.gson.*;
public class ServletThreeJSON extends HttpServlet
{
public void doPost(HttpServletRequest request,HttpServletResponse response)
{
try
{
/*
String firstName=request.getParameter("firstName");
String lastName=request.getParameter("lastName");
int age=Integer.parseInt(request.getParameter("age"));
*/
BufferedReader br=request.getReader();
String line;
StringBuffer sb=new StringBuffer();
while(true)
{
line=br.readLine();
if(line==null) break;
sb.append(line);
}
String rawData=sb.toString();
Gson gson=new Gson();
Customer c=gson.fromJson(rawData,Customer.class);
String firstName=c.firstName;
String lastName=c.lastName;
int age=c.age;
PrintWriter pw=response.getWriter();
response.setContentType("text/plain");
pw.print(firstName+","+lastName+","+age);
pw.flush();
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
public void doGet(HttpServletRequest request,HttpServletResponse response)
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