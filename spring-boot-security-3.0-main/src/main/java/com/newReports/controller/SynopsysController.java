package com.newReports.controller;

import java.util.ArrayList;
import java.util.List;


import com.newReports.entity.CarparkingModel;
import com.newReports.entity.ClientModel;
import com.newReports.repository.SynopsysRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin("*")
@RequestMapping("/Newreports")
public class SynopsysController {
	
	@Autowired
	SynopsysRep synopsysRep;
	


	  @GetMapping ("/getParkingClients")
	  @PreAuthorize("hasAuthority('AC_ACTIVE')")
	  public ResponseEntity<List<ClientModel>> getParkingClients(){
		  
		  List<ClientModel> parkingClients = synopsysRep.getParkingClients();
		  
		 System.out.println(parkingClients);
		 
			return new ResponseEntity<List<ClientModel>>(parkingClients, HttpStatus.OK);
		  
	
		  
	  }
	  
	  
	  @GetMapping("/getParkingBuildings")
	  @PreAuthorize("hasAuthority('AC_ACTIVE')")
	  public List<String> getParkingBuildings(@RequestParam String parkingClient) {
	        return synopsysRep.getDistinctBuildingNames(parkingClient);
	    }
	  
	  	
		/*
		 * @GetMapping("/synopsys-summary") public List<SynopsysModel>
		 * getSynopsysOldSummary(@RequestParam String date, @RequestParam String
		 * parkingClient,@RequestParam List<String> parkingBuildings) {
		 * 
		 * 
		 * LOGGER.info(date);
		 * 
		 * 
		 * return synopsysDAO.getSynopsysOldSummary(date,
		 * parkingClient,parkingBuildings); }
		 */
	  
	  @GetMapping("/synopsys-summary")
	  @PreAuthorize("hasAuthority('AC_ACTIVE')")
	  public List<CarparkingModel> getSynopsysOldSummary(
	          @RequestParam List<String> dates,
	          @RequestParam String parkingClient,
	          @RequestParam List<String> parkingBuildings) {

	      List<CarparkingModel> results = new ArrayList<>();
	      
	      for (String date : dates) {
	          List<CarparkingModel> summaryForDate = synopsysRep.getSynopsysOldSummary(date, parkingClient, parkingBuildings);
	          results.addAll(summaryForDate);
	      }

	      return results;
	  }

	  
	  
	  
	  @GetMapping("/getBuildingInfo")
	  @PreAuthorize("hasAuthority('AC_ACTIVE')")
	  public List<CarparkingModel> getBuildingInfo(
	            @RequestParam String parkingClient,
	            @RequestParam List<String> parkingBuildings
	    ) {
	        return synopsysRep.getbuildingCurrentTime(parkingClient, parkingBuildings);
	    }
	  
	  
	  
	  
	 




}
