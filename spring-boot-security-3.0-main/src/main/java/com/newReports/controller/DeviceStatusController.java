package com.newReports.controller;



import com.newReports.ExcelExport.SummaryReportExporter;
import com.newReports.entity.*;
import com.newReports.repository.DeviceStatusRep;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/Newreports")
public class DeviceStatusController {

    @Autowired
    private DeviceStatusRep deviceStatusRep;

   /* @GetMapping("all")
    public List<DeviceStatus> getAllDeviceStatus() {
        return deviceStatusRep.findAll();
    }


    @GetMapping("allRep")
    public List<DeviceStatus> getAllDevices() {
        return deviceStatusRep.findAllDeviceName();
    }
*/
    @PreAuthorize("hasAuthority('AC_ACTIVE')")
    @GetMapping("/Clients")
    public ResponseEntity<List<ClientModel>> viewDevices() {
        try {

            System.out.println("output: 1 ");
            List<ClientModel> clientNames = deviceStatusRep.getClients();
            System.out.println("output: 2" + clientNames.get(0).toString());
            return new ResponseEntity<>(clientNames, HttpStatus.OK);
        } catch (Exception e) {

            // handle exception
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/getClientData")
    @PreAuthorize("hasAuthority('AC_ACTIVE')")
    public ResponseEntity<List<DeviceStatus>> getCLientData(@RequestParam String clientName, @RequestParam String  startDate, @RequestParam String endDate) {
        try {
            List<DeviceStatus> clientname = deviceStatusRep.getDevices(clientName.toLowerCase(), startDate, endDate);

            return new ResponseEntity<List<DeviceStatus>>(clientname, HttpStatus.OK);
        } catch (Exception e) {
            // handle exception
            return null;
        }
    }


    @GetMapping("/ClientsList")
    @PreAuthorize("hasAuthority('AC_ACTIVE')")
    public ResponseEntity<List<AddClient>> viewClientsList() {
        try {
            List<AddClient> clients = deviceStatusRep.getClientsList();
            return new ResponseEntity<List<AddClient>>(clients, HttpStatus.OK);
        } catch (Exception e) {
            // handle exception
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getPacketlossReprt")
    @PreAuthorize("hasAuthority('AC_ACTIVE')")
    public ResponseEntity<List<PacketlossReport>> getPacketlossReprt(@RequestParam String clientName,
                                                                     @RequestParam String startDate, @RequestParam String endDate) {
        List<PacketlossReport> clientname = deviceStatusRep.getPacketloss(clientName.toLowerCase(), startDate, endDate);

        return new ResponseEntity<List<PacketlossReport>>(clientname, HttpStatus.OK);
    }

    @GetMapping("/getSummary")
    @PreAuthorize("hasAuthority('AC_ACTIVE')")
    public ResponseEntity<List<List<SummaryReport>>> getSummary(String clientName) {
        List<List<SummaryReport>> list = new ArrayList<>();
        List<AddClient> clientlist = deviceStatusRep.getClientsList();
        for (AddClient addClient : clientlist) {
            list.add(deviceStatusRep.getDevicestotaList(addClient.getClientName().toLowerCase()));
        }
        return new ResponseEntity<List<List<SummaryReport>>>(list, HttpStatus.OK);
    }

    @GetMapping("/exportSummary")
    @PreAuthorize("hasAuthority('AC_ACTIVE')")
    public void exportSummary(HttpServletResponse response, String clientName) throws IOException, IOException {
        // Get summary data
        List<List<SummaryReport>> summaryData = new ArrayList<>();
        List<AddClient> clientList = deviceStatusRep.getClientsList();
        for (AddClient addClient : clientList) {
            summaryData.add(deviceStatusRep.getDevicestotaList(addClient.getClientName().toLowerCase()));
        }

        // Create workbook
        Workbook workbook = SummaryReportExporter.exportSummary(summaryData);

        // Set response headers
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new java.util.Date());
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=summary_report_" + currentDateTime + ".xlsx");

        // Write workbook to response stream
        workbook.write(response.getOutputStream());

        // Close workbook
        workbook.close();
    }


    @GetMapping("/getMultipleStopped")
    @PreAuthorize("hasAuthority('AC_ACTIVE')")
    public ResponseEntity<List<List<DeviceStatus>>> getMultipleStopped(String clientName,String startDate,String endDate) {
        List<List<DeviceStatus>> list = new ArrayList<>();
        List<AddClient> clientlist = deviceStatusRep.getClientsList();
        for (AddClient addClient : clientlist) {
            list.add(deviceStatusRep.getDevicesRawdata(addClient.getClientName().toLowerCase(), startDate, endDate));
        }
        return new ResponseEntity<List<List<DeviceStatus>>>(list, HttpStatus.OK);
    }

    @GetMapping("/getAllClientDevicesList")
    @PreAuthorize("hasAuthority('AC_ACTIVE')")
    public ResponseEntity<List<List<DeviceStatus>>> getAllClientDevicesList(String clientName) {
        List<List<DeviceStatus>> list = new ArrayList<>();
        List<AddClient> clientlist = deviceStatusRep.getClientsList();
        for (AddClient addClient : clientlist) {
            List<DeviceStatus> clientDevicesList = deviceStatusRep.getAllClientDevicesList(addClient.getClientName().toLowerCase());
            if (!clientDevicesList.isEmpty()) {
                list.add(clientDevicesList);
            }
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }



}
