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
		Random r = new Random();
		int randomX = r.nextInt(2 * width) - width;
		int randomY = r.nextInt(2 * height) - height;
		ISFSMMOApi mmoApi = SmartFoxServer.getInstance().getAPIManager().getMMOApi();
		Vec3D pos = new Vec3D((float)randomX, (float)randomY);
		Room room = user.getCurrentMMORoom();
		mmoApi.setUserPosition(user, pos, room);
		trace("Should set new user Position" + user.getName()+" " + mmoApi);
		sendSetOnMapResponse(pos, user);
		addNPC(user);
		ISFSApi api = SmartFoxServer.getInstance().getAPIManager().getSFSApi();
		List<UserVariable> variables = new ArrayList<>();
		variables.add(new SFSUserVariable("rot", 0.0f));
		api.setUserVariables(user, variables);
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
			List<UserVariable> variables = new ArrayList<>();
			variables.add(new SFSUserVariable("rot", 0.0f));
			api.setUserVariables(npc, variables);
			trace("Created NPC");
			
		} catch (SFSLoginException | SFSJoinRoomException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
