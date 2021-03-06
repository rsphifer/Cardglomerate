package misc;

import java.io.Serializable;


/**
 * @author 	Richard Phifer<rsphifer@purdue.edu>
 * @date	10/11/2013
 * 
 * All communication between client applications and the server will be in the form of a Request
 * 
 */
@SuppressWarnings("serial")
public class Request implements Serializable{

	private String action;
	private Object object;
	
	public Request(String action, Object object) {
		this.action = action;
		this.object = object;
	}
	
	public String getAction() {
		return action;
	}
	
	public Object getObject() {
		return object;
	}
	
}
