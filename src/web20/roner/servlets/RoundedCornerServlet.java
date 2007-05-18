/**
 * @(#)RoundedCornerServlet.java
 */
package web20.roner.servlets;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import web20.roner.RoundedCornerGenerator;
import web20.roner.RoundedCornerUtil;

/**
 * Provides generated rounded corner images in a similar use / fashion as
 * outlined here: <a href="http://xach.livejournal.com/95656.html">google's own cornershop</a>.
 */
public class RoundedCornerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final long MONTH_SECONDS = 60 * 60 * 24 * 30;

    private static final long EXPIRES = System.currentTimeMillis() + 365 * 24 * 60 * 60 * 1000L;

    private RoundedCornerGenerator _generator = new RoundedCornerGenerator();

    // holds pre-built binaries for previously generated colors
    private Map _imageCache = new HashMap();
		
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		processRequest(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		processRequest(request, response);
	}
	public void processRequest(HttpServletRequest request, HttpServletResponse response) {
		
        if (request.getHeader("If-Modified-Since") != null)
        {
            response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
            return;
        }
        String color = request.getParameter(RoundedCornerUtil.PARM_COLOR);
        String bgColor = request.getParameter(RoundedCornerUtil.PARM_BACKGROUND_COLOR);
        String inbgColor = request.getParameter(RoundedCornerUtil.PARM_INBACKGROUND_COLOR);
        int width = RoundedCornerUtil.getIntParam(request.getParameter(RoundedCornerUtil.PARM_WIDTH));
        int height = RoundedCornerUtil.getIntParam(request.getParameter(RoundedCornerUtil.PARM_HEIGHT));
        String angle = request.getParameter(RoundedCornerUtil.PARM_ANGLE);
        
        int shadowWidth = RoundedCornerUtil.getIntParam(request.getParameter(RoundedCornerUtil.PARM_SHADOW_WIDTH));
        float shadowOpacity = RoundedCornerUtil.getFloatParam(request.getParameter(RoundedCornerUtil.PARM_SHADOW_OPACITY));
        String side = request.getParameter(RoundedCornerUtil.PARM_SHADOW_SIDE);

        boolean wholeShadow = Boolean.valueOf(request.getParameter(RoundedCornerUtil.PARM_WHOLE_SHADOW)).booleanValue();
        float arcWidth = RoundedCornerUtil.getFloatParam(request.getParameter(RoundedCornerUtil.PARM_ARC_WIDTH));
        float arcHeight = RoundedCornerUtil.getFloatParam(request.getParameter(RoundedCornerUtil.PARM_ARC_HEIGHT));

        String hashKey = color + bgColor + inbgColor + width + height + angle + shadowWidth + shadowOpacity + side + wholeShadow;

        ByteArrayOutputStream bo = null;
        
        try {
            
            String type = "png"; //(bgColor != null) ? "gif" : "png";

            byte[] data = (byte[])_imageCache.get(hashKey);
            if (data != null) {

                writeImageResponse(response, data, type);
                return;
            }

            BufferedImage image = null;

            if (wholeShadow) {

                image = _generator.buildShadow(bgColor, width, height, arcWidth, arcHeight, shadowWidth, shadowOpacity, inbgColor);
            } else if (side != null) {

                image = _generator.buildSideShadow(side, shadowWidth, shadowOpacity);
            } else {

                image = _generator.buildCorner(color, bgColor, width, height, angle, shadowWidth, shadowOpacity, inbgColor);
            }

            bo = new ByteArrayOutputStream();

            ImageIO.write(image, type, bo);

            data = bo.toByteArray();

            _imageCache.put(hashKey, data);

            writeImageResponse(response, data, type);
            
        } catch (Throwable ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (bo != null) {
                    bo.close();
                }
            } catch (Throwable t) {
                // ignore
            }

        }
    }

    void writeImageResponse(HttpServletResponse response, byte[] data, String type)
    throws Exception
    {
        OutputStream os = null;

        try {
            response.setDateHeader("Expires", EXPIRES);
            response.setHeader("Cache-Control", "public, max-age=" + (MONTH_SECONDS * 3));
            response.setContentLength(data.length);
            response.setContentType("image/" + type);
            response.setContentLength(data.length);
            
            os = response.getOutputStream();

            os.write(data);

        }  finally {
            try {
                if (os != null) {
                    os.flush();
                    os.close();
                }
            } catch (Throwable t) {
                // ignore
            }
        }
    }
}
