package co.edu.unbosque.model;

import java.io.Serializable;

/**
 * This is the main and only bean of the application, holds the user data.
 * 
 * @author Johann Toncon
 *
 */
public class Person implements Serializable {

	private String name;
	private String phoneNum;
	private String email;
	private int id;

	public Person(String name, String phoneNum, String email, int id) {
		this.name = name;
		this.phoneNum = phoneNum;
		this.email = email;
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
