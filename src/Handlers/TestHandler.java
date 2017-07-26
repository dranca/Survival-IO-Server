package Handlers;

import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;

public class TestHandler extends BaseClientRequestHandler {

	@Override
	public void handleClientRequest(User user, ISFSObject args) {
		
		int numA = args.getInt("numA");
		int numB = args.getInt("numB");
		
		ISFSObject objOut = new SFSObject();
		objOut.putInt("result", numA + numB); 
		
		send("SumNumbers", objOut, user);
	}

}
