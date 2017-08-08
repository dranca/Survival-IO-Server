package Inventory;

import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;

public class InventoryObject {
	public static final String itemIdKey = "itemId";
	public static final String itemCountKey = "itemCount";
	public static final String itemNameKey = "itemName";

	public String itemId;
	public int quantity;
	
	public boolean isWeapon() {
		return true;
	}
	
	public boolean isArmor() {
		return false;
	}
	
	public String name() {
		// TODO Add propper implementation (most likely from XML)
		if (itemId.equals("0")) { 
			return "Pickaxe";
		} else {
			return "Spear";
		}
	}
	
	public InventoryObject(ISFSObject smartfoxObject) {
		
	}
	
	public InventoryObject() {
		
	}
	
	public ISFSObject createSmartfoxObject() {
		ISFSObject sfsObject = new SFSObject();
		sfsObject.putText(itemIdKey, itemId);
		sfsObject.putInt(itemCountKey, quantity);
		sfsObject.putText(itemNameKey, name());
		return sfsObject;
	}
}
