package com.memberservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by szagoret
 */
@Entity
public class Member {

	@Id @GeneratedValue private Long id;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "birthday")
	private Date birthday;

	@Column(name = "zip_code")
	private String zipCode;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Member member = (Member) o;

		if (id != null ? !id.equals(member.id) : member.id != null)
			return false;
		if (firstName != null ? !firstName.equals(member.firstName) : member.firstName != null)
			return false;
		if (lastName != null ? !lastName.equals(member.lastName) : member.lastName != null)
			return false;
		if (birthday != null ? !birthday.equals(member.birthday) : member.birthday != null)
			return false;
		return zipCode != null ? zipCode.equals(member.zipCode) : member.zipCode == null;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
		result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
		result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
		result = 31 * result + (zipCode != null ? zipCode.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "Member{" + "id=" + id + ", firstName='" + firstName + '\'' + ", lastName='" + lastName + '\''
				+ ", birthday=" + birthday + ", zipCode='" + zipCode + '\'' + '}';
	}
}
