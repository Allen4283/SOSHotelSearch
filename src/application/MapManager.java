package application;

import com.teamdev.jxmaps.*;
import com.teamdev.jxmaps.javafx.MapView;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


/**
 * Desc: manager class for the map object.
 */

public class MapManager {
  // API key for Google Maps
  private static final double DEGREES_TO_MILES = 69; // const approx num of miles in one degree
  protected  static final String API_KEY = "AIzaSyChDY-krlOCW7QcWSoQndEoazJndJaOyyw";
  private int waitTime = 2; // const wait time for function delay in seconds

  private double defaultZoom; // defines the default zoom level
  private double radiusInMiles; // defines radius (in miles) to display hotels from searched for
  private double radiusInDegrees;

  private String iconFilePath = "lib/resources/mapicons/marker_icon.png";
  private String hotelFilePath = "lib/resources/hotels.txt";

  private MapView mapView;
  private MapViewOptions mapViewOptions;
  private Icon icon;

  private String address;
  private String error;

  private boolean hasError;
  private ArrayList<Hotel> hotelsToDisplay = new ArrayList<>();

  /**
   * Desc: Default constructor for the MapManager class.
   */
  public MapManager() {
    MapView.InitJavaFX();
    mapViewOptions = new MapViewOptions();
    mapViewOptions.setApiKey(API_KEY);
    defaultZoom = 14.0;
    radiusInMiles = 20;
    radiusInDegrees = radiusInMiles / DEGREES_TO_MILES;
    icon = new Icon();
    icon.loadFromFile(iconFilePath);
    mapView = new MapView(mapViewOptions);
    address = "Fort Myers, FL";
    error = "No error";
    hasError = false;
  }

  /**
   * Desc: sets the default zoom.
   *
   * @param: defaultZoom - the zoom-in level when the map opens
   */
  public void setDefaultZoom(double defaultZoom) {
    this.defaultZoom = defaultZoom;
  }

  /**
   * Desc: sets radius in miles.
   *
   * @param: radiusInMiles - the radius from the central point that hotels will dispay
   */
  public void setRadiusInMiles(double radiusInMiles) {
    this.radiusInMiles = radiusInMiles;
  }

  /**
   * Desc: sets the path of the map marker icon.
   *
   * @param: iconFilePath - the location of the icon image file path
   */
  public void setIconFilePath(String iconFilePath) {
    this.iconFilePath = iconFilePath;
    this.icon.loadFromFile(iconFilePath);
  }

  /**
   * Desc: sets the file path for the list of hotels.
   *
   * @param: hotelFilePath - the location of the hotel text file
   */
  public void setHotelFilePath(String hotelFilePath) {
    this.hotelFilePath = hotelFilePath;
  }

  /**
   * Desc: sets the mapView.
   *
   * @param: mapView - the mapView object
   */
  public void setMapView(MapView mapView) {
    this.mapView = mapView;
  }

  /**
   * Desc: sets mapViewOptions.
   *
   * @param: mapViewOptions - the MapViewOptions object
   */
  public void setMapViewOptions(MapViewOptions mapViewOptions) {
    this.mapViewOptions = mapViewOptions;
  }

  /**
   * Desc: sets the icon that will display on the map.
   *
   * @param: icon - the icon that will display on the map
   */
  public void setIcon(Icon icon) {
    this.icon = icon;
  }

  /**
   * Desc: sets the error returned from mapView.
   *
   * @param: error - the error returned from mapView
   */
  private void setError(String error) {
    this.error = error;
  }

  /**
   * Desc: sets the error status.
   *
   * @param: status - whether or not there is an eror
   */
  private void setErrorStatus(boolean status) {
    this.hasError = status;
  }

  /**
   * Desc: sets the address that the map will open on.
   *
   * @param: address - the starting address
   */
  public void setAddress(String address) {
    this.address = address;
  }

  /**
   * Desc: gets the default zoom level.
   *
   * @return: defaultZoom - the default zoom level
   */
  public double getDefaultZoom() {
    return defaultZoom;
  }

  /**
   * Desc: gets the radius in miles.
   *
   * @return: radiusInMiles - the radius in miles that the map will display markers
   */
  public double getRadiusInMiles() {
    return radiusInMiles;
  }

  /**
   * Desc: gets the radius in degrees.
   *
   * @return: radiusInMiles - the radius in degrees that the map will display markers
   */
  public double getRadiusInDegrees() {
    return radiusInDegrees;
  }

  /**
   * Desc: gets the icon file path.
   *
   * @return: iconFilePath - the file location of the icon image
   */
  public String getIconFilePath() {
    return iconFilePath;
  }

