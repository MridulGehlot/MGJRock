package com.mg.hr.dl;
public class AdministratorDTO implements java.io.Serializable,Comparable<AdministratorDTO>
{
private String uname;
private String pwd;
public AdministratorDTO()
{
this.uname=null;
this.pwd=null;
}

public boolean equals(Object object)
{
if(!(object instanceof AdministratorDTO)) return false;
AdministratorDTO other=(AdministratorDTO)object;
return this.uname.equalsIgnoreCase(other.getUname());
}
public int compareTo(AdministratorDTO other)
{
return this.uname.compareTo(other.getUname());
}
public int hashCode()
{
return this.uname.hashCode();
}
public void setUname(String uname)
{
this.uname=uname;
}
public String getUname()
{
return this.uname;
}
public void setPwd(String pwd)
{
this.pwd=pwd;
}
public String getPwd()
{
return this.pwd;
}
}