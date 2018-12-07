package carhireapp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;



public class CarRentalServiceImpl implements CarRental 
{
	@Override
	public void bookCar(carType carType, Date startDate, Long licenseId, int numberOfDays) 
	{
		if (carType != null && startDate != null && licenseId != null) 
		{
			Date newEndDate = getIncrementedDate(startDate, numberOfDays);	
			boolean isAvailable = this.checkForCarAvailabilty(carType);
			
			if (isAvailable) 
			{	
				boolean isBookingAvailable = this.checkIfCarHasBeenBookedForNonOverlappingTimeFrames(carType, startDate,
						licenseId, numberOfDays, newEndDate);

				if (isBookingAvailable) 
				{		
					this.createReservation(carType, startDate, licenseId, numberOfDays);
					System.out.println("Your car has been booked from " + startDate + " to " + newEndDate);
				}
			}
		}

	}

	
	/**
	 * The following method checks if the car has been booked for non overlapping
	 * time frames.
	 * 
	 * @param carType
	 * @param startDate
	 * @param licenseId
	 * @param numberOfDays
	 * @return
	 */
	private boolean checkIfCarHasBeenBookedForNonOverlappingTimeFrames(carType carType, Date startDate, Long licenseId,
			int numberOfDays, Date newEndDate) 
	{
		boolean bookingOverlapped = true;
		Date endDate = getIncrementedDate(startDate, numberOfDays);
		Date previousStartDate = null;
		Date previousEndDate = null;

		for (int i = 0; i < numberOfDays; i++) 
		{
			this.initiallReservations();
			if (reservations.containsKey(licenseId)) 
			{
				List<reservation> reservationList = reservations.get(licenseId);

				for (reservation reservation : reservationList) 
				{
					previousStartDate = reservation.getStartDate();
					previousEndDate = this.getIncrementedDate(previousStartDate, reservation.getNumberOfDays());
					startDate = getDateWithoutTime(startDate);
					endDate = getDateWithoutTime(endDate);
					previousStartDate = getDateWithoutTime(previousStartDate);
					previousEndDate = getDateWithoutTime(previousEndDate);

					if (startDate.after(previousStartDate) && endDate.before(previousEndDate)
							|| startDate.equals(previousStartDate) && endDate.equals(previousEndDate)) 
					{
						System.out.println("Car Has been already booked for this Dates");
						return false;
					}
				}

			}
		}

		return bookingOverlapped;
	}


	/**
	 * @param startDate
	 * @return
	 */
	private Date getDateWithoutTime(Date startDate) 
	{
		Calendar cal = Calendar.getInstance();
		 cal.setTime(startDate);
		 cal.set(Calendar.HOUR_OF_DAY, 0);
		 cal.set(Calendar.MINUTE, 0);
		 cal.set(Calendar.SECOND, 0);
		 cal.set(Calendar.MILLISECOND, 0);
		 startDate = cal.getTime();
		return startDate;
	}

	/**
	 * The following method creates car reservation for the following car type and
	 * on the start date
	 * 
	 * @param carType
	 * @param startDate
	 * @param licenseId
	 * @param numberOfDays
	 * @return
	 */
	private boolean createReservation(carType carType, Date startDate, Long licenseId, int numberOfDays) 
	{
		reservation reservation = new reservation(startDate, numberOfDays, carType);

		for (int i = 0; i < numberOfDays; i++) 
		{

			List<reservation> reservationList = new LinkedList<>();
			reservationList.add(reservation);
			reservations.put(licenseId, reservationList);
		}
		return true;
	}
		
	
	/**
	 * The following method checks for the number cars available
	 * 
	 * @param carType
	 * @param startDate
	 * @return
	 */
	private boolean checkForCarAvailabilty(carType carType) 
	{	
		boolean available = true;
		this.carsInitially();
		int count = availableCars.get(carType);

		if (count == 0) 
		{
			return false;
		} 
		return available;

	}

    public static Date getIncrementedDate(Date date, int days)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }

    public void carsInitially() {
        availableCars = new Hashtable<>();
        availableCars.put(carType.SEDAN, SEDAN_COUNT);
        availableCars.put(carType.SUV, SUV_COUNT);
        availableCars.put(carType.HATCHBACK, HATCHBACK_COUNT);
        reservations = new Hashtable<>();
    }

    public static Map<carType, Integer> getAvailableCars() 
    {
        return availableCars;
    }
    
	public void initiallReservations() 
	{
		reservations = new Hashtable<>();
		Calendar cal = Calendar.getInstance();
		Date startDate = cal.getTime();
		Long licenseId = new Long(12345);
		int numberOfDays = 5;
		reservation reservation = new reservation(startDate, numberOfDays, carType.SEDAN);
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		List<reservation> reservationList = new LinkedList<>();
		reservationList.add(reservation);
		reservations.put(licenseId, reservationList);
	}

    private static Map<carType, Integer> availableCars;
    private static Map<Long, List<reservation>> reservations;
    private static final int SEDAN_COUNT = 10;
    private static final int HATCHBACK_COUNT = 10;
    private static final int SUV_COUNT = 10;
	

}
