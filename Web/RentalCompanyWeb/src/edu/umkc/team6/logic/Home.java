package edu.umkc.team6.logic;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import edu.umkc.team6.database.Functions;
 
@ManagedBean
public class Home {

	private List<String> location;
	private String selectedLocation;
	private Date fromDate;
	private Date toDate;
	
	private Date timeFrom,timeTo;
	private String age;
	
	@EJB
	private Functions f = new Functions();
	@PostConstruct
	public void load() {
		location = f.getListOfLocations();
		System.out.println("hi");
		System.out.println("locations" + location.size());
		
				
	}
	
	public String search(){
		
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);

		session.setAttribute("location", getSelectedLocation());
		session.setAttribute("fromDate", getFromDate());
		session.setAttribute("timeFromDate", getTimeFrom());
		session.setAttribute("toDate", getToDate());
		session.setAttribute("timeToDate", getTimeTo());
		
		session.setAttribute("age", getAge());
		
		return "search";
	}
	
	public List<String> getLocation() {
		return location;
	}

	public void setLocation(List<String> location) {
		this.location = location;
	}

	public String getSelectedLocation() {
		return selectedLocation;
	}

	public void setSelectedLocation(String selectedLocation) {
		this.selectedLocation = selectedLocation;
	}
	
	public Date getTimeFrom() {
		return timeFrom;
	}
	public void setTimeFrom(Date timeFrom) {
		this.timeFrom = timeFrom;
	}
	public Date getTimeTo() {
		return timeTo;
	}
	public void setTimeTo(Date timeTo) {
		this.timeTo = timeTo;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	
}
