package carhireapp;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.Test;

class CarRentalServiceImplTest {

	/**
	 * The following test method would book car from start date to the end date
	 * 
	 */
	@Test
	void testBookCar() 
	{
	Calendar cal = Calendar.getInstance();
	Date startDate = cal.getTime();
	Long licenseId = new Long(1234);
	int numberOfDays = 6;
	carhireapp.carType c1 = carhireapp.carType.SUV;
	CarRentalServiceImpl rentalServiceImpl = new CarRentalServiceImpl();
  	rentalServiceImpl.bookCar(c1, startDate, licenseId, numberOfDays);
  	Date endDate = getIncrementedDate(startDate, numberOfDays);
    String messageBooked = "Your car has been booked from " +  startDate  + " to " +  endDate;
    assertEquals("Your car has been booked from " +  startDate  + " to " +  endDate, messageBooked);   
	}

	/**
	 * The following test method test if the customer has already booked for
	 * particular dates
	 * 
	 */
	@Test
	void testOverlappingTimeFrames() 
	{
	Calendar cal = Calendar.getInstance();
	Date startDate = cal.getTime();
	Long licenseId = new Long(12345);
	int numberOfDays = 5;
	carhireapp.carType c1 = carhireapp.carType.SEDAN;
	CarRentalServiceImpl rentalServiceImpl = new CarRentalServiceImpl();
  	rentalServiceImpl.bookCar(c1, startDate, licenseId, numberOfDays);
    String messageBooked = "Car Has been already booked for this Dates";
    assertEquals("Car Has been already booked for this Dates", messageBooked);   
	}

	public static Date getIncrementedDate(Date date, int days)
	   {
	        Calendar cal = Calendar.getInstance();
	        cal.setTime(date);
	        cal.add(Calendar.DATE, days);
	        return cal.getTime();
	    }
	
}
