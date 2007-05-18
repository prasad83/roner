/**
 * @(#)RoundedCornerServlet.java
 */
package web20.roner.servlets;

import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Help For Rounded Corner.
 */
public class RoundedCornerHelpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		processRequest(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		processRequest(request, response);
	}
	public void processRequest(HttpServletRequest request, HttpServletResponse response) {
		try {
			writeHelpResponse(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	String getRoundedCornerUrl(HttpServletRequest request) {
		String baseurl = request.getScheme() + "://" + request.getServerName();
		if(request.getServerPort() != 80 && request.getServerPort() != 443) {
			baseurl += ":" + request.getServerPort();
		}
		baseurl += request.getContextPath();
		if(!baseurl.endsWith("/")) baseurl += "/";
		
		String url = baseurl + "rounded";
		return url;
	}
	
    void writeHelpResponse(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	String roundedURL = getRoundedCornerUrl(request);
    	
        PrintWriter pw = null;
        try {
            response.setContentType("text/html");
            pw = response.getWriter();
            
            pw.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
            pw.println("<html>");
            pw.println("<head>");
            pw.println("<title>Roner</title>");

            pw.println("<style type='text/css'>");
            pw.println("body { font: 14px, Verdana, Arial, sans-serif; }");
            pw.println("table { font: inherit; }");
            pw.println("table th { text-align: left; }");
            pw.println("a { color: blue; }");
            pw.println("a b { color: black; }");
            pw.println("</style>");
            pw.println("</head>");

            pw.println("<body>");
            pw.println("<h2>Roner - <u>Ro</u>unded Cor<u>ner</u> Generator</h2>");
            
            pw.println("<i>Implemented in Java</i>");

            pw.println("<p>You can use the <a href='#rounded_corner'>rounded corner</a> and <a href='#shadow_boxes'>shadow boxes </a> with easy now.</p>");

            pw.println("<p><b>URL: " + roundedURL + "?</b></p>");

            pw.println("<table>");
            pw.println("	<tr><th>Parameter</th><th>Meaning</th>         <th>Valid Values</th></tr>");
            pw.println("	<tr><td>f</td>        <td>FILENAME</td>         <td>Only when run through console</td></tr>");
            pw.println("	<tr><td>c</td>        <td>COLOR</td>            <td><a href='#COLOR'>COLOR</a></td></tr>");
            pw.println("	<tr><td>bc</td>       <td>BACKGROUND</td>       <td><a href='#COLOR'>COLOR</a></td></tr>");
            pw.println("	<tr><td>ibc</td>      <td>INNER BACKGROUND</td> <td><a href='#COLOR'>COLOR</a></td></tr>");
            pw.println("	<tr><td>w</td>        <td>WIDTH</td>            <td>int</td></tr>");
            pw.println("	<tr><td>h</td>        <td>HEIGHT</td>           <td>int</td></tr>");
            pw.println("	<tr><td>a</td>        <td>ANGLE</td>            <td>tl, tr, bl, br</td></tr>");
            	
            pw.println("	<tr><td>sw</td>       <td>SHADOW WIDTH</td>     <td>int</td></tr>");
            pw.println("	<tr><td>o</td>        <td>SHADOW OPACITY</td>   <td>0.0 to 1.0</td></tr>");
            pw.println("	<tr><td>s</td>        <td>SHADOW SIDE</td>      <td>left, right, top, bottom</td></tr>");
            	
            pw.println("	<tr><td>shadow</td>  <td>WHOLE SHADOW</td>      <td>true or false</td></tr>");
            pw.println("	<tr><td>ah</td>      <td>ARC HEIGHT</td>        <td>float</td></tr>");
            pw.println("	<tr><td>aw</td>      <td>ARC WIDTH</td>         <td>float</td></tr>");
            pw.println("</table>");

            pw.println("<p><a name='COLOR'><b>COLOR</b></a>:In RGB (like, d7d7d7 etc.) and aqua, black, blue, fuchsia, gray, green, lime, maroon, navy, olive, red, silver, teal, white, yellow</p>");

            pw.println("<p><b>Output is PNG format image.</b></p>");
            
            pw.println("<p><b><u>Few Examples</u></b></p>");

            pw.println("<table>");
            pw.println("	<tr><th><a name='rounded_corner'><b>Build Corner:</b></a></th><td>c, bc, w, h, a, sw (optional), o(optional)</td></tr>");
            pw.println("</table>");

            pw.println("<table>");
            pw.println("	<tr valign='top'><td><img src='"+roundedURL+"?c=blue&a=tl&w=100&h=100&bc=white'></td><td>"+roundedURL+"?c=blue&amp;a=tl&amp;w=100&amp;h=100&amp;bc=white</td></tr>");
            pw.println("	<tr valign='top'><td><img src='"+roundedURL+"?c=d7d7d7&a=br&w=100&h=100&bc=blue'></td><td>"+roundedURL+"?c=d7d7d7&amp;a=br&amp;w=100&amp;h=100&amp;bc=blue</td></tr>");			
            pw.println("</table>");
            pw.println("<br/>");

            pw.println("<table>");	
            pw.println("	<tr><th><a name='shadow_boxes'><b>Whole Shadow:</b></a></th><td>shadow, bc, w, h, aw, ah, sw, o, ibc(optional)</td></tr>");
            pw.println("</table>");
            pw.println("<table>");
            pw.println("	<tr valign='top'>");
            pw.println("		<td><img src='"+roundedURL+"?shadow=true&bc=red&w=100&h=100&aw=10&ah=10&sw=10&o=0.99999'></td>");
            pw.println("		<td>"+roundedURL+"?shadow=true&amp;bc=red&amp;w=100&amp;h=100&amp;aw=10&amp;ah=10&amp;sw=10&amp;o=0.99999</td>");
            pw.println("	</tr>");
            pw.println("	<tr valign='top'>");
            pw.println("		<td><img src='"+roundedURL+"?shadow=true&bc=white&ibc=efefef&w=100&h=100&aw=10&ah=10&sw=5&o=0.99999'></td>");
            pw.println("		<td>"+roundedURL+"?shadow=true&amp;bc=white&amp;ibc=efefef&amp;w=100&amp;h=100&amp;aw=10&amp;ah=10&amp;sw=5&amp;o=0.99999</td>");
            pw.println("	</tr>");
            pw.println("</table>");

            pw.println("<br/>");
            pw.println("<table><tr><th>Side Shadow:</th><td>s, sw, o</td></tr></table>");

            pw.println("<table>");
            pw.println("	<tr valign='top'>");
            pw.println("	<td><img src='"+roundedURL+"?s=left&sw=100' /></td>");
            pw.println("	<td>"+roundedURL+"?s=left&amp;sw=100</td>");
            pw.println("	</tr>");
            pw.println("	<tr valign='top'>");
            pw.println("	<td><img src='"+roundedURL+"?s=top&sw=30' /></td>");
            pw.println("    <td>"+roundedURL+"?s=top&amp;sw=30</td>");
            pw.println("    </tr>");
            pw.println("</table>");

            pw.println("</body>");
            pw.println("</html>");

        }  finally {
            try {
                if (pw != null) {
                    pw.flush();
                    pw.close();
                }
            } catch (Throwable t) {
                // ignore
            }
        }
    }
}
