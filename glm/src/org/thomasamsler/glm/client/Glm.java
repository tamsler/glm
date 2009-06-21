package org.thomasamsler.glm.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.control.SmallMapControl;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;


public class Glm implements EntryPoint {

	private static VerticalPanel mainPanel = null;
	
	private static FlowPanel consoleFlowPanel = null;
	
	public Glm() {
		
		mainPanel = new VerticalPanel();
		consoleFlowPanel = new FlowPanel();
		
	}

	public void onModuleLoad() {
		
		mainPanel.setWidth("100%");
		consoleFlowPanel.setWidth("400px");
		consoleFlowPanel.setHeight("30px");
		
		DecoratorPanel decoratorPanel = new DecoratorPanel();
		decoratorPanel.add(consoleFlowPanel);
		mainPanel.add(decoratorPanel);
		
		if(!supportsGeoLocation()) {
			consoleFlowPanel.add(new HTML("Browser does not have HTML 5 Geo Location support"));
		}
		else {
			getGeoLocation();
		}

		RootPanel.get("verticalPanel").add(mainPanel);

	}

	public static void geoLocationCallback(Position position) {
		
		consoleFlowPanel.add(new HTML(position.getLatitude() + " : " + position.getLongitude()));
		LatLng myLocation = LatLng.newInstance(position.getLatitude(), position.getLongitude());
		MapWidget map = new MapWidget(myLocation, 12);
        map.setSize("400px", "300px");
        map.addControl(new SmallMapControl());
        map.addOverlay(new Marker(myLocation));
        map.panTo(myLocation);
        mainPanel.add(map);
	}
	
	public static void geoLocationCallbackError(JavaScriptObject jso) {
		// TODO:
	}

	private native boolean supportsGeoLocation() /*-{
		
		if(null == $wnd.navigator.geolocation) {
			return false;
		}
		else {
			return true;
		}
	}-*/;
	
	private native void getGeoLocation() /*-{
		
		if(null == $wnd.navigator.geolocation) {
			return;
		} 

		$wnd.navigator.geolocation.getCurrentPosition(@org.thomasamsler.glm.client.Glm::geoLocationCallback(Lorg/thomasamsler/glm/client/Position;),
		                                              @org.thomasamsler.glm.client.Glm::geoLocationCallbackError(Lcom/google/gwt/core/client/JavaScriptObject;),
		                                              {enableHighAccuracy:true, maximumAge:60000});
		
	}-*/;
}
