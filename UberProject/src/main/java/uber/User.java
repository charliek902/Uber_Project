package uber;

import java.util.*;

abstract public class User {
    public String fullName;
    public GeoLocation currentLocation;
    public Double rating;
    public HashMap<String, ArrayList<Double>> pastRatings;
    public String email;
    public Boolean isLocked;
    Ride currentRide;

    public User(String fullName, GeoLocation location, String email){
        this.fullName = fullName;
        this.currentLocation = location;
        this.rating = 0.0;
        this.pastRatings = new HashMap<String, ArrayList<Double>>();
        this.email = email;
        this.isLocked = false;
    }

    public Double getRating(){
        return this.rating;
    }

    public String getEmail(){
        return this.email;
    }

    public String getFullName(){
        return this.fullName;
    }

    public GeoLocation getCurrentLocation(){
        return this.currentLocation;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setCurrentLocation(GeoLocation currentLocation) {
        this.currentLocation = currentLocation;
    }

    public void setCurrentRide(Ride ride){
        this.currentRide = ride;
    }
    public Ride getCurrentRide(){
        if(currentRide == null){
            return null;
        }
        return this.currentRide;
    }

    public void addRating(String email, Double rating){
        if(!this.pastRatings.containsKey(email)){
            this.pastRatings.put(email, new ArrayList<Double>());
        }
        this.pastRatings.get(email).add(rating);
        this.rating = this.setRating();
    }

    private Double setRating(){
        Double summedOverall = 0.0;
        Integer count = 0;
        for (ArrayList<Double> ratingsPerCustomer : pastRatings.values()) {
            count += ratingsPerCustomer.size();
            for (Double rating : ratingsPerCustomer) {
                summedOverall += rating;
            }
        }
        return summedOverall / count;
    }

}
