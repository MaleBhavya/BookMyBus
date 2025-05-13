package com.tsrtc.online;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Bus {
	private int busId;
	private String busFrom;
	private String busTo;
	private LocalDate date;
	private int availableSeats;
	private int ticketCost;

	public Bus(int busId, String busFrom, String busTo, LocalDate date, int availableSeats, int ticketCost) {
		this.busId = busId;
		this.busFrom = busFrom;
		this.busTo = busTo;
		this.date = date;
		this.availableSeats = availableSeats;
		this.ticketCost = ticketCost;
	}

	public int getBusId() {
		return busId;
	}

	public void setBusId(int busId) {
		this.busId = busId;
	}

	public String getBusFrom() {
		return busFrom;
	}

	public void setBusFrom(String busFrom) {
		this.busFrom = busFrom;
	}

	public String getBusTo() {
		return busTo;
	}

	public void setBusTo(String busTo) {
		this.busTo = busTo;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public int getAvailableSeats() {
		return availableSeats;
	}

	public void setAvailableSeats(int availableSeats) {
		this.availableSeats = availableSeats;
	}

	public int getTicketCost() {
		return ticketCost;
	}

	public void setTicketCost(int ticketCost) {
		this.ticketCost = ticketCost;
	}

}

class Customer {
	private String customerName;
	private int customerPhoneNumber;

	public Customer(String customerName, int customerPhoneNumber) {
		this.customerName = customerName;
		this.customerPhoneNumber = customerPhoneNumber;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		customerName = customerName;
	}

	public int getCustomerPhoneNumber() {
		return customerPhoneNumber;
	}

	public void setCustomerPhoneNumber(int customerPhoneNumber) {
		this.customerPhoneNumber = customerPhoneNumber;
	}
}

class Booking {
	private Bus bus;
	private Customer customer;
	private String reservationId;
	private int seatsTakenByCustomer;

	public Booking(Bus bus, Customer customer, int seatsTakenByCustomer) {
		this.bus = bus;
		this.customer = customer;
		this.seatsTakenByCustomer = seatsTakenByCustomer;
	}

	public Bus getBus() {
		return bus;
	}

	public void setBus(Bus bus) {
		this.bus = bus;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getReservationId() {
		return reservationId;
	}

	public void setReservationId(String reservationId) {
		this.reservationId = reservationId;
	}

	public int getSeatsTakenByCustomer() {
		return seatsTakenByCustomer;
	}

	public void setSeatsTakenByCustomer(int seatsTakenByCustomer) {
		this.seatsTakenByCustomer = seatsTakenByCustomer;
	}

}

class MyAbhiBus {
	private List<Bus> Buses;
	private List<Customer> Customers;
	private List<Booking> Bookings;
	public void addBus(Bus bus)
	{
		this.Buses.add(bus);
	}
	Scanner sc = new Scanner(System.in);
    
	public MyAbhiBus() {
		this.Buses = new ArrayList<>();
		this.Customers = new ArrayList<>();
		this.Bookings = new ArrayList<>();
	}

	public void viewAvailableBuses() {
		System.out.println("Available Buses:");
		for (Bus bus : Buses) {
			System.out.println("Bus ID:" + bus.getBusId() + "| From:" + bus.getBusFrom() + "| To:" + bus.getBusTo()
					+ "| Date:" + bus.getDate() + "| Available Seats:" + bus.getAvailableSeats());
		}
	}

	public void searchBusesInRoute() {
		System.out.println("Enter source location:");
	    String sourceLocation = sc.nextLine();
	    System.out.println("Enter destination location:");
	    String destinationLocation = sc.nextLine();
	    System.out.println("Enter travel date (YYYY-MM-DD):");
	    String travelDateStr = sc.nextLine();

	    LocalDate travelDate;
	    try {
	        travelDate = LocalDate.parse(travelDateStr);
	    } catch (Exception e) {
	        System.out.println("Invalid date format. Please use YYYY-MM-DD.");
	        return;
	    }

	    System.out.println("Matching Buses:");
	    boolean found = false;
	    for (Bus bus : Buses) {
	        if (bus.getBusFrom().equalsIgnoreCase(sourceLocation)
	                && bus.getBusTo().equalsIgnoreCase(destinationLocation)
	                && bus.getDate().equals(travelDate)) {
	            System.out.println("Bus ID: " + bus.getBusId() + " | Available Seats: "
	                    + bus.getAvailableSeats() + " | Fare: $" + bus.getTicketCost());
	            found = true;
	        }
	    }
	    if (!found) {
	        System.out.println("No buses found for the given route and date.");
	    }
	}

	public void bookTicket() {
		System.out.println("Enter Bus ID to book:");
		int selectedBusID = sc.nextInt();
		sc.nextLine();
		System.out.println("Enter your name:");
		String name = sc.nextLine();
		
		System.out.println("Enter your phone Number");
		int phoneNumber = sc.nextInt();
		System.out.println("Enter number of seats:");
		int noOfSeatsRequired = sc.nextInt();
		Bus selectedBus = null;
		for (Bus bus : Buses) {
			if (selectedBusID == bus.getBusId()) {
				selectedBus = bus;
				break;
			}
		}
		if (selectedBus != null && selectedBus.getAvailableSeats() > noOfSeatsRequired) {
			System.out.println("Total Fare: $" + selectedBus.getTicketCost() * noOfSeatsRequired);
			System.out.println("Confirm booking (Y/N)?:");
			sc.nextLine();
			String confirm = sc.nextLine();
			
			if (confirm.equalsIgnoreCase("Y")) {
				Customer newCustomer = new Customer(name, phoneNumber);
				Customers.add(newCustomer);
				Booking newBooking = new Booking(selectedBus, newCustomer, noOfSeatsRequired);
				Bookings.add(newBooking);
				selectedBus.setAvailableSeats(selectedBus.getAvailableSeats() - noOfSeatsRequired);
				newBooking.setReservationId("R" + Bookings.size());
				System.out.println("Booking successful! Your Reservation ID is: " + newBooking.getReservationId());
			} else {
				System.out.println("We offer the best price compared to other websites. Are you sure you want to cancel?");
			}
		} else {
			System.out.println("The selected Bus Id is not available or there no seats available ");
		}
	}

	public void cancelTicket() {
		System.out.println("Enter the ReservationId:");
		String reserveId = sc.nextLine();
		Booking cancelBooking = null;
		for (Booking booking : Bookings) {
			if (booking.getReservationId().equals(reserveId)) {

				cancelBooking = booking;
				break;
			}
		}
		if (cancelBooking != null) {
			Customer tobeRemovedCustomer = cancelBooking.getCustomer();
			Bus toAddSeatsBus = cancelBooking.getBus();
			int ticketsToAdd = cancelBooking.getSeatsTakenByCustomer();
			System.out.println("Booking found:");
			System.out.println("Passenger Name:" + tobeRemovedCustomer.getCustomerName() + "| Bus ID:"
					+ toAddSeatsBus.getBusId() + "| Seats:" + ticketsToAdd);
			System.out.println(" ");
			System.out.println("Are you sure you want to cancel? (Y/N):");
			String cancel = sc.nextLine();
			if (cancel.equalsIgnoreCase("Y")) {
				toAddSeatsBus.setAvailableSeats(toAddSeatsBus.getAvailableSeats() + ticketsToAdd);
				Customers.remove(tobeRemovedCustomer);
				Bookings.remove(cancelBooking);
				System.out.println("Ticket canceled successfully.");
			} else {
				System.out.println("Thank you for staying with us");
			}
		} else {
			System.out.println("No ticket is booked on this ID. Please check and try again!");
		}
	}

	public void viewBookings() {
		System.out.println("Enter your name:");
		String enteredName = sc.nextLine();
		System.out.println("Enter your ReservationID:");
		String reservationID = sc.nextLine();
		Booking viewbooking = null;
		for (Booking booking : Bookings) {
			if (booking.getCustomer().getCustomerName().equals(enteredName)
					&& booking.getReservationId().equals(reservationID)) {
				viewbooking = booking;
				break;
			}
		}
		if(viewbooking!=null)
		{
			System.out.println("Reservation ID:" + reservationID + "| BUS ID:" + viewbooking.getBus().getBusId()
					+ "| From: " + viewbooking.getBus().getBusFrom() + "| To: " + viewbooking.getBus().getBusTo()
					+ "| Date: " + viewbooking.getBus().getDate() + "| Seats:" + viewbooking.getSeatsTakenByCustomer());
		}
		else
		{
			System.out.println("No such reservation found.");
		}
	}

	public void menu() {

		while (true) {
			System.out.println("==== Welcome to the Bus Reservation System ====");
			System.out.println("1. View All Available Buses");
			System.out.println("2. Search Buses by Route and Date");
			System.out.println("3. Book a Ticket");
			System.out.println("4. Cancel a Ticket");
			System.out.println("5. View My Bookings");
			System.out.println("6.Exit");
			System.out.println("      ");
			System.out.println("Enter your choice:");
			int choice = sc.nextInt();
			sc.nextLine();
			if (choice == 1) {
				viewAvailableBuses();

			} else if (choice == 2) {

				searchBusesInRoute();

			} else if (choice == 3) {
				bookTicket();
			} else if (choice == 4) {
				cancelTicket();

			} else if (choice == 5) {
				viewBookings();

			} else if (choice == 6) {
				System.exit(0);
			} else {
				System.out.println("Invalid choice. Choose the correct choice");
			}
		}
	}

}

public class BookMyBus {

	public static void main(String[] args) {
		
		MyAbhiBus abhibus=new MyAbhiBus();
		
		Bus b1 = new Bus(101, "New York", "Washington DC", LocalDate.of(2025, 5, 15), 40, 30);
		Bus b2 = new Bus(102, "Boston", "New York", LocalDate.of(2025, 5, 16), 35, 25);
		Bus b3 = new Bus(103, "Chicago", "Detroit", LocalDate.of(2025, 5, 17), 50, 40);
		Bus b4 = new Bus(104, "San Francisco", "Los Angeles", LocalDate.of(2025, 5, 18), 45, 55);
		Bus b5 = new Bus(105, "Seattle", "Portland", LocalDate.of(2025, 5, 19), 38, 28);
		Bus b6 = new Bus(106, "Houston", "Dallas", LocalDate.of(2025, 5, 20), 42, 35);
		Bus b7 = new Bus(107, "Atlanta", "Orlando", LocalDate.of(2025, 5, 21), 48, 32);
		Bus b8 = new Bus(108, "Phoenix", "Las Vegas", LocalDate.of(2025, 5, 22), 30, 45);
		Bus b9 = new Bus(109, "Philadelphia", "Pittsburgh", LocalDate.of(2025, 5, 23), 36, 38);
		Bus b10 = new Bus(110, "Denver", "Salt Lake City", LocalDate.of(2025, 5, 24), 33, 50);
		Bus b11 = new Bus(111, "Denver", "Salt Lake City", LocalDate.of(2025, 5, 24), 20, 40);
		abhibus.addBus(b1);
		abhibus.addBus(b2);
		abhibus.addBus(b3);
		abhibus.addBus(b4);
		abhibus.addBus(b5);
		abhibus.addBus(b6);
		abhibus.addBus(b7);
		abhibus.addBus(b8);
		abhibus.addBus(b9);
		abhibus.addBus(b10);
		
		abhibus.menu();

	}

}
