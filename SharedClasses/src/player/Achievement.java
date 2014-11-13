package player;

import java.io.Serializable;

public class Achievement implements Serializable {

	private String name;
	public boolean isEarned;
	
	public Achievement(String name, boolean isEarned) {
		this.name = name;
		this.isEarned = isEarned;
	}
	
	public String getName() {
		return name;
	}
}
