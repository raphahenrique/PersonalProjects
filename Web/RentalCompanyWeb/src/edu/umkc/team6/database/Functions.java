package edu.umkc.team6.database;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.umkc.team6.logic.Car;

public class Functions implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<String> listOfLocations = new ArrayList<String>();

	private List<Car> listOfCars = new ArrayList<Car>();

	private Statement stmt;

	private String id_customer;

	public Functions() {

		JDBCConnection cCon = new JDBCConnection();
		stmt = cCon.getStatement();

	}

	public void loadLocations() {
		String sqlCmd1 = "SELECT * FROM \"office\"";

		try {

			ResultSet rs = stmt.executeQuery(sqlCmd1);

			while (rs.next()) {
				int id_office = rs.getInt("id_office");
				String city_name = rs.getString("city");
				String state_name = rs.getString("state");

				listOfLocations.add(city_name + "-" + state_name);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<String> getListOfLocations() {
		loadLocations();
		return listOfLocations;
	}

	public List<Car> getListOfCars(String current_location,
			Date current_fromDate, Date current_timeFromDate,
			Date current_toDate, Date current_timeToDate) {

		Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String fromDate = formatter.format(current_fromDate);
		String timeFromDate = formatter.format(current_timeFromDate);
		String toDate = formatter.format(current_toDate);
		String timeToDate = formatter.format(current_timeToDate);

		String formattedDatePickup = fromDate.substring(0, 10) + " "
				+ timeFromDate.substring(11, timeFromDate.length() - 1);
		String formattedDateReturn = toDate.substring(0, 10) + " "
				+ timeToDate.substring(11, 18);

		String formattedLocation = current_location.substring(0,
				current_location.length() - 3);
		System.out.println("location city " + formattedLocation);
		String sqlCmd1 = "SELECT name_cartype,model, price_cartype FROM \"car\""
				+ "INNER JOIN \"carhistory\" "
				+ "ON car.id_car = carhistory.id_car"
				+ " INNER JOIN \"office\""
				+ " ON car.id_office = office.id_office"
				+ " INNER JOIN \"cartype\" "
				+ "ON car.id_cartype = cartype.id_cartype "
				+ " INNER JOIN \"rental\" ON car.id_car = rental.id_car"
				+ " WHERE carhistory.state = 'Available' and city = '"
				+ formattedLocation
				+ "'"
				+ " and ((pickuptime > '"
				+ formattedDatePickup
				+ "' and returntime > '"
				+ formattedDateReturn
				+ "') "
				+ " or (pickuptime < '"
				+ formattedDatePickup
				+ "' and returntime < '"
				+ formattedDatePickup + "'))";

		try {

			ResultSet rs = stmt.executeQuery(sqlCmd1);

			while (rs.next()) {
				String car_name = rs.getString("model");
				String car_type = rs.getString("name_cartype");
				String price = rs.getString("price_cartype");

				Car newCar = new Car();
				// newCar.set....
				newCar.setModel(car_name);
				newCar.setCar_type(car_type);

				newCar.setPrice(price);// format prices that are e.g. 10.04936

				listOfCars.add(newCar);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return listOfCars;
	}

	public void insertCustomer(String firstname, String middlename,
			String lastname, String age, String license) {
		String sqlCmd1 = "insert into customer (firstname, middlename, lastname, age, license)"
				+ " values ('"
				+ firstname
				+ "', '"
				+ middlename
				+ "', '"
				+ lastname + "', " + age + ", '" + license + "');";

		String sqlCmd2 = "select id_customer from customer where license='"
				+ license + "';";

		try {
			stmt.executeQuery(sqlCmd1);

			ResultSet rs = stmt.executeQuery(sqlCmd2);

			while (rs.next()) {

				id_customer = rs.getString("id_customer");

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void insertRental(int id_car,
			String pickuptime, String returntime, double finalPrice) {

		String sqlCmd3 = "insert into rental (id_customer, id_car, payment, pickuptime, returntime,price) "
				+ "values ("+id_customer+","+id_car+",'Credit','"+pickuptime+"','"+returntime+"',"+finalPrice+");";

		try {

			stmt.executeQuery(sqlCmd3);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
