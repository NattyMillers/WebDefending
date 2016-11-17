package io.muic.dcom.p2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class DataModel {
    public static class ParcelObserved {
        private String parcelId;
        private String stationId;
        private long timeStamp;


        ParcelObserved(String parcelId_, String stationId_, long ts_) {
            this.parcelId = parcelId_;
            this.stationId = stationId_;
            this.timeStamp = ts_;
        }

        public String getParcelId() { return parcelId; }
        public String getStationId() { return stationId; }
        public long getTimeStamp() { return timeStamp; }

    }

    private ConcurrentHashMap<String, List<ParcelObserved>> hashTrail = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, Long> hashCount = new ConcurrentHashMap<>();

    DataModel() {


    }

    /// IMPLEMENTS CONSTRUCTOR //
    public void postObserve(String parcelId, String stationId, long timestamp) {
        ParcelObserved parcelObserved = new ParcelObserved(parcelId, stationId, timestamp);

        if (hashCount.containsKey(stationId)){
            hashCount.put(stationId, hashCount.get(stationId)+ 1L);}

        else{
            hashCount.put(stationId, 1L);}

        if (!hashTrail.containsKey(parcelId)){
            List<ParcelObserved> a = new ArrayList<>(Arrays.asList(parcelObserved));
            hashTrail.put(parcelId, a);}

        else {
            hashTrail.get(parcelId).add(parcelObserved);
        }

    }


    // Display only that parceID  <parceID, that station>
    public List<ParcelObserved> getParcelTrail(String parcelId) {
        if (hashTrail.containsKey(parcelId)){
            return  new ArrayList<>(hashTrail.get(parcelId));
        }
        else {
            return new ArrayList<>();
        }

    }

    // Count only if that station ID < StationID , howmany (Count) >
    public long getStopCount(String stationId) {
        if (!hashCount.containsKey(stationId)){ return 0L;}
        else{
        return hashCount.get(stationId);
        }
    }
}
