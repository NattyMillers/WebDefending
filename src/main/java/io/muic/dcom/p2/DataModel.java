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

    private ConcurrentHashMap<String, ArrayList<ParcelObserved>> hashTrail = new ConcurrentHashMap<>();

    private ConcurrentHashMap<String, Long> hashCount = new ConcurrentHashMap<>();
    private Long count ;

    DataModel() {
//        hashTrail = new ConcurrentHashMap<>();
//        hashCount = new ConcurrentHashMap<>();
//        count = 1L;



    }

    /// IMPLEMENTS CONSTRUCTOR //
    public void postObserve(String parcelId, String stationId, long timestamp) {

        ParcelObserved parcelObserved = new ParcelObserved(parcelId, stationId, timestamp);

        if (!hashTrail.containsKey(parcelId)){
            ArrayList<ParcelObserved> b = new ArrayList<>(Arrays.asList(parcelObserved));
            hashTrail.put(parcelId, b);
        }else {
            hashTrail.get(parcelId).add(parcelObserved);
        }

        if (!hashCount.containsKey(stationId)){
            hashCount.put(stationId, 1L);
        }else{
            long counter = hashCount.get(stationId);
            counter += 1L;
            hashCount.put(stationId, counter);

        }

    }
    // Display only that parceID  <parceID, that station>
    public ArrayList<ParcelObserved> getParcelTrail(String parcelId) {

        if (!hashTrail.containsKey(parcelId)) {
            return new ArrayList<>();
        }else{
        return hashTrail.get(parcelId);
        }
    }

    // Count only if that station ID < StationID , howmany (Count) >
    public long getStopCount(String stationId) {
        return hashCount.get(stationId);
    }
}
