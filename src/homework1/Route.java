package homework1;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * A Route is a path that traverses arbitrary GeoSegments, regardless of their
 * names.
 * <p>
 * Routes are immutable. New Routes can be constructed by adding a segment to
 * the end of a Route. An added segment must be properly oriented; that is, its
 * p1 field must correspond to the end of the original Route, and its p2 field
 * corresponds to the end of the new Route.
 * <p>
 * Because a Route is not necessarily straight, its length - the distance
 * traveled by following the path from start to end - is not necessarily the
 * same as the distance along a straight line between its endpoints.
 * <p>
 * Lastly, a Route may be viewed as a sequence of geographical features, using
 * the <tt>getGeoFeatures()</tt> method which returns an Iterator of GeoFeature
 * objects.
 * <p>
 * <b>The following fields are used in the specification:</b>
 * <p>
 * <pre>
 *   start : GeoPoint            // location of the start of the route
 *   end : GeoPoint              // location of the end of the route
 *   startHeading : angle        // direction of travel at the start of the route, in degrees
 *   endHeading : angle          // direction of travel at the end of the route, in degrees
 *   geoFeatures : sequence      // a sequence of geographic features that make up this Route
 *   geoSegments : sequence      // a sequence of segments that make up this Route
 *   length : real               // total length of the route, in kilometers
 *   endingGeoSegment : GeoSegment  // last GeoSegment of the route
 * </pre>
 **/
public class Route {
    final GeoPoint start; // location of the start of the route
    final GeoPoint end; // location of the end of the route
    final double startHeading; // direction of travel at the start of the route, in
    // degrees
    final double endHeading; // direction of travel at the end of the route, in
    // degrees
    final LinkedList<GeoFeature> geoFeatures; // a sequence of geographic features
    // that make up this Route
    final LinkedList<GeoSegment> geoSegments; // a sequence of segments that make up
    // this Route
    final double length; // total length of the route, in kilometers
    final GeoSegment endingGeoSegment; // last GeoSegment of the route

    /**
     * Constructs a new Route.
     *
     * @requires gs != null
     * @effects Constructs a new Route, r, such that r.startHeading = gs.heading
     * && r.endHeading = gs.heading && r.start = gs.p1 && r.end = gs.p2
     **/
    public Route(GeoSegment gs) {
        this.start = gs.getP1();
        this.end = gs.getP2();
        this.startHeading = gs.getHeading();
        this.endHeading = gs.getHeading();
        this.geoFeatures = new LinkedList<GeoFeature>() {{
            add(new GeoFeature(gs));
        }};
        this.geoSegments = new LinkedList<GeoSegment>() {{
            add(gs);
        }};
        this.length = gs.getLength();
        this.endingGeoSegment = gs;
        checkRep();
    }

    /**
     * Constructs a new Route.
     *
     * @requires gs != null r!=null
     * @effects Constructs a new Route, r, such that r.startHeading = r.startHeading
     * && r.endHeading = gs.heading && r.start = r.getStart && r.end = gs.p2
     **/
    public Route(Route r, GeoSegment gs) {
        this.start = r.getStart();
        this.end = gs.getP2();
        this.startHeading = r.getStartHeading();
        this.endHeading = gs.getHeading();
        this.geoFeatures = new LinkedList<GeoFeature>() {{
            Iterator<GeoSegment> i;
            String lastFeature_name = "";
            for (GeoFeature s : r.geoFeatures) {
                i = s.getGeoSegments();
                GeoFeature gf_temp = new GeoFeature((GeoSegment) i.next());
                while (i.hasNext()) {
                    gf_temp = new GeoFeature(gf_temp, (GeoSegment) i.next());
                }
                add(gf_temp);
                lastFeature_name = gf_temp.getName();
            }
            if (gs.getName().equals(lastFeature_name)) {
                set(size() - 1, new GeoFeature(get(size() - 1), gs));
            } else {
                add(new GeoFeature(gs));
            }
        }};

        this.geoSegments = new LinkedList<GeoSegment>() {{
            for (GeoSegment s : r.geoSegments) {
                add(new GeoSegment(s.getName(), s.getP1(), s.getP2()));
            }
            add(gs);
        }};
        this.length = gs.getLength() + r.getLength();
        this.endingGeoSegment = gs;
        checkRep();
    }

    /**
     * Returns location of the start of the route.
     *
     * @return location of the start of the route.
     **/
    public GeoPoint getStart() {
        checkRep();
        return this.start;
    }

    /**
     * Returns location of the end of the route.
     *
     * @return location of the end of the route.
     **/
    public GeoPoint getEnd() {
        checkRep();
        return this.end;
    }

