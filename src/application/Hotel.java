package application;

/**
 * Desc: class describing a hotel object.
 */

public class Hotel implements Comparable<Hotel> {
  private String hotelId;
  private String name;
  private double stars;
  private String city;
  private String countryCode;
  private String countryName;
  private double lat;
  private double lng;
  private int price;
  private static int numReservations = 0;

  /**
   * Desc: Hotel constructor.
   *
   * @param: hotelId - the hotel ID number.
   * @param: name - the hotel name.
   * @param: stars - the hotel star rating.
   * @param: city - the hotel city.
   * @param: countryCode - the hotel country abbreviation.
   * @param: countryName - the hotel country name.
   * @param: lat - the lattitude of the hotel location.
   * @param: lng - the longitute of the hotel location.
   * @param: price - the price of a hotel room.
   */
  public Hotel(int hotelId, String name, double stars, String city, String countryCode,
      String countryName, double lat, double lng, int price) {
    this.hotelId = Integer.toString(hotelId);
    this.name = name;
    this.stars = stars;
    this.city = city;
    this.countryCode = countryCode;
    this.countryName = countryName;
    this.lat = lat;
    this.lng = lng;
    this.price = price;
  }

  /**
   * Desc: sets the hotel ID.
   *
   * @param: id - the hotel id in the database.
   */
  public void setHotelId(int id) {
    this.hotelId = Integer.toString(id);
  }

  /**
   * Desc: gets the hotel ID.
   *
   * @return: hoteldId - the hotel ID number.
   */
  String getHotelId() {
    return hotelId;
  }

  /**
   * Desc: sets the hotel name.
   *
   * @param: name - the hotel name.
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Desc: sets the hotel star rating.
   *
   * @param: stars - the hotel star rating.
   */
  public void setStars(double stars) {
    this.stars = stars;
  }

  /**
   * Desc: sets the hotel city.
   *
   * @param: city - the hotel city.
   */
  public void setCity(String city) {
    this.city = city;
  }

  /**
   * Desc: sets the hotel country code.
   *
   * @param: countryCode - the hotel country abbreviation.
   */
  public void setCountryCode(String countryCode) {
    this.countryCode = countryCode;
  }

  /**
   * Desc: set hotel country name.
   *
   * @param: countryName - the name of the hotel country.
   */
  public void setCountryName(String countryName) {
    this.countryName = countryName;
  }

  /**
   * Desc: sets hotel lattitude.
   *
   * @param: lat - the lattitude of the hotel location.
   */
  public void setLat(double lat) {
    this.lat = lat;
  }

  /**
   * Desc: sets hotel longitude.
   *
   * @param: lng - the longitude of the hotel location.
   */
  public void setLng(double lng) {
    this.lng = lng;
  }

  /**
   * Desc: sets hotel room price.
   *
   * @param: price - the price of a hotel room.
   */
  public void setPrice(int price) {
    this.price = price;
  }

  /**
   * Desc: returns the hotel name.
   *
   * @return: name - the hotel name.
   */
  public String getName() {
    return name;
  }

  /**
   * Desc: returns hotel star rating.
   *
   * @return: stars - the hotel star rating.
   */
  double getStars() {
    return stars;
  }

  /**
   * Desc: returns the hotel city.
   *
   * @return: city - the hotel city name.
   */
  String getCity() {
    return city;
  }

  /**
   * Desc: returns the hotel country code.
   *
   * @return: countryCode - the hotel country abbreviation.
   */
  public String getCountryCode() {
    return countryCode;
  }

  /**
   * Desc: returns the hotel country name.
   *
   * @return: countryName - the name of the hotel country.
   */
  String getCountryName() {
    return countryName;
  }

  /**
   * Desc: returns the hotel lattitude.
   *
   * @return: lat - the lattitude of the hotel location.
   */
  double getLat() {
    return lat;
  }

  /**
   * Desc: returns to the previous screen.
   *
   * @return: lng - the longitude of the  hotel location.
   */
  double getLng() {
    return lng;
  }

  /**
   * Desc: returns the hotel room price.
   *
   * @return: price - the hotel room price.
   */
  int getPrice() {
    return this.price;
  }

  /**
   * Desc: adds a reservation to the hotel.
   *
   * @param: numUserRooms - the number of rooms the user is booking.
   */
  public void addReservation(int numUserRooms) {
    setNumReservations(numUserRooms);
  }

  /**
   * Desc: sets the number of reservations to the hotel.
   *
   * @param: numUserRooms - the number of rooms the user is booking.
   */
  private static void setNumReservations(int numUserRooms) {
    Hotel.numReservations += numUserRooms;
  }

  @Override
  public String toString() {
    return "Hotel{"
        + "hotelId=" + hotelId
        + ", name='" + name
        + '\'' + ", stars=" + stars
        + ", city='" + city + '\''
        + ", countryCode='" + countryCode
        + '\'' + ", countryName='" + countryName
        + '\'' + ", lat=" + lat
        + ", lng=" + lng + ", price="
        + price + ", numReservations="
        + numReservations + '}';
  }

  /**
   * Desc: returns the difference in price between this and another
   * hotel object.
   *
   * @param: o - the hotel object to compare.
   * @return: int - the difference between the price of the two objects.
   * @throws: Exception.
   */
  @Override
  public int compareTo(Hotel o) {
    return (this.getPrice() - o.getPrice());
  }
}
