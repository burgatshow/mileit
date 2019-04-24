package hu.thom.mileit.rest;

import java.io.Serializable;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath(value = "/rest")
public class RestEndpointApplication extends Application implements Serializable {
	private static final long serialVersionUID = -3432870126955940105L;

	public RestEndpointApplication() {
	}
}
