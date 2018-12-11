package application;


import javafx.event.ActionEvent;

/**
 * Desc: Controller for the final screen.
 */
public class FinalController {

  /**
   * Desc: returns to the previous screen.
   *
   * @param: event - the event handler for the button
   * @throws: Exception
   */
  public void ok(ActionEvent event) throws Exception {
    Navigator.search(event);
  }

}
