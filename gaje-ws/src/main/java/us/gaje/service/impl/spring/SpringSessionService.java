package us.gaje.service.impl.spring;

import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.userdetails.UserDetails;

import us.gaje.service.web.SessionService;

/**
 * The spring implementation of the SessionService.
 * 
 * Uses SecurityContextHolder to get the security context.
 * Assumes using the spring security configuration.
 * 
 * 
 * 
 * @author artripa
 *
 */
public class SpringSessionService implements SessionService {
	public  String getUsername() {
		Object obj = SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		String username;

		if (obj instanceof UserDetails) {
			username = ((UserDetails) obj).getUsername();
		} else {
			username = obj.toString();
		}
		return username;
	}
}
