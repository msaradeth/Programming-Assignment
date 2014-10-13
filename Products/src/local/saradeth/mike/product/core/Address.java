package local.saradeth.mike.product.core;

import java.io.Serializable;

public class Address implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3689664950185056050L;
	private int address_id; 
	private int store_id=0;
	private int product_id=0;
	private String addr="";
	private String city="";
	private String state="";
	private String zip="";
	private String phone="";

	public Address(String addr, String city, String state, String zip, String phone) {
		this.addr = addr;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.phone = phone;
		
	}
	
	
	public Address() {
			
		// TODO Auto-generated constructor stub
	}
	
	public String getAddr() {
		return addr;
	}


	public void setAddr(String addr) {
		this.addr = addr;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public String getZip() {
		return zip;
	}


	public void setZip(String zip) {
		this.zip = zip;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getAddress_id() {
		return address_id;
	}


	public void setAddress_id(int address_id) {
		this.address_id = address_id;
	}
	public int getStore_id() {
		return store_id;
	}


	public void setStore_id(int store_id) {
		this.store_id = store_id;
	}


	public int getProduct_id() {
		return product_id;
	}


	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}


	

}
