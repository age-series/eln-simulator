package mods.eln.common;

/**
 *  LinearFunction is a class that emulates a linear function.
 *
 *  It extends IFunction, a generic interface for functions
 */
public class LinearFunction implements IFunction {

    private final float yInt;
    private final float slope;

    /**
     *  Linear Function models a linear mathematical function.
     *
     *  This takes two points and makes it into a linear function.
     *
     * @param x0 point 1 x coordinate
     * @param y0 point 1 y coordinate
     * @param x1 point 2 x coordinate
     * @param y1 point 2 y coordinate
     */
    public LinearFunction(float x0, float y0, float x1, float y1) {
        yInt = (-x0) / (x1 - x0) * (y1-y0) + y0;
        // note, divide by zero will result in an INFINITY
        slope = (y1 - y0) / (x1 - x0);
    }

    /**
     *  Linear Function models a linear mathematical function.
     *
     *  This takes a y interval and a slope and makes it into a linear function.
     *
     * @param yInt a y interval
     * @param slope a slope
     */
    public LinearFunction(float yInt, float slope) {
        this.yInt = yInt;
        this.slope = slope;
    }

    /**
     *  Get value of Y for a particular X from this function.
     *
     * @param x the x value you have that you want put through the function
     * @return the y value you want from the function
     */
    @Override
    public double getValue(double x) {
        return x * slope + yInt;
    }
}