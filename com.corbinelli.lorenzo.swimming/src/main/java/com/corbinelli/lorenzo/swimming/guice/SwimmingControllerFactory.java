package com.corbinelli.lorenzo.swimming.guice;

import com.corbinelli.lorenzo.swimming.controller.SwimmingController;
import com.corbinelli.lorenzo.swimming.view.SwimmerView;

public interface SwimmingControllerFactory {
	
	SwimmingController create(SwimmerView view);

}
