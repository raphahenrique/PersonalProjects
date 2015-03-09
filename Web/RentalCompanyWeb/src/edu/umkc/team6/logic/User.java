package edu.umkc.team6.logic;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import edu.umkc.team6.database.Functions;

public class User {

	private String firstName;
	private String middleName;
	private String lastName;
	private String licenseNumber;
	private int numberOfDays;
	private double finalPrice;
	private double priceDay;
	private String formattedDatePickup;
	private String formattedDateReturn;
	private String age;
	private int id_car;
	
	@EJB
	private Functions f = new Functions();
	

	@PostConstruct
	public void load() {

		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(
				false);
		System.out.println("here");
		age = (String) session.getAttribute("age");
		Date current_fromDate = (Date) session.getAttribute("fromDate");
		Date current_timeFromDate = (Date) session.getAttribute("timeFromDate");
		Date current_toDate = (Date) session.getAttribute("toDate");
		Date current_timeToDate = (Date) session.getAttribute("timeToDate");
		
		
		
		Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String fromDate = formatter.format(current_fromDate);
		String timeFromDate = formatter.format(current_timeFromDate);
		String toDate = formatter.format(current_toDate);
		String timeToDate = formatter.format(current_timeToDate);

		formattedDatePickup = fromDate.substring(0, 10) + " "
				+ timeFromDate.substring(11, timeFromDate.length() - 1);
		formattedDateReturn = toDate.substring(0, 10) + " "
				+ timeToDate.substring(11, 18);

		id_car = (int) session.getAttribute("sel_id_car");
		
		
		
		String priceDay = (String) session.getAttribute("sel_price");
		
		this.numberOfDays = (int) session.getAttribute("numberOfDays");
		this.priceDay = Double.parseDouble(priceDay);

		this.finalPrice = this.numberOfDays * this.priceDay;

	}

	public String end() {
		return "end";
	}
	public void insert() {
		f.insertCustomer(getFirstName(), getMiddleName(), getLastName(), age, getLicenseNumber());
		f.insertRental(getId_car(), getFormattedDatePickup(), getFormattedDateReturn(),getFinalPrice());
		System.out.println("done");
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public int getId_car() {
		return id_car;
	}

	public void setId_car(int id_car) {
		this.id_car = id_car;
	}

	public String getFormattedDatePickup() {
		return formattedDatePickup;
	}

	public void setFormattedDatePickup(String formattedDatePickup) {
		this.formattedDatePickup = formattedDatePickup;
	}

	public String getFormattedDateReturn() {
		return formattedDateReturn;
	}

	public void setFormattedDateReturn(String formattedDateReturn) {
		this.formattedDateReturn = formattedDateReturn;
	}

	public double getPriceDay() {
		return priceDay;
	}

	public void setPriceDay(double priceDay) {
		this.priceDay = priceDay;
	}

	public double getFinalPrice() {
		return finalPrice;
	}

	public void setFinalPrice(double finalPrice) {
		this.finalPrice = finalPrice;
	}

	public int getNumberOfDays() {
		return numberOfDays;
	}

	public void setNumberOfDays(int numberOfDays) {
		this.numberOfDays = numberOfDays;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLicenseNumber() {
		return licenseNumber;
	}

	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}

}
