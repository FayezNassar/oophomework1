package homework1;

import java.util.Iterator;

/**
 * A RouteFormatter class knows how to create a textual description of
 * directions from one location to another. The class is abstract to
 * support different textual descriptions.
 */
public abstract class RouteFormatter {

    /**
     * Give directions for following this Route, starting at its start point
     * and facing in the specified heading.
     *
     * @param route   the route for which to print directions.
     * @param heading the initial heading.
     * @return A newline-terminated directions <tt>String</tt> giving
     * human-readable directions from start to end along this route.
     * @requires route != null &&
     * 0 <= heading < 360
     **/
    public String computeDirections(Route route, double heading) {
        // Implementation hint:
        // This method should call computeLine() for each geographic
        // feature in this route and concatenate the results into a single
        // String.
        String s = "";
        Iterator<GeoFeature> i = route.getGeoFeatures();
        GeoFeature temp_2;
        double last_heading = heading;
        while (i.hasNext()) {
            temp_2 = (GeoFeature) i.next();
            s += this.computeLine(temp_2, last_heading);
            last_heading = temp_2.getEndHeading();
        }
        return s;
    }


    /**
     * Computes a single line of a multi-line directions String that
     * represents the instructions for traversing a single geographic
     * feature.
     *
     * @param geoFeature  the geographical feature to traverse.
     * @param origHeading the initial heading.
     * @return A newline-terminated <tt>String</tt> that gives directions
     * on how to traverse this geographic feature.
     * @requires geoFeature != null
     */
    public abstract String computeLine(GeoFeature geoFeature, double origHeading);


    /**
     * Computes directions to turn based on the heading change.
     *
     * @param origHeading the start heading.
     * @param newHeading  the desired new heading.
     * @return English directions to go from the old heading to the new
     * one. Let the angle from the original heading to the new
     * heading be a. The turn should be annotated as:
     * <p>
     * <pre>
     * Continue             if a < 10
     * Turn slight right    if 10 <= a < 60
     * Turn right           if 60 <= a < 120
     * Turn sharp right     if 120 <= a < 179
     * U-turn               if 179 <= a
     * </pre>
     * and likewise for left turns.
     * @requires 0 <= oldHeading < 360 &&
     * 0 <= newHeading < 360
     */
    protected String getTurnString(double origHeading, double newHeading) {
        String s = "";
        double angelsDiff = Math.abs(newHeading - origHeading);
        double angelBetween = Math.min(angelsDiff, 360 - angelsDiff);
        String direction = "";
        if (origHeading <= 180) {
            direction = newHeading <= origHeading + 180 ? "right" : "left";
        } else {
            direction = newHeading <= origHeading ? "left" : "right";
        }
        if (angelBetween < 10) {
            s = "Continue";
        } else if (angelBetween >= 10 && angelBetween < 60) {
            s = "Turn slight " + direction;
        } else if (angelBetween >= 60 && angelBetween < 120) {
            s = "Turn " + direction;
        } else if (angelBetween >= 120 && angelBetween < 179) {
            s = "Turn sharp " + direction;
        } else {
            s = "U-turn";
        }
        return s;
    }

}
