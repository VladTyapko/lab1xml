package sample;

import java.util.ArrayList;

public class Line {
    public int line_id;
    public String color;
    private final ArrayList<Station>  stations = new ArrayList<>();

    Line(int line_id, String color) {
        this.line_id = line_id;
        this.color = color;

    }


    public ArrayList<Station> getStations() {
        return stations;
    }

    public void addStation(Station station){
        stations.add(station);
    }

    public Station addStationsById(int code) {
        for (int i = 0; i < stations.size(); i++) {
            if (stations.get(i).station_id == code) {
                return stations.get(i);
            }
        }
        return null;
    }

    public Station findStationById(int id){
        for (Station station: stations) {
            if (station.station_id == id){ return station;}
        }
        return null;
    }

    @Override
    public  String toString(){
        return System.lineSeparator() + "Line{" +
                "line_id=" + line_id +
                ",color=" + color + '\'' +
                ", stations=" + stations +
                "}";

    }

}

