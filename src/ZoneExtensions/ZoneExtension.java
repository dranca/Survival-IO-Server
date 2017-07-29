package ZoneExtensions;

import com.smartfoxserver.v2.extensions.SFSExtension;

import Handlers.PositionPlayerRandomlyHandler;

public class ZoneExtension extends SFSExtension {

	@Override
	public void init() {
		// TODO Auto-generated method stub
		addRequestHandler("PositionRandom", PositionPlayerRandomlyHandler.class);
	}
}
