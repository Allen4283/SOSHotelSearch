package application;

import java.time.LocalDate;


/**
 * Desc: allows the user to make a reservation.
 */
public class Reservation {
  private Hotel hotel;
  private LocalDate checkInDate;
  private LocalDate checkOutDate;
  private int numberOfRooms;
  private int numberOfNights;
  private int finalCost;

  /**
   * Desc: sets the hotel, check-in date, check-out date, and number of rooms
   * for the current reservation, and calculates the final cost.
   *
   * @param: hotel - the hotel selected by the user
   * @param: checkInDate - the check-in date chosen by the user
   * @param: checkOutDate - the check-out date chosen by the user
   * @param: numberOfRooms - the number of rooms chosen by the user
   */
  public Reservation(Hotel hotel, LocalDate checkInDate,
                     LocalDate checkOutDate, int numberOfRooms) {

    this.hotel = hotel;
    this.checkInDate = checkInDate;
    this.checkOutDate = checkOutDate;
    this.numberOfRooms = numberOfRooms;
    this.numberOfNights = Math.abs(checkInDate.compareTo(checkOutDate));
    /*calculates final cost by multiplying
     the hotel price, # of rooms, and # of nights*/
    this.finalCost = hotel.getPrice() * numberOfRooms * numberOfNights;

  }

  /**
   * Desc: gets the current hotel.
   *
   * @return: hotel - the current hotel
   */
  public Hotel getHotel() {
    return hotel;
  }

  /**
   * Desc: sets the hotel for the reservation.
   *
   * @param: hotel - the current hotel
   */
  public void setHotel(Hotel hotel) {
    this.hotel = hotel;
  }

  /**
   * Desc: gets the check-in date.
   *
   * @return: checkInDate - check-in date
   */
  public LocalDate getCheckInDate() {
    return checkInDate;
  }

  /**
   * Desc: sets the check-in date.
   *
   * @param: checkInDate - check-in date
   */
  public void setCheckInDate(LocalDate checkInDate) {
    this.checkInDate = checkInDate;
  }

  /**
   * Desc: gets the check-out date.
   *
   * @return: checkOutDate - check-out date
   */
  public LocalDate getCheckOutDate() {
    return checkOutDate;
  }

  /**
   * Desc: sets the check-out date.
   *
   * @param: checkOutDate - check-out date
   */
  public void setCheckOutDate(LocalDate checkOutDate) {
    this.checkOutDate = checkOutDate;
  }

  /**
   * Desc: gets the number of rooms.
   *
   * @return: numberOfRooms - numberOfRooms
   */
  public int getNumberOfRooms() {
    return numberOfRooms;
  }

  /**
   * Desc: sets the number of rooms.
   *
   * @param: numberOfRooms - number of rooms.
   */
  public void setNumberOfRooms(int numberOfRooms) {
    this.numberOfRooms = numberOfRooms;
  }

  /**
   * Desc: calculates the number of nights by finding the difference
   * between the check-in date and check-out date.
   *
   * @param: date1 - check-in date
   * @param: date2 - check-out date
   */
  public void setNumberOfNights(LocalDate date1, LocalDate date2) {
    this.numberOfNights = Math.abs(date1.compareTo(date2));
  }

  /**
   * Desc: gets the number of nights.
   *
   * @return: numberOfNights - the difference between the check-in date and check-out date.
   */
  public int getNumberOfNights() {
    return this.numberOfNights;
  }

  /**
   * Desc: gets the final cost.
   *
   * @return: finalCost - the total cost based on the price, number of rooms and number of nights
   */
  public int getFinalCost() {
    return finalCost;
  }

  /**
   * Desc: writes a string that lists the reservation information.
   *
   * @return: a string that writes the hotel information.
   */
  @Override
  public String toString() {
    return "Reservation{"
        + "hotel=" + hotel
        + ", checkInDate=" + checkInDate
        + ", checkOutDate=" + checkOutDate
        + ", numberOfRooms=" + numberOfRooms
        + ", numberOfNights=" + numberOfNights
        + ", finalCost=" + finalCost
        + '}';
  }
}
