package player;

import java.io.Serializable;

public class SetPasswordRequest implements Serializable {
	private Player player;
	private String oldPass;
	private String newPass;
	
	public SetPasswordRequest(Player player, String oldPass, String newPass) {
		this.player = player;
		this.oldPass = oldPass;
		this.newPass = org.apache.commons.codec.digest.DigestUtils.sha256Hex(newPass);
	}

	public Player getPlayer() {
		return player;
	}
	
	public String getOldPass() {
		return oldPass;
	}

	public String getNewPass() {
		return newPass;
	}
	
	
	
}
