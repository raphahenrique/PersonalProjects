package edu.umkc.team6.logic;


import java.io.Serializable;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.umkc.team6.database.Functions;

@ManagedBean
public class SearchDataGrid implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<Car> cars;

	private Car selectedCar;
	private int numberOfDays;
	
	@EJB
	private Functions f = new Functions();

	@PostConstruct
	public void load() {
		
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		
		String current_location = (String) session.getAttribute("location");
		Date current_fromDate = (Date) session.getAttribute("fromDate");
		Date current_timeFromDate = (Date) session.getAttribute("timeFromDate");
		Date current_toDate = (Date) session.getAttribute("toDate");
		Date current_timeToDate = (Date) session.getAttribute("timeToDate");
		drivers_age = (String) session.getAttribute("age");
		
		cars = f.getListOfCars(current_location, current_fromDate,
				current_timeFromDate, current_toDate, current_timeToDate);
		System.out.println("loading data...");
		System.out.println("how many cars > " + cars.size());

		
		SimpleDateFormat formater=new SimpleDateFormat("yyyy-MM-dd");
		String fromDate = formater.format(current_fromDate);
		String toDate = formater.format(current_toDate);
		
		Date d1 = new Date();
		Date d2 = new Date();
		try {
			d1 = new SimpleDateFormat("yyyy-M-dd").parse(fromDate);
			d2 = new SimpleDateFormat("yyyy-M-dd").parse(toDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		long diff = d2.getTime() - d1.getTime();
		numberOfDays =  (int) (diff / (1000 * 60 * 60 * 24));
		System.out.println("numb of days " + numberOfDays);
		System.out.println("Difference between  " + d1 + " and " + d2 + " is "
				+ (diff / (1000 * 60 * 60 * 24)) + " days.");
		
	}

	// retrieving parameters
/*
	FacesContext fc = FacesContext.getCurrentInstance();
	Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
*/

	public String finish(){
		
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);

		session.setAttribute("sel_id_car", selectedCar.getId_car());
		session.setAttribute("sel_car_type",selectedCar.getCar_type());
		session.setAttribute("sel_model", selectedCar.getModel());
		
		session.setAttribute("sel_price", selectedCar.getPrice());
		
		session.setAttribute("numberOfDays", getNumberOfDays());
		
		return "finish";
	}
	
	
	public int getNumberOfDays() {
		return numberOfDays;
	}

	public void setNumberOfDays(int numberOfDays) {
		this.numberOfDays = numberOfDays;
	}


	private String drivers_age;
	private int id_car;
	private String car_type;
	private String model;
	private String price;

	
	public int getId_car() {
		return id_car;
	}

	public void setId_car(int id_car) {
		this.id_car = id_car;
	}

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

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public List<Car> getCars() {
		return cars;
	}

	public Car getSelectedCar() {
		return selectedCar;
	}

	public void setSelectedCar(Car selectedCar) {
		this.selectedCar = selectedCar;
	}

}
