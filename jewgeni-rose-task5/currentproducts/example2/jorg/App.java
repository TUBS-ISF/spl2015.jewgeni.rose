package jorg; 

import jorg.app_features.Calculator; 

import jorg.app_features.Clock; 

import jorg.UserMenu; 

import jorg.app_features.Notes; 

/**
 * @author rose
 */
public final   class  App  implements Runnable {
	

	 private void  init__wrappee__Calculator  () {
		original();
		addFeature(new Calculator());
	}

	

	 private void  init__wrappee__Clock  () {
		init__wrappee__Calculator();
		addFeature(new Clock());
	}

	

	 private void  init__wrappee__MultiUserSupport  () {
		init__wrappee__Clock();
		
		final UserMenu sys = new UserMenu(userSystem, features);
		userSystem = sys;
		addFeature(sys);
	}

	

	private void init() {
		init__wrappee__MultiUserSupport();
		addFeature(new Notes());
	}


}
