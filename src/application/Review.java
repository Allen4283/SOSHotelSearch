package application;

/**
 * Desc: allows the user to write reviews and give a rating to a hotel.
 */
public class Review {

  private String username;
  private String review;
  private String userRating;


  /**
   * Desc: sets the user, text, and rating for the review.
   *
   * @param: username - the username of the user making the review
   * @param: review - the text of the review
   * @param: userRating - rating of hotel given by the user
   */
  public Review(String username, String review, String userRating) {
    this.username = username;
    this.review = review;
    this.userRating = userRating;
  }

  /**
   * Desc: gets the username of the user.
   *
   * @return: username - the username of the user's account
   */
  public String getUsername() {
    return username;
  }

  /**
   * Desc: gets the text written for the review.
   *
   * @return: review - the text the user writes for the review
   */
  public String getReview() {
    return review;
  }

  /**
   * Desc: gets the rating for the review.
   *
   * @return: review - the rating the user gives to the hotel
   */
  public String getUserRating() {
    return userRating;
  }
}
