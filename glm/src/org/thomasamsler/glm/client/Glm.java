package org.thomasamsler.glm.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.gears.client.Factory;
import com.google.gwt.gears.client.geolocation.Geolocation;
import com.google.gwt.gears.client.geolocation.PositionHandler;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.control.LargeMapControl;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;


public class Glm implements EntryPoint {

	private VerticalPanel mainPanel = null;
	
	private FlowPanel consoleFlowPanel = null;
	private Geolocation geolocation;
	
	private MapWidget map;
	
	LatLng myLocation = null;
	
	public Glm() {
		
		mainPanel = new VerticalPanel();
		consoleFlowPanel = new FlowPanel();
		
	}

	public void onModuleLoad() {
		
		mainPanel.setWidth("100%");
		consoleFlowPanel.setWidth("500px");
		consoleFlowPanel.setHeight("100px");
		
		DecoratorPanel decoratorPanel = new DecoratorPanel();
		decoratorPanel.add(consoleFlowPanel);
		mainPanel.add(decoratorPanel);

		consoleFlowPanel.add(new HTML("Finding your location ..."));
		geolocation = Factory.getInstance().createGeolocation();
		
		geolocation.getCurrentPosition(new PositionHandler() {

			public void onPosition(PositionEvent position) {
				consoleFlowPanel.clear();
//				consoleFlowPanel.add(new HTML("Lat=" + getClientLatitude()));
//				consoleFlowPanel.add(new HTML("Lon=" + getClientLongitude()));
				consoleFlowPanel.add(new HTML("Latitude=" + position.getPosition().getLatitude()));
				consoleFlowPanel.add(new HTML("Longitue=" + position.getPosition().getLongitude()));
				myLocation = LatLng.newInstance(position.getPosition().getLatitude(),position.getPosition().getLongitude());
				map = new MapWidget(myLocation, 10);
				map.setSize("500px", "300px");
				map.addControl(new LargeMapControl());
				map.addOverlay(new Marker(myLocation));
				mainPanel.add(map);
				
				
			}
			
		});

		RootPanel.get("verticalPanel").add(mainPanel);

	}


	private native double getClientLatitude() /*-{
		return ($wnd.google.loader.ClientLocation == null ? 0 : Number($wnd.google.loader.ClientLocation.latitude));
	}-*/;

	private native double getClientLongitude() /*-{
		return ($wnd.google.loader.ClientLocation == null ? 0 : Number($wnd.google.loader.ClientLocation.longitude));
	}-*/;


}