  /**
   * Desc: gets the hotel text file file path.
   *
   * @return: radiusInMiles - the radius in miles that the map will display markers
   */
  public String getHotelFilePath() {
    return hotelFilePath;
  }

  /**
   * Desc: gets the mapView object.
   *
   * @return: mapView - the mapView object
   */
  public MapView getMapView() {
    return mapView;
  }

  /**
   * Desc: gets the mapViewOptions object.
   *
   * @return: mapViewOptions - the mapViewOptions object
   */
  public MapViewOptions getMapViewOptions() {
    return mapViewOptions;
  }

  /**
   * Desc: gets the address the map will show.
   *
   * @return: address - the address on the map
   */
  public String getAddress() {
    return address;
  }

  /**
   * Desc: gets the text of the current error.
   *
   * @return: error - the current error
   */
  public String getError() {
    return error;
  }

  /**
   * Desc: gets the current error status.
   *
   * @return: errorStatus - the current error status
   */
  public boolean getErrorStatus() {
    return hasError;
  }

  /**
   * Desc: gets the list of hotels generated by the createMap function.
   *
   * @return: hotelsToDislay - array of hotel objects
   */
  public ArrayList getHotelsToDisplay() {
    return hotelsToDisplay;
  }

  /**
   * Desc: creates a MapView object and sets default zoom and address. If all is good,
   * searches the hotel list for hotels whose lat/lngs are in the range of
   * radiusInDegrees and stores these hotels in the hotelsToDisplay array.
   */
  public void createMap() {
    //create map ready handler
    mapView.setOnMapReadyHandler(new MapReadyHandler() {
      @Override
      public void onMapReady(MapStatus status) {
        if (status == MapStatus.MAP_STATUS_OK) {
          final Map map = mapView.getMap();
          map.setZoom(defaultZoom);
          GeocoderRequest request = new GeocoderRequest();
          request.setAddress(address);

          mapView.getServices().getGeocoder().geocode(request, new GeocoderCallback(map) {
            @Override
            public void onComplete(GeocoderResult[] result, GeocoderStatus status) {
              if (status == GeocoderStatus.OK) {
                map.setCenter(result[0].getGeometry().getLocation());

                double locLatRange = result[0].getGeometry().getLocation().getLat();
                double locLonRange = result[0].getGeometry().getLocation().getLng();

                try {
                  BufferedReader br = new BufferedReader(new FileReader(hotelFilePath));
                  br.readLine(); //ignore the first line

                  while (br.readLine() != null) {
                    try {
                      String line = br.readLine();
                      String[] h = line.split(",");

                      Hotel hotel = new Hotel(Integer.parseInt(h[0]), h[1],
                          Double.parseDouble(h[2]), h[3], h[4], h[5], Double.parseDouble(h[6]),
                          Double.parseDouble(h[7]), Integer.parseInt(h[8]));

                      double lat = hotel.getLat();
                      double lon = hotel.getLng();

                      // compare the difference between the coordinates of the location the user
                      // searched for and every hotel in the database. If that difference is at
                      // most 'radius' degrees, display the hotel on the map.
                      if (Math.abs(Math.abs(lat) - Math.abs(locLatRange)) <= radiusInDegrees
                          && Math.abs(Math.abs(lon)
                          - Math.abs(locLonRange)) <= radiusInDegrees) {
                        Marker marker = new Marker(map);
                        marker.setIcon(icon);
                        marker.setPosition(new LatLng(lat, lon));

                        hotelsToDisplay.add(hotel);
                        br.close();
                      }
                    } catch (NullPointerException e) {
                      e.printStackTrace();
                    }
                  }
                } catch (IOException e) {
                  e.printStackTrace();
                }
              } else if (status == GeocoderStatus.ERROR) {
                setError("There was an error connecting to the Google Servers.");
                setErrorStatus(true);
              } else if (status == GeocoderStatus.INVALID_REQUEST) {
                setError("This request was invalid.");
                setErrorStatus(true);
              } else if (status == GeocoderStatus.OVER_QUERY_LIMIT) {
                setError("The web page has gone over the requests limit in too short a period "
                    + "of time.");
                setErrorStatus(true);
              } else if (status == GeocoderStatus.REQUEST_DENIED) {
                setError("Access denied.");
                setErrorStatus(true);
              } else if (status == GeocoderStatus.UNKNOWN_ERROR) {
                setError("Your request could not be processed due to a server error.");
                setErrorStatus(true);
              } else {
                setError("No results found for this location.");
                setErrorStatus(true);
              }
            }
          });
        } else {
          setError("Map status error.");
          setErrorStatus(true);
        }
      }
    });
    try {
      TimeUnit.SECONDS.sleep(waitTime); // wait waitTime seconds to finish parsing the text file
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
