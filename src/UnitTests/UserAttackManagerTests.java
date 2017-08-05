package UnitTests;

import static org.junit.Assert.*;
import org.junit.Test;

import com.smartfoxserver.v2.mmo.Vec3D;

import Actions.UsersAttackManager;

public class UserAttackManagerTests {
	
	@Test
	public void getAngle_givenStartingPoints_shouldGiveAndle() {
		float result = UsersAttackManager.getAngle(0, 0);
		
		assertEquals("", 0.0f, result, 0.0f);
	}
	
	@Test
	public void getAngle_givenOneOne_shouldGive45() {
		float result = UsersAttackManager.getAngle(1, 1);
		
		assertEquals("", 45.0f, result, 0.0f);
	}
	
	@Test
	public void getAngle_givenTwoZero_shouldGive90() {
		float result = UsersAttackManager.getAngle(2, 0);
		
		assertEquals("", 90.0f, result, 0.0f);
	}
	
	@Test
	public void getAngle_angleInBetween_shouldReturnTrue() {
		boolean result = UsersAttackManager.angleIsInRange(0, 10, 5);
		
		assertTrue(result);
	}
	
	@Test
	public void getAngle_givenAngleOnLowerRange_shouldReturnTrue() {
		boolean result = UsersAttackManager.angleIsInRange(0, 10, 0);
		
		assertTrue(result);
	}
	
	@Test
	public void getAngle_givenAngleOnUpperRange_shouldReturnTrue() {
		boolean result = UsersAttackManager.angleIsInRange(0, 10, 10);
		
		assertTrue(result);
	}
	
	@Test
	public void getAngle_givenAngleBellowLowerRange_shouldReturnFalse() {
		boolean result = UsersAttackManager.angleIsInRange(0, 10, -4);
		assertFalse(result);
	}
	
	@Test
	public void getAngle_givenAngleAboveLowerRange_shouldReturnFalse() {
		boolean result = UsersAttackManager.angleIsInRange(0, 10, 11);
		
		assertFalse(result);
	}
	
	@Test
	public void isInFront_givenZeros_shouldReturnTrue() {
		Vec3D currentUserPos = new Vec3D(0.0f, 0.0f);
		Vec3D targetUserPos = new Vec3D(0.0f, 0.0f);
		float rotation = 0;
		boolean result = UsersAttackManager.userIsInFront(currentUserPos, targetUserPos, rotation);
		assertTrue(result);
	}
	
	@Test
	public void isInFront_givenTargetOutsideRange_shouldReturnFalse() {
		Vec3D currentUserPos = new Vec3D(0.0f, 0.0f);
		Vec3D targetUserPos = new Vec3D(-1.0f, -1.0f);
		float rotation = 0;
		boolean result = UsersAttackManager.userIsInFront(currentUserPos, targetUserPos, rotation);
		assertFalse(result);
	}
	
	@Test
	public void isInFront_givenTargetsFarFromCenterButStillCorrect_shouldReturnTrue() {
		Vec3D currentUserPos = new Vec3D(90.0f, 90.0f);
		Vec3D targetUserPos = new Vec3D(91.0f, 91.0f);
		float rotation = 0;
		boolean result = UsersAttackManager.userIsInFront(currentUserPos, targetUserPos, rotation);
		assertTrue(result);
	}
	
	@Test
	public void isInFront_givenTargetRightInFront_shouldReturnTrue() {
		Vec3D currentUserPos = new Vec3D(0.0f, 0.0f);
		Vec3D targetUserPos = new Vec3D(0.0f, 1.0f);
		float rotation = 0;
		boolean result = UsersAttackManager.userIsInFront(currentUserPos, targetUserPos, rotation);
		assertTrue(result);
	}
	
	@Test
	public void isInFront_givenKnownWrongPositionButWithCorrectAngle_shouldReturnTrue() {
		Vec3D currentUserPos = new Vec3D(0.0f, 0.0f);
		Vec3D targetUserPos = new Vec3D(0.0f, -1.0f);
		float rotation = 180;
		boolean result = UsersAttackManager.userIsInFront(currentUserPos, targetUserPos, rotation);
		assertTrue(result);
	}
	
	
}
