package edu.umb.cs681.hw09;

import java.util.ArrayList;
import java.util.List;

public record Position(double latitude,double longitude,double altitude) {
	public List<Double> coordinate() {
		List<Double> coorddinates = new ArrayList<Double>();
		coorddinates.add(latitude);
		coorddinates.add(longitude);
		coorddinates.add(altitude);
		return coorddinates;
    }

    public Position change(double newLat,double newLon,double newAlt) {
        return new Position(newLat,newLon,newAlt);
    }

    public boolean higherAltThan(Position anotherPosition) {
        return altitude > anotherPosition.altitude();
    }

    public boolean lowerAltThan(Position anotherPosition) {
        return altitude < anotherPosition.altitude();
    }

    public boolean northOf(Position anotherPosition) {
        return latitude > anotherPosition.latitude();
    }

    public boolean southOf(Position anotherPosition) {
        return latitude < anotherPosition.latitude();
    }
    
    public static void main(String[] args) {
        Position position = new Position(10,20,30);
        System.out.println("Positions coordinates before change: "+position.coordinate());
        Position anotherposition = position.change(25,15,50);
        System.out.println("Positions coordinates After change: "+position.coordinate());
        System.out.println("New instances coordinates: "+anotherposition.coordinate());
        System.out.println("Old position compared to new =====");
        System.out.println("Higher altitude :"+position.higherAltThan(anotherposition));
        System.out.println("Lower altitude :"+position.lowerAltThan(anotherposition));
        System.out.println("North of :"+position.northOf(anotherposition));
        System.out.println("South of :"+position.southOf(anotherposition));
    }

}
