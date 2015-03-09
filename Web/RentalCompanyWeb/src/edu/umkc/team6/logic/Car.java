package edu.umkc.team6.logic;

import java.io.Serializable;

public class Car implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id_car;
	private int id_cartype;
	private int id_office;
	private byte picture;
	private String color;
	private String make;
	private String car_type;
	private String model;
	private String price;
	
	
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getCar_type() {
		return car_type;
	}
	public void setCar_type(String car_type) {
		this.car_type = car_type;
	}
	public int getId_car() {
		return id_car;
	}
	public void setId_car(int id_car) {
		this.id_car = id_car;
	}
	public int getId_cartype() {
		return id_cartype;
	}
	public void setId_cartype(int id_cartype) {
		this.id_cartype = id_cartype;
	}
	public int getId_office() {
		return id_office;
	}
	public void setId_office(int id_office) {
		this.id_office = id_office;
	}
	public byte getPicture() {
		return picture;
	}
	public void setPicture(byte picture) {
		this.picture = picture;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getMake() {
		return make;
	}
	public void setMake(String make) {
		this.make = make;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	
}
