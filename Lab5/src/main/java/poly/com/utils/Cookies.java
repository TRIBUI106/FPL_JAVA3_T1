package poly.com.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class Cookies {
	
	public static Cookie add(String name, String value, int hours, HttpServletResponse resp) {
		Cookie cookie = new Cookie(name, value);
		cookie.setMaxAge(hours*60*60);
		cookie.setPath("/");
		resp.addCookie(cookie);
		return cookie;
	}
	
	public static String get(String name, HttpServletRequest resq) {
		Cookie[] cookies = resq.getCookies();
		if(cookies!=null) {
			for(Cookie cookie: cookies) {
				if(cookie.getName().equalsIgnoreCase(name)) {
					return cookie.getValue();
				}
			}
		}
		return "";
	}
}
