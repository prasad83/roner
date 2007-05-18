/**
 * @(#)RoundedCornerUtil.java
 */
package web20.roner;

/**
 * Few useful constants and utility functions.
 * @author prasad
 *
 */
public class RoundedCornerUtil {

	public static final String PARM_FILE = "f";
	
    public static final String PARM_COLOR = "c";
    public static final String PARM_BACKGROUND_COLOR = "bc";
    public static final String PARM_INBACKGROUND_COLOR = "ibc";
    public static final String PARM_WIDTH = "w";
    public static final String PARM_HEIGHT = "h";
    public static final String PARM_ANGLE = "a";

    public static final String PARM_SHADOW_WIDTH ="sw";
    public static final String PARM_SHADOW_OPACITY ="o";
    public static final String PARM_SHADOW_SIDE = "s";

    public static final String PARM_WHOLE_SHADOW = "shadow";
    public static final String PARM_ARC_HEIGHT = "ah";
    public static final String PARM_ARC_WIDTH = "aw";

    public static int getIntParam(String value)
    {
        if (value == null)
            return -1;
        
        return Integer.valueOf(value).intValue();
    }

    public static float getFloatParam(String value)
    {
        if (value == null)
            return -1f;
        
        return Float.valueOf(value).floatValue();
    }
}
