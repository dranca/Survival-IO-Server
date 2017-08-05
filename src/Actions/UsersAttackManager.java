package Actions;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.smartfoxserver.v2.SmartFoxServer;
import com.smartfoxserver.v2.api.ISFSApi;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.variables.SFSUserVariable;
import com.smartfoxserver.v2.entities.variables.UserVariable;
import com.smartfoxserver.v2.mmo.Vec3D;

import Logger.LoggerSFS;

public class UsersAttackManager {
	
	private static UsersAttackManager singleton = new UsersAttackManager();
	private List<User> attackingUsers = new ArrayList<User>();
	private Timer timer = new Timer(true);
	
	private Map<User, Long> attacks = new HashMap<User, Long>();
	
	private UsersAttackManager() {
		timer.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				for (User u : attackingUsers) {
					if (u != null) {
						attack(u);
					} else {
						LoggerSFS.log("User Is null");
					}
				}
			}
		}, 0, 100);
	}
	
	public static UsersAttackManager instance() {
		return singleton;
	}
	
	public void userStartedAttacking(User u) {
		attackingUsers.add(u);
	}
	
	public void userStoppedAttacking(User u) {
		if (attackingUsers.contains(u)) {
			attackingUsers.remove(u);
		}
	}
	
	private void attack(User user) {
		long currentServerTime = user.getLastRequestTime();
		if (attacks.containsKey(user)) {
			long lastAttack = attacks.get(user);
			if (currentServerTime - lastAttack < 1000) {
				return;
			}
		}
		attacks.put(user, currentServerTime);
		Vec3D aoi = new Vec3D(2.0f, 2.0f);
		ISFSApi api = SmartFoxServer.getInstance().getAPIManager().getSFSApi();
		List<User> usersInRange = user.getCurrentMMORoom().getProximityList(user, aoi);
		LoggerSFS.log("Users found: " + usersInRange);
		for (User target : usersInRange) {
			LoggerSFS.log("Have found an user");
			if (userIsInFront(user, target)) {
				LoggerSFS.log("User Is In Front");
				float prevHP = target.getVariable("hp").getDoubleValue().floatValue();
				List<UserVariable> variables = new ArrayList<>();
				float damage = damage(user, target);
				if (prevHP <= damage) { 
					api.leaveRoom(target, target.getLastJoinedRoom());
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
