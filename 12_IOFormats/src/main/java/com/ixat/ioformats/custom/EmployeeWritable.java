package com.ixat.ioformats.custom;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Enumeration;

import nanoxml.XMLElement;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;

public class EmployeeWritable implements WritableComparable<EmployeeWritable> {

	private LongWritable employeeId;
	private Text ename;
	private Text job;
	private Text email;
	private DoubleWritable salary;
	
	public EmployeeWritable() {
		
		this(new LongWritable(), new Text(), new Text(), new Text(), new DoubleWritable());
		
	}
	
	
	public EmployeeWritable(LongWritable employeeId, Text ename, Text job,
			Text email, DoubleWritable salary) {
		super();
		this.employeeId = employeeId;
		this.ename = ename;
		this.job = job;
		this.email = email;
		this.salary = salary;
	}


	public EmployeeWritable(long employeeId, String ename, String job, String email, double salary){
		
		this(new LongWritable(employeeId), new Text(ename), new Text(job), new Text(email), new DoubleWritable(salary));
		
	}

	
	public void setFieldsfromXML(String xml){
		
		try{
			
			XMLElement element  = new XMLElement();
			element.parseString(xml);
		    Enumeration childs = element.enumerateChildren();
	        while(childs.hasMoreElements()){
	        	XMLElement child = (XMLElement) childs.nextElement();
	        	String tagName = child.getName();
	        	String value = child.getContent();
	        	if(tagName.equals("EmployeeId"))
	        	{
	        		setEmployeeId(new LongWritable(Long.parseLong(value)));
	        	}else if(tagName.equals("Name"))
	        	{
	        		setEname(new Text(value));
	        	}else if(tagName.equals("Job"))
	        	{
	        		setJob(new Text(value));
	        	}
	        	else if(tagName.equals("Email"))
	        	{
	        		setEmail(new Text(value));
	        	}else if(tagName.equals("Salary")){
	        		setSalary(new DoubleWritable(Double.parseDouble(value)));
	        	}
	        }
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	@Override
	public String toString() {
		return "Employee [employeeId=" + employeeId + ", ename="
				+ ename + ", job=" + job + ", email=" + email + ", salary="
				+ salary + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result
				+ ((employeeId == null) ? 0 : employeeId.hashCode());
		result = prime * result + ((ename == null) ? 0 : ename.hashCode());
		result = prime * result + ((job == null) ? 0 : job.hashCode());
		result = prime * result + ((salary == null) ? 0 : salary.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		EmployeeWritable other = (EmployeeWritable) obj;
		if (email == null) {
			if (other.email != null) {
				return false;
			}
		} else if (!email.equals(other.email)) {
			return false;
		}
		if (employeeId == null) {
			if (other.employeeId != null) {
				return false;
			}
		} else if (!employeeId.equals(other.employeeId)) {
			return false;
		}
		if (ename == null) {
			if (other.ename != null) {
				return false;
			}
		} else if (!ename.equals(other.ename)) {
			return false;
		}
		if (job == null) {
			if (other.job != null) {
				return false;
			}
		} else if (!job.equals(other.job)) {
			return false;
		}
		if (salary == null) {
			if (other.salary != null) {
				return false;
			}
		} else if (!salary.equals(other.salary)) {
			return false;
		}
		return true;
	}


	
	public void write(DataOutput out) throws IOException {
		// TODO Auto-generated method stub
		employeeId.write(out);
		ename.write(out);
		job.write(out);
		email.write(out);
		salary.write(out);
	}

	public void readFields(DataInput in) throws IOException {
		// TODO Auto-generated method stub
		employeeId.readFields(in);
		ename.readFields(in);
		job.readFields(in);
		email.readFields(in);
		salary.readFields(in);
		
	}

	public int compareTo(EmployeeWritable other) {
		// TODO Auto-generated method stub
		return employeeId.compareTo(other.getEmployeeId());
	}

	public LongWritable getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(LongWritable employeeId) {
		this.employeeId = employeeId;
	}

	public Text getEname() {
		return ename;
	}

	public void setEname(Text ename) {
		this.ename = ename;
	}

	public Text getJob() {
		return job;
	}

	public void setJob(Text job) {
		this.job = job;
	}

	public Text getEmail() {
		return email;
	}

	public void setEmail(Text email) {
		this.email = email;
	}

	public DoubleWritable getSalary() {
		return salary;
	}

	public void setSalary(DoubleWritable salary) {
		this.salary = salary;
	}
	

}
