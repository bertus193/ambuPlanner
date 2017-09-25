package com.example.ambuplanner.model;

public class MapRequest {

    private String mapJSON;

    private String notificationsJSON;

    public MapRequest() {
    }

    public MapRequest(String mapJSON, String notificationsJSON) {
        this.mapJSON = mapJSON;
        this.notificationsJSON = notificationsJSON;
    }

    public String getMapJSON() {
        return mapJSON;
    }

    public void setMapJSON(String mapJSON) {
        this.mapJSON = mapJSON;
    }

    public String getNotificationsJSON() {
        return notificationsJSON;
    }

    public void setNotificationsJSON(String notificationsJSON) {
        this.notificationsJSON = notificationsJSON;
    }
}
