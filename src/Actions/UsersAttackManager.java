package Actions;

import java.util.ArrayList;
import java.util.List;

import com.smartfoxserver.v2.SmartFoxServer;
import com.smartfoxserver.v2.api.ISFSApi;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.variables.SFSUserVariable;
import com.smartfoxserver.v2.entities.variables.UserVariable;
import com.smartfoxserver.v2.mmo.Vec3D;

public class UsersAttackManager {
	public static void attack(User user) {
		Vec3D aoi = new Vec3D(2.0f, 2.0f);
		ISFSApi api = SmartFoxServer.getInstance().getAPIManager().getSFSApi();
		List<User> usersInRange = user.getCurrentMMORoom().getProximityList(user, aoi);
		for (User target : usersInRange) {
			if (userIsInFront(user, target)) {
				float prevHP = target.getVariable("hp").getDoubleValue().floatValue();
				List<UserVariable> variables = new ArrayList<>();
				float damage = damage(user, target);
				if (prevHP <= damage) { 
					api.disconnectUser(target);
					variables.add(new SFSUserVariable("kill", 1.0f));
					api.setUserVariables(user, variables);
					return;
				}
				variables.add(new SFSUserVariable("hp", prevHP - damage));
				api.setUserVariables(target, variables);
			}
		}
	}
	
	private static float damage(User user, User target) {
		// TODO Add propper implementation
		return 10.0f;
	}
	
	private static boolean userIsInFront(User currentUser, User target){
		float rotation = currentUser.getVariable("rot").getDoubleValue().floatValue();
		// TODO add propper implementation
		if (rotation < 90) {
			
		} else if (rotation < 180) {
			
		} else if (rotation < 270) {
			
		} else {
			
		}
		return true;
	}
	
}