    /**
     * Returns direction of travel at the start of the route, in degrees.
     *
     * @return direction (in compass heading) of travel at the start of the
     * route, in degrees.
     **/
    public double getStartHeading() {
        checkRep();
        return this.startHeading;
    }

    /**
     * Returns direction of travel at the end of the route, in degrees.
     *
     * @return direction (in compass heading) of travel at the end of the route,
     * in degrees.
     **/
    public double getEndHeading() {
        checkRep();
        return this.endHeading;
    }

    /**
     * Returns total length of the route.
     *
     * @return total length of the route, in kilometers. NOTE: this is NOT
     * as-the-crow-flies, but rather the total distance required to
     * traverse the route. These values are not necessarily equal.
     **/
    public double getLength() {
        checkRep();
        return this.length;
    }

    /**
     * Creates a new route that is equal to this route with gs appended to its
     * end.
     *
     * @return a new Route r such that r.end = gs.p2 && r.endHeading =
     * gs.heading && r.length = this.length + gs.length
     * @requires gs != null && gs.p1 == this.end
     **/
    public Route addSegment(GeoSegment gs) {
        checkRep();
        return new Route(this, gs);
    }

    /**
     * Returns an Iterator of GeoFeature objects. The concatenation of the
     * GeoFeatures, in order, is equivalent to this route. No two consecutive
     * GeoFeature objects have the same name.
     *
     * @return an Iterator of GeoFeatures such that
     * <p>
     * <pre>
     *      this.start        = a[0].start &&
     *      this.startHeading = a[0].startHeading &&
     *      this.end          = a[a.length - 1].end &&
     *      this.endHeading   = a[a.length - 1].endHeading &&
     *      this.length       = sum(0 <= i < a.length) . a[i].length &&
     *      for all integers i
     *          (0 <= i < a.length - 1 => (a[i].name != a[i+1].name &&
     *                                     a[i].end  == a[i+1].start))
     *         </pre>
     * <p>
     * where <code>a[n]</code> denotes the nth element of the Iterator.
     * @see homework1.GeoFeature
     **/
    public Iterator<GeoFeature> getGeoFeatures() {
        checkRep();
        return this.geoFeatures.iterator();
    }

    /**
     * Returns an Iterator of GeoSegment objects. The concatenation of the
     * GeoSegments, in order, is equivalent to this route.
     *
     * @return an Iterator of GeoSegments such that
     * <p>
     * <pre>
     *      this.start        = a[0].p1 &&
     *      this.startHeading = a[0].heading &&
     *      this.end          = a[a.length - 1].p2 &&
     *      this.endHeading   = a[a.length - 1].heading &&
     *      this.length       = sum (0 <= i < a.length) . a[i].length
     *         </pre>
     * <p>
     * where <code>a[n]</code> denotes the nth element of the Iterator.
     * @see homework1.GeoSegment
     **/
    public Iterator<GeoSegment> getGeoSegments() {
        checkRep();
        return this.geoSegments.iterator();
    }

    /**
     * Compares the specified Object with this Route for equality.
     *
     * @return true iff (o instanceof Route) && (o.geoFeatures and
     * this.geoFeatures contain the same elements in the same order).
     **/
    public boolean equals(Object o) {
        checkRep();
        if (o == null || !(o instanceof Route))
            return false;
        if (this.geoFeatures.size() != ((Route) o).geoFeatures.size())
            return false;
        for (int i = 0; i < this.geoFeatures.size(); i++) {
            if (!(this.geoFeatures.get(i).equals(((Route) o).geoFeatures.get(i))))
                return false;
        }
        return true;
    }

    /**
     * Returns a hash code for this.
     *
     * @return a hash code for this.
     **/
    public int hashCode() {
        // This implementation will work, but you may want to modify it
        // for improved performance.
        checkRep();
        int sum = 0;
        for (int i = 0; i < this.geoFeatures.size(); i++) {
            sum += this.geoFeatures.get(i).hashCode();

        }
        return sum / this.geoFeatures.size();
    }

    /**
     * Returns a string representation of this.
     *
     * @return a string representation of this.
     **/
    public String toString() {
        checkRep();
        String s = "";
        for (int i = 0; i < this.geoFeatures.size(); i++) {
            s += this.geoFeatures.get(i).toString() + " ----- ";
        }
        return s;
    }

    /**
     * Checks to see if the representation invariant is being violated.
     *
     * @throws AssertionError if representation invariant is violated.
     */
    private void checkRep() {
        assert this.length != 0 :
                "Length must be more than 0";  //TODO: CHeck this thing
    }
}
