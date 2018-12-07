package carhireapp;

import java.util.Date;

public interface CarRental {
	
	/**
	 * The following method creates car reservation based on carType, startDate,
	 * licenseId and numberOfDays
	 * 
	 * @param carType
	 * @param startDate
	 * @param licenseId
	 * @param numberOfDays
	 */
	public void bookCar(carType carType, Date startDate, Long licenseId, int numberOfDays);	
}
