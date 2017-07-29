package Handlers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.smartfoxserver.v2.SmartFoxServer;
import com.smartfoxserver.v2.api.ISFSApi;
import com.smartfoxserver.v2.api.ISFSMMOApi;
import com.smartfoxserver.v2.core.ISFSEvent;
import com.smartfoxserver.v2.core.SFSEventParam;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.variables.UserVariable;
import com.smartfoxserver.v2.exceptions.SFSException;
import com.smartfoxserver.v2.extensions.BaseServerEventHandler;
import com.smartfoxserver.v2.mmo.Vec3D;

public class UserVariablesHandler extends BaseServerEventHandler {
	@Override
    public void handleServerEvent(ISFSEvent event) throws SFSException
    {
        @SuppressWarnings("unchecked")
        List<UserVariable> variables = (List<UserVariable>) event.getParameter(SFSEventParam.VARIABLES);
        User user = (User) event.getParameter(SFSEventParam.USER);
        
        // Make a map of the variables list
        Map<String, UserVariable> varMap = new HashMap<>();
        for (UserVariable var : variables)
        {
            varMap.put(var.getName(), var);
        }
        if (varMap.containsKey("x") && varMap.containsKey("y"))
        {
            Vec3D pos = new Vec3D
            (
                varMap.get("x").getDoubleValue().floatValue(),
                1.0f,
                varMap.get("y").getDoubleValue().floatValue()
            );
            ISFSMMOApi mmoApi = SmartFoxServer.getInstance().getAPIManager().getMMOApi();
            mmoApi.setUserPosition(user, pos, user.getCurrentMMORoom());
        }
    }
}
