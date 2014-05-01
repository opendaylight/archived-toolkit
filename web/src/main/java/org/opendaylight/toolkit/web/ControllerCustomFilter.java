package org.opendaylight.toolkit.web;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.security.web.context.SecurityContextPersistenceFilter;

public class ControllerCustomFilter extends SecurityContextPersistenceFilter {

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		System.out.println("SPRING");
	}

}
