package Helpers;

import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.mmo.Vec3D;

import Logger.LoggerSFS;

public class UserHelper {
	public static Vec3D userPos(User user) {
		float x = 0;
		float y = 0;
		if (user.containsVariable("x")) {
			x = user.getVariable("x").getDoubleValue().floatValue();
		}
		if (user.containsVariable("y")) {
			y = user.getVariable("y").getDoubleValue().floatValue();
		}
		return new Vec3D(x, y);
	}
	
	public static float userRotation(User user) {
		float result = 360 - user.getVariable("rot").getDoubleValue().floatValue();
		LoggerSFS.log("result : " + result);
		return result;
	}
}
