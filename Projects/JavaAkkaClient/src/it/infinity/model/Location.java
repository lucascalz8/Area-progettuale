package it.infinity.model;

import java.io.Serializable;

public class Location implements Serializable {
	private static final long serialVersionUID = 8888L;
	
	private double latitude;
	private double longitude;
	
	public Location(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public double getLatitude() {
		return (latitude);
	}
	
	public double getLongitude() {
		return (longitude);
	}

	public double distanceBetween(Location location1, Location location2) {
		double earthRadius = 6371 * 10^3;
		
		double latitude1 = location1.getLatitude();
		double longitude1 = location1.getLongitude();
		double latitude2 = location2.getLatitude();
		double longitude2 = location2.getLongitude();
		
		double φ1 = Math.toRadians(latitude1);
		double φ2 = Math.toRadians(latitude2);
		double Δφ = Math.toRadians(latitude2 - latitude1);
		double Δλ = Math.toRadians(longitude2 - longitude1);

		double a = Math.sin(Δφ / 2) * Math.sin(Δφ / 2) + 
				   Math.cos(φ1) * Math.cos(φ2) *
				   Math.sin(Δλ / 2) * Math.sin(Δλ / 2);
		
		double b = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

		double distance = earthRadius * b;
		return (distance);
	}
	
}
