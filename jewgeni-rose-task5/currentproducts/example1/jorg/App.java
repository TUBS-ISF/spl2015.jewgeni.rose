package jorg; 

import jorg.app_features.Calculator; 

import jorg.app_features.Clock; 

/**
 * @author rose
 */
public final   class  App  implements Runnable {
	

	 private void  init__wrappee__Calculator  () {
		original();
		addFeature(new Calculator());
	}

	

	private void init() {
		init__wrappee__Calculator();
		addFeature(new Clock());
	}


}
