package Inventory;

import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSArray;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSArray;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.entities.variables.SFSUserVariable;
import com.smartfoxserver.v2.entities.variables.UserVariable;
import com.smartfoxserver.v2.exceptions.SFSVariableException;

public class InventoryManager {
	public static final String inventoryKey = "inv";
	
	static void initiateManagerForUser(User user) {
		if (!user.containsVariable(inventoryKey)) {
			SFSArray userInv = new SFSArray();
			setInventory(user, userInv);
		}
	}
	
	public static ISFSArray userInventory(User user) {
		if (user.containsVariable(inventoryKey)) {
			UserVariable variable = user.getVariable(inventoryKey);
			return variable.getSFSArrayValue();
		} else {
			initiateManagerForUser(user);
			return userInventory(user);
		}
	}
	
	private static void setInventory(User user, ISFSArray inventory) {
		SFSUserVariable inventoryVariable = new SFSUserVariable(inventoryKey, inventory);
		try {
			user.setVariable(inventoryVariable);
		} catch (SFSVariableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void addItemInInventory(User user, InventoryObject product) {
		ISFSArray inventory = userInventory(user);
		ISFSObject object = product.createSmartfoxObject();
		ISFSObject objectFromInventory = getObjectWithItemKeyFromInventory(inventory,product);
		if (objectFromInventory != null) {
			int count = objectFromInventory.getInt(InventoryObject.itemCountKey);
			count += product.quantity;
			objectFromInventory.putInt(InventoryObject.itemCountKey, count);
		} else {
			object.putInt(InventoryObject.itemCountKey, product.quantity);
			inventory.addSFSObject(object);
		}
		setInventory(user, inventory);
	}
	
	public static void handleInventoryStatusChanged(User user) {
		// TODO: Delete object
	}
	
	private static ISFSObject getObjectWithItemKeyFromInventory(ISFSArray inventory, InventoryObject product) {
		for (int i = 0 ; i < inventory.size(); i++) {
			ISFSObject invObject = inventory.getSFSObject(i);
			if (invObject.containsKey(InventoryObject.itemIdKey)) { 
				if (invObject.getText(InventoryObject.itemIdKey).equals(product.itemId)) {
					return invObject;
				}
			} 
		}
		return null;
	}
	
	public static void setItemAsEquiped(InventoryObject product) {
		
	}
//	public static InventoryObject 
}
