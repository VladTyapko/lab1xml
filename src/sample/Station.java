package sample;

public class Station {
    public int station_id;
    public String name;



    public Station(int station_id, String name) {
        this.station_id = station_id;
        this.name = name;
    }

    @Override
    public String toString(){
        return "Station{" +
                "station_id" + station_id +
                ", name=" + name +
                "}";
    }
}

