package com.mg.hr.dl;
import java.math.*;
import java.util.*;
public class EmployeeDTO implements java.io.Serializable,Comparable<EmployeeDTO>
{
private String employeeId;
private String name;
private int designationCode;
private String designation;
private Date dateOfBirth;
private String gender;
private Boolean isIndian;
private BigDecimal basicSalary;
private String panNumber;
private String aadharCardNumber;

public EmployeeDTO()
{
this.employeeId="";
this.name="";
this.designationCode=0;
this.designation="";
this.dateOfBirth=null;
this.gender="";
this.isIndian=null;
this.basicSalary=null;
this.panNumber="";
this.aadharCardNumber="";
}
public void setEmployeeId(java.lang.String employeeId)
{
this.employeeId=employeeId;
}
public java.lang.String getEmployeeId()
{
return this.employeeId;
}
public void setName(java.lang.String name)
{
this.name=name;
}
public java.lang.String getName()
{
return this.name;
}
public void setDesignationCode(int designationCode)
{
this.designationCode=designationCode;
}
public int getDesignationCode()
{
return this.designationCode;
}
public void setDesignation(java.lang.String designation)
{
this.designation=designation;
}
public java.lang.String getDesignation()
{
return this.designation;
}
public void setDateOfBirth(java.util.Date dateOfBirth)
{
this.dateOfBirth=dateOfBirth;
}
public java.util.Date getDateOfBirth()
{
return this.dateOfBirth;
}
public void setGender(java.lang.String gender)
{
this.gender=gender;
}
public java.lang.String getGender()
{
return this.gender;
}
public void setIsIndian(java.lang.Boolean isIndian)
{
this.isIndian=isIndian;
}
public java.lang.Boolean getIsIndian()
{
return this.isIndian;
}
public void setBasicSalary(java.math.BigDecimal basicSalary)
{
this.basicSalary=basicSalary;
}
public java.math.BigDecimal getBasicSalary()
{
return this.basicSalary;
}
public void setPANNumber(java.lang.String panNumber)
{
this.panNumber=panNumber;
}
public java.lang.String getPANNumber()
{
return this.panNumber;
}
public void setAadharCardNumber(java.lang.String aadharCardNumber)
{
this.aadharCardNumber=aadharCardNumber;
}
public java.lang.String getAadharCardNumber()
{
return this.aadharCardNumber;
}

public int hashCode()
{
return this.employeeId.hashCode();
}
public boolean equals(Object object)
{
if(!(object instanceof EmployeeDTO)) return false;
EmployeeDTO emp=(EmployeeDTO)object;
return this.employeeId.equalsIgnoreCase(emp.getEmployeeId());
}
public int compareTo(EmployeeDTO employeeDTO)
{
return this.employeeId.compareTo(employeeDTO.getEmployeeId());
}
}