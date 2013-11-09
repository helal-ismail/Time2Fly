package com.countyapps.model;

public class Record {

	public String name;
	public String vehicle;
	public String id;
	public double amount;
	public String vin;
	public boolean is_suspended;
	public boolean is_renewed;
	public boolean can_renew;
	public String expiration_date;
	public String street_address;
	public String city;
	public String state;
	public String zip;

	public Record(String name, String vehicle, String id, double amount,
			String vin, boolean is_suspended, boolean is_renewed,
			boolean can_renew, String expiration_date, String street_address,
			String city, String state, String zip) {
		super();
		this.name = name;
		this.vehicle = vehicle;
		this.id = id;
		this.amount = amount;
		this.vin = vin;
		this.is_suspended = is_suspended;
		this.is_renewed = is_renewed;
		this.can_renew = can_renew;
		this.expiration_date = expiration_date;
		this.street_address = street_address;
		this.city = city;
		this.state = state;
		this.zip = zip;
	}

}
