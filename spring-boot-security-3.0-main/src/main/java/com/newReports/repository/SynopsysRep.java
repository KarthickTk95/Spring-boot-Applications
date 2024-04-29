package com.newReports.repository;



import com.newReports.entity.CarparkingModel;
import com.newReports.entity.ClientModel;

import java.util.List;

public interface SynopsysRep {
    List<ClientModel> getParkingClients();

    List<String> getDistinctBuildingNames(String parkingClient);

    List<CarparkingModel> getSynopsysOldSummary(String date, String parkingClient, List<String> parkingBuildings);

    List<CarparkingModel> getbuildingCurrentTime(String parkingClient, List<String> parkingBuildings);

    List<CarparkingModel> getCarparkingRawData(List<String> dates, String parkingClient, List<String> parkingBuildings);
}
