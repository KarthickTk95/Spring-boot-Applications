package com.newReports.controller;


import com.newReports.entity.DeviceStatus;
import com.newReports.repository.SummarySheetRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/Newreports")
public class SummarySheetController {
    @Autowired
    SummarySheetRep summarySheetRep;

    @GetMapping("/getTotalDevices")
    @PreAuthorize("hasAuthority('AC_ACTIVE')")
    public ResponseEntity<List<DeviceStatus>> getTotalDevices(@RequestParam String clientname) {
        List<DeviceStatus> devices = summarySheetRep.getTotalDevices(clientname);
        if (devices == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(devices, HttpStatus.OK);
    }

    @GetMapping("/getActiveDevices")
    @PreAuthorize("hasAuthority('AC_ACTIVE')")
    public ResponseEntity<List<DeviceStatus>> getActiveDevices(@RequestParam String clientname) {
        List<DeviceStatus> devices = summarySheetRep.getActiveDevices(clientname);
        if (devices == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(devices, HttpStatus.OK);
    }


    @GetMapping("/getStoppedDevices")
    @PreAuthorize("hasAuthority('AC_ACTIVE')")
    public ResponseEntity<List<DeviceStatus>> getStoppedDevices(@RequestParam String clientname) {
        List<DeviceStatus> devices = summarySheetRep.getStoppedDevices(clientname);
        if (devices == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(devices, HttpStatus.OK);
    }


    @GetMapping("/getPreStoppedDevices")
    @PreAuthorize("hasAuthority('AC_ACTIVE')")
    public ResponseEntity<List<DeviceStatus>> getPreStoppedDevices(@RequestParam String clientname) {
        List<DeviceStatus> devices = summarySheetRep.getPreStopped(clientname);
        if (devices == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(devices, HttpStatus.OK);
    }

    @GetMapping("/getNotyetDevices")
    @PreAuthorize("hasAuthority('AC_ACTIVE')")
    public ResponseEntity<List<DeviceStatus>> getNotyetDevices(@RequestParam String clientname) {
        List<DeviceStatus> devices = summarySheetRep.getNotyetDevices(clientname);
        if (devices == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(devices, HttpStatus.OK);
    }

    @GetMapping("/getBlegatewayDevices")
    @PreAuthorize("hasAuthority('AC_ACTIVE')")
    public ResponseEntity<List<DeviceStatus>> getBlegatewayDevices(@RequestParam String clientname) {
        List<DeviceStatus> devices = summarySheetRep.getBlegatewayDevices(clientname);
        if (devices == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(devices, HttpStatus.OK);
    }

    @GetMapping("/getBleDevices")
    @PreAuthorize("hasAuthority('AC_ACTIVE')")
    public ResponseEntity<List<DeviceStatus>> getBleDevices(@RequestParam String clientname) {
        List<DeviceStatus> devices = summarySheetRep.getBleDevices(clientname);
        if (devices == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(devices, HttpStatus.OK);
    }

    @GetMapping("/getIntrafficDevices")
    @PreAuthorize("hasAuthority('AC_ACTIVE')")
    public ResponseEntity<List<DeviceStatus>> getIntrafficDevices(@RequestParam String clientname) {
        List<DeviceStatus> devices = summarySheetRep.getIntrafficDevices(clientname);
        if (devices == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(devices, HttpStatus.OK);
    }


    @GetMapping("/getfeedbackDevices")
    @PreAuthorize("hasAuthority('AC_ACTIVE')")
    public ResponseEntity<List<DeviceStatus>> getfeedbackDevices(@RequestParam String clientname) {
        List<DeviceStatus> devices = summarySheetRep.getfeedbackDevices(clientname);
        if (devices == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(devices, HttpStatus.OK);
    }

    @GetMapping("/getoccupancyDisplayDevices")
    @PreAuthorize("hasAuthority('AC_ACTIVE')")
    public ResponseEntity<List<DeviceStatus>> getoccupancyDisplayDevices(@RequestParam String clientname) {
        List<DeviceStatus> devices = summarySheetRep.getoccupancyDisplayDevices(clientname);
        if (devices == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(devices, HttpStatus.OK);
    }

    @GetMapping("/getBatterylowDevices")
    @PreAuthorize("hasAuthority('AC_ACTIVE')")
    public ResponseEntity<List<DeviceStatus>> getBatterylowDevices(@RequestParam String clientname) {
        List<DeviceStatus> devices = summarySheetRep.getBatterylowDevices(clientname);
        if (devices == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(devices, HttpStatus.OK);
    }

    @GetMapping("/getAllBatterylowDevices")
    @PreAuthorize("hasAuthority('AC_ACTIVE')")
    public ResponseEntity<List<DeviceStatus>> getAllBatterylowDevices(@RequestParam String clientname) {
        List<DeviceStatus> devices = summarySheetRep.getBatterylowDevices(clientname);
        if (devices == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(devices, HttpStatus.OK);
    }





}
