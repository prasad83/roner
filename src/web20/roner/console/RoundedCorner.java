/**
 * @(#)RoundedCorner.java
 */
package web20.roner.console;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import web20.roner.RoundedCornerGenerator;
import web20.roner.RoundedCornerUtil;

/**
 * RoundedCorner Console Support to save the image in file for usage.
 * @author prasad
 *
 */
public class RoundedCorner {
	
	public static void main(String[] args) {
		ArgumentHandler arghandler = new ArgumentHandler(args);
		processRequest(arghandler);		
	}

	public static void processRequest(ArgumentHandler request) {
		String filename = request.get(RoundedCornerUtil.PARM_FILE);
		String color = request.get(RoundedCornerUtil.PARM_COLOR);
		String bgColor = request.get(RoundedCornerUtil.PARM_BACKGROUND_COLOR);
        String inbgColor = request.get(RoundedCornerUtil.PARM_INBACKGROUND_COLOR);
        int width = RoundedCornerUtil.getIntParam(request.get(RoundedCornerUtil.PARM_WIDTH));
        int height = RoundedCornerUtil.getIntParam(request.get(RoundedCornerUtil.PARM_HEIGHT));
        String angle = request.get(RoundedCornerUtil.PARM_ANGLE);
        
        int shadowWidth = RoundedCornerUtil.getIntParam(request.get(RoundedCornerUtil.PARM_SHADOW_WIDTH));
        float shadowOpacity = RoundedCornerUtil.getFloatParam(request.get(RoundedCornerUtil.PARM_SHADOW_OPACITY));
        String side = request.get(RoundedCornerUtil.PARM_SHADOW_SIDE);

        boolean wholeShadow = Boolean.valueOf(request.get(RoundedCornerUtil.PARM_WHOLE_SHADOW)).booleanValue();
        float arcWidth = RoundedCornerUtil.getFloatParam(request.get(RoundedCornerUtil.PARM_ARC_WIDTH));
        float arcHeight = RoundedCornerUtil.getFloatParam(request.get(RoundedCornerUtil.PARM_ARC_HEIGHT));

        ByteArrayOutputStream bo = null;
        
        RoundedCornerGenerator _generator = new RoundedCornerGenerator();
        
        try {
            
            String type = "png"; //(bgColor != null) ? "gif" : "png";

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

            byte[] data = bo.toByteArray();

            // Write image to the file.
            if(filename == null) filename = "roner-" + System.currentTimeMillis();
            if(filename.toLowerCase().indexOf(".png") == -1) filename += ".png";
            File file = new File(filename);
            
            writeImageFile(file, data, type);
        
        } catch (Throwable ex) {
            ex.printStackTrace();
            
            System.out.println("[PLEASE REFER TO docs FOR USAGE]");
            
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
	
	static void writeImageFile(File file, byte[] data, String type)
    throws Exception
    {
        OutputStream os = null;

        try {
            os = new FileOutputStream(file);

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

class ArgumentHandler {
	private Map keyval = new HashMap();
	private List values = new ArrayList();
	
	public ArgumentHandler() {
		
	}

	public ArgumentHandler(String[] args) {
		init(args, 0);
	}
	public ArgumentHandler(String[] args, int startIndex) {
		init(args, startIndex);        
	}
	
	public void init(String[] args, int startIndex) {
		if(args == null) return;
		for(int index = startIndex; index < args.length; ++index) {
			if(args[index].startsWith("-")) {
				if((index+1) < args.length && !args[index+1].startsWith("-"))
					keyval.put(args[index],args[++index]);
				else 
					keyval.put(args[index], null);
			} else {
				values.add(args[index]);
			}
		}
	}
	
	public boolean has(String key) {        
		return keyval.containsKey("-"+key);
	}
    
	public String get(String key) {
		return (String)keyval.get("-"+key);
	}
    
	public String get(int index) {
		if(index >= 0 && index < values.size()) return (String) values.get(index);
		return null;
	}
    public int valsize() {        
        return values.size();        
    }
}
