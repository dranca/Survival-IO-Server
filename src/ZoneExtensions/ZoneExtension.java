package ZoneExtensions;


import com.smartfoxserver.v2.core.SFSEventType;
import com.smartfoxserver.v2.extensions.SFSExtension;

import Handlers.PositionPlayerRandomlyHandler;
import Handlers.UserVariablesHandler;
import Logger.LoggerSFS;

public class ZoneExtension extends SFSExtension {

	@Override
	public void init() {
		// TODO Auto-generated method stub
		LoggerSFS.instance().extension = this;
		addRequestHandler("PositionRandom", PositionPlayerRandomlyHandler.class);
		addEventHandler(SFSEventType.USER_VARIABLES_UPDATE, UserVariablesHandler.class);
		trace("Initiating request handlers");
	}
}
