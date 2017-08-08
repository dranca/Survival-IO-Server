package Handlers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.smartfoxserver.v2.SmartFoxServer;
import com.smartfoxserver.v2.api.ISFSMMOApi;
import com.smartfoxserver.v2.core.ISFSEvent;
import com.smartfoxserver.v2.core.SFSEventParam;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.variables.UserVariable;
import com.smartfoxserver.v2.exceptions.SFSException;
import com.smartfoxserver.v2.extensions.BaseServerEventHandler;
import com.smartfoxserver.v2.mmo.Vec3D;

import Actions.UsersAttackManager;
import Helpers.UserHelper;
import Inventory.InventoryManager;
import Inventory.InventoryObject;

public class UserVariablesHandler extends BaseServerEventHandler {
	@Override
    public void handleServerEvent(ISFSEvent event) throws SFSException
    {
		// This will be called ONLY when the client sets the variables.
        @SuppressWarnings("unchecked")
        List<UserVariable> variables = (List<UserVariable>) event.getParameter(SFSEventParam.VARIABLES);
        User user = (User) event.getParameter(SFSEventParam.USER);
        
        // Make a map of the variables list
        Map<String, UserVariable> varMap = new HashMap<>();
        for (UserVariable var : variables)
        {
            varMap.put(var.getName(), var);
        }
        if (varMap.containsKey("x") && varMap.containsKey("y")) {
            Vec3D pos = UserHelper.userPos(user);
            ISFSMMOApi mmoApi = SmartFoxServer.getInstance().getAPIManager().getMMOApi();
            mmoApi.setUserPosition(user, pos, user.getCurrentMMORoom());
        }
        if (varMap.containsKey("atk")) { 
        	Boolean attacking = user.getVariable("atk").getBoolValue();
        	if (attacking) {
        		UsersAttackManager.instance().userStartedAttacking(user);
        	} else {
        		UsersAttackManager.instance().userStoppedAttacking(user);
        	}
        }
        
        if (varMap.containsKey(InventoryManager.inventoryKey)) {
        	InventoryManager.handleInventoryStatusChanged(user);
        }
    }
}
