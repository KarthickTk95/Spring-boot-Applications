package com.newReports.controller;

import java.util.List;


import com.newReports.entity.ZanDeviceData;
import com.newReports.repository.ZanDeviceDataRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RestController
@CrossOrigin("*")
@RequestMapping("/Newreports")
public class ZanDeviceDataController {

	@Autowired
	ZanDeviceDataRep zanDeviceDataRep;

	@GetMapping("/getZanDeviceData")
	@PreAuthorize("hasAuthority('AC_ACTIVE')")
	public List<ZanDeviceData> getZanDeviceData(@RequestParam String deviceMacId, @RequestParam String yearmonth, @RequestParam String topic) {
		return zanDeviceDataRep.getZanDeviceData(deviceMacId, yearmonth, topic);
	}

	@GetMapping("/getZanDeviceDataTopic")
	@PreAuthorize("hasAuthority('AC_ACTIVE')")
	public List<ZanDeviceData> getZanDeviceDataTopic(@RequestParam String deviceMacId, @RequestParam String yearmonth) {
		return zanDeviceDataRep.getZanDeviceDataTopic(deviceMacId, yearmonth);
	}
}
