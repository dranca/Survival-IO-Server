package Handlers;

import java.util.ArrayList;
import java.util.List;

import java.util.Random;

import com.smartfoxserver.v2.SmartFoxServer;
import com.smartfoxserver.v2.api.ISFSApi;
import com.smartfoxserver.v2.api.ISFSMMOApi;
import com.smartfoxserver.v2.entities.Room;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.entities.variables.SFSUserVariable;
import com.smartfoxserver.v2.entities.variables.UserVariable;
import com.smartfoxserver.v2.exceptions.SFSJoinRoomException;
import com.smartfoxserver.v2.exceptions.SFSLoginException;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;
import com.smartfoxserver.v2.mmo.Vec3D;

public class PositionPlayerRandomlyHandler extends BaseClientRequestHandler {

	int width = 25;
	int height = 25;
	
	@Override
	public void handleClientRequest(User user, ISFSObject arg1) {
		// TODO Auto-generated method stub
		ISFSMMOApi mmoApi = SmartFoxServer.getInstance().getAPIManager().getMMOApi();
		Room room = user.getCurrentMMORoom();
		
		Vec3D pos = new Vec3D(0.0f, 0.0f);
		mmoApi.setUserPosition(user, pos, room);
		sendSetOnMapResponse(pos, user);
		addNPC(user);
		setUserVariables(user);
	}
	
	private Vec3D getRandomPosition() {

		Random r = new Random();
		int randomX = r.nextInt(2 * width) - width;
		int randomY = r.nextInt(2 * height) - height;
		
		return new Vec3D((float)randomX, (float)randomY);
	}
	
	private void setUserVariables(User user){
		ISFSApi api = SmartFoxServer.getInstance().getAPIManager().getSFSApi();
		List<UserVariable> variables = initialUserVars();
		api.setUserVariables(user, variables);
	}
	
	private List<UserVariable> initialUserVars() {
		List<UserVariable> variables = new ArrayList<>();
		variables.add(new SFSUserVariable("rot", 0.0f));
		variables.add(new SFSUserVariable("hp", 1000.0f));
		SFSUserVariable var = new SFSUserVariable("lastAttack", 0.0d);
		var.setHidden(true);
		variables.add(var);
		return variables;
	}
	
	private void sendSetOnMapResponse(Vec3D pos, User user) {
		trace("Set on map send");
		SFSObject response = new SFSObject();
		response.putFloat("x", pos.floatX());
		response.putFloat("y", pos.floatY());
		send("SetOnMap", response, user);
	}
	
	private void addNPC(User user) {
		Vec3D pos = new Vec3D(0.0f, 0.0f);
		ISFSApi api = SmartFoxServer.getInstance().getAPIManager().getSFSApi();
		try {
			User npc = api.createNPC("NPC Random", user.getZone(), true);
			api.joinRoom(npc, user.getCurrentMMORoom());
			ISFSMMOApi mmoApi = SmartFoxServer.getInstance().getAPIManager().getMMOApi();
			mmoApi.setUserPosition(npc, pos, user.getCurrentMMORoom());
			List<UserVariable> variables = initialUserVars();
			api.setUserVariables(npc, variables);
		} catch (SFSLoginException | SFSJoinRoomException e) {
			
		}
	}
}
