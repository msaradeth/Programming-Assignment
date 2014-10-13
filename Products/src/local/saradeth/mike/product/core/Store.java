package local.saradeth.mike.product.core;

import java.io.Serializable;

public class Store implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5174933149656027762L;
	private int storeId;
	private String storeName;
	private Address address = new Address();	
	
	public Store(String storeName, Address address) {
		this.storeName = storeName;
		this.address = address;
	}	
	
	public Store() {
		// TODO Auto-generated constructor stub
	}

	public String getStoreName() {
		return storeName;
	}


	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}


	public Address getAddress() {
		return address;
	}


	public void setAddress(Address address) {
		this.address = address;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	
}
