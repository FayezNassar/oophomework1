package homework1;

/**
 * A GeoPoint is a point on the earth. GeoPoints are immutable.
 * <p>
 * North latitudes and east longitudes are represented by positive numbers.
 * South latitudes and west longitudes are represented by negative numbers.
 * <p>
 * The code may assume that the represented points are nearby the Technion.
 * <p>
 * <b>Implementation direction</b>:<br>
 * The Ziv square is at approximately 32 deg. 46 min. 59 sec. N latitude and 35
 * deg. 0 min. 52 sec. E longitude. There are 60 minutes per degree, and 60
 * seconds per minute. So, in decimal, these correspond to 32.783098 North
 * latitude and 35.014528 East longitude. The constructor takes integers in
 * millionths of degrees. To create a new GeoPoint located in the the Ziv
 * square, use:
 * <tt>GeoPoint zivCrossroad = new GeoPoint(32783098,35014528);</tt>
 * <p>
 * Near the Technion, there are approximately 110.901 kilometers per degree of
 * latitude and 93.681 kilometers per degree of longitude. An implementation may
 * use these values when determining distances and headings.
 * <p>
 * <b>The following fields are used in the specification:</b>
 * <p>
 * <pre>
 *   latitude :  real        // latitude measured in degrees
 *   longitude : real        // longitude measured in degrees
 * </pre>
 **/
public class GeoPoint implements Cloneable {

    /**
     * Minimum value the latitude field can have in this class.
     **/
    public static final int MIN_LATITUDE = -90 * 1000000;

    /**
     * Maximum value the latitude field can have in this class.
     **/
    public static final int MAX_LATITUDE = 90 * 1000000;

    /**
     * Minimum value the longitude field can have in this class.
     **/
    public static final int MIN_LONGITUDE = -180 * 1000000;

    /**
     * Maximum value the longitude field can have in this class.
     **/
    public static final int MAX_LONGITUDE = 180 * 1000000;

    /**
     * Approximation used to determine distances and headings using a "flat
     * earth" simplification.
     */
    public static final double KM_PER_DEGREE_LATITUDE = 110.901;

    /**
     * Approximation used to determine distances and headings using a "flat
     * earth" simplification.
     */
    public static final double KM_PER_DEGREE_LONGITUDE = 93.681;

    // Implementation hint:
    // Doubles and floating point math can cause some problems. The exact
    // value of a double can not be guaranteed except within some epsilon.
    // Because of this, using doubles for the equals() and hashCode()
    // methods can have erroneous results. Do not use floats or doubles for
    // any computations in hashCode(), equals(), or where any other time
    // exact values are required. (Exact values are not required for length
    // and distance computations). Because of this, you should consider
    // using ints for your internal representation of GeoPoint.

    int latitude;
    int longitude;

    // Abstraction Function:
    // A GeoPoint p is NotInWorld
    // if p.latitude < MIN_LATITUDE or p.latitude > MAX_LATITUDE,
    // or if p.longitude < MIN_LONGITUDE or p.longitude > MAX_LONGITUDE,
    // (p.latitude , p.latitude) otherwise is our point in the world.

    // Representation invariant for every GeoPoint p:
    // (MIN_LATITUDE <= p.latitude <= MAX_LATITUDE) and
    // (MIN_LONGITUDE <= p.longitude <= MAX_LONGITUDE)

    /**
     * Constructs GeoPoint from a latitude and longitude.
     *
     * @requires the point given by (latitude, longitude) in millionth of a
     * degree is valid such that: (MIN_LATITUDE <= latitude <=
     * MAX_LATITUDE) and (MIN_LONGITUDE <= longitude <= MAX_LONGITUDE)
     * @effects constructs a GeoPoint from a latitude and longitude given in
     * millionth of degrees.
     **/
    public GeoPoint(int latitude, int longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        checkRep();
    }

    /**
     * Returns the latitude of this.
     *
     * @return the latitude of this in millionths of degrees.
     */
    public int getLatitude() {
        checkRep();
        return this.latitude;
    }

