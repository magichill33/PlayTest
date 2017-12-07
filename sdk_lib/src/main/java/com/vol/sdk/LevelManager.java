package com.vol.sdk;



public class LevelManager{
	private static LevelManager instance = new LevelManager();

	public static enum Level {
		LEVEL_NORMAL,
		LEVEL_CHILDREN
	}
	
	private Level currentLevel = null;
	public Level getCurrentLevel() {
		return currentLevel;
	}
	public void setCurrentLevel(Level currentLevel) {
		this.currentLevel = currentLevel;
	}

	private LevelManagerListener managerListener = null;
	public void setLevelManagerListener(LevelManagerListener managerListener) {
		this.managerListener = managerListener;
	}

	public LevelManagerListener getLevelManagerListener() {
		return managerListener;
	}


	private LevelManager() {
	}

	public static LevelManager GetInstance() {
		return instance;
	}

	public interface LevelManagerListener {
		public String getInterfaceVersionCode();

		public boolean getSupport3D();

		public Level getLevel();
	}
}