    /**
     * Returns the longitude of this.
     *
     * @return the latitude of this in millionths of degrees.
     */
    public int getLongitude() {
        checkRep();
        return this.longitude;
    }

    /**
     * Computes the distance between GeoPoints.
     *
     * @return the distance from this to gp, using the flat-surface, near the
     * Technion approximation.
     * @requires gp != null
     **/
    public double distanceTo(GeoPoint gp) {
        checkRep();
        double distance_longitude = Math.abs(gp.longitude - this.longitude) * KM_PER_DEGREE_LONGITUDE;
        double distance_latitude = Math.abs(gp.latitude - this.latitude) * KM_PER_DEGREE_LATITUDE;
        return Math.sqrt(Math.pow(distance_longitude, 2) + Math.pow(distance_latitude, 2)) / 1000000;
    }

    /**
     * Computes the compass heading between GeoPoints.
     *
     * @return the compass heading h from this to gp, in degrees, using the
     * flat-surface, near the Technion approximation, such that 0 <= h <
     * 360. In compass headings, north = 0, east = 90, south = 180, and
     * west = 270.
     * @requires gp != null && !this.equals(gp)
     **/
    public double headingTo(GeoPoint gp) {
        // Implementation hints:
        // 1. You may find the mehtod Math.atan2() useful when
        // implementing this method. More info can be found at:
        // http://docs.oracle.com/javase/8/docs/api/java/lang/Math.html
        //
        // 2. Keep in mind that in our coordinate system, north is 0
        // degrees and degrees increase in the clockwise direction. By
        // mathematical convention, "east" is 0 degrees, and degrees
        // increase in the counterclockwise direction.

        // I will put this point in the (0,0) and compute the atan2 on the
        // diifernce between them.
        checkRep();
        double x = (gp.latitude  - this.latitude) * KM_PER_DEGREE_LATITUDE;
        double y = (gp.longitude - this.longitude) * KM_PER_DEGREE_LONGITUDE;
        double theta = Math.toDegrees(Math.atan2(x, y));
        theta = theta < 0 ? 360 + theta : theta;
        if (theta >= 0 && theta <= 90)
            return 90 - theta;
        return 360 - (theta - 90);
    }

    /**
     * Compares the specified Object with this GeoPoint for equality.
     *
     * @return gp != null && (gp instanceof GeoPoint) && gp.latitude =
     * this.latitude && gp.longitude = this.longitude
     **/
    public boolean equals(Object gp) {
        checkRep();
        return gp != null && (gp instanceof GeoPoint) && ((GeoPoint) gp).latitude == this.latitude
                && ((GeoPoint) gp).longitude == this.longitude;
    }

    /**
     * Returns a hash code value for this GeoPoint.
     *
     * @return a hash code value for this GeoPoint.
     **/
    public int hashCode() {
        // This implementation will work, but you may want to modify it
        // for improved performance.

        checkRep();
        return (this.latitude / 1000000 + this.longitude / 1000000) / 2;
    }

    /**
     * Returns a string representation of this GeoPoint.
     *
     * @return a string representation of this GeoPoint.
     **/
    public String toString() {
        checkRep();
        return "(latitude,longitude)=(" + this.latitude + "," + this.longitude + ") \n";

    }

    /**
     * Checks to see if the representation invariant is being violated.
     *
     * @throws AssertionError if representation invariant is violated.
     */
    private void checkRep() {
        assert this.latitude >= MIN_LATITUDE && this.latitude <= MAX_LATITUDE :
                "Latitude of a GeoPoint must be between " + MIN_LATITUDE + " and " + MAX_LATITUDE;

        assert this.longitude >= MIN_LONGITUDE && this.longitude <= MAX_LONGITUDE :
                "Longitude of a GeoPoint must be between " + MIN_LONGITUDE + " and " + MAX_LONGITUDE;
    }

    /**
     * Returns a copy for the point.
     *
     * @return a copy for the point.
     */
    @Override
    public GeoPoint clone() {
        return new GeoPoint(this.latitude, this.longitude);
    }

}
