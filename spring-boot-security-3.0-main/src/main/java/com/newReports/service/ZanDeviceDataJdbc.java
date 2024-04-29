package com.newReports.service;

import java.util.List;


import com.newReports.entity.ZanDeviceData;
import com.newReports.repository.ZanDeviceDataRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class ZanDeviceDataJdbc implements ZanDeviceDataRep {

	@Autowired
	private JdbcTemplate jdbcTemplate;


	   	
	   	public List<ZanDeviceData> getZanDeviceDataTopic(String deviceMacId, String yearmonth) {
	   		
		    try {
		    	return jdbcTemplate.query(
		                "select topic from zanDeviceData.sensorData_"+yearmonth+" where deviceMacId = '"+deviceMacId+"' group by topic;",
		                new BeanPropertyRowMapper<>(ZanDeviceData.class));
		        
		    } catch (Exception e) {
		        return null;
		    }
		}

	   	
 	public List<ZanDeviceData> getZanDeviceData(String deviceMacId,String yearmonth,String topic) {
	   		
		    try {
		    	return jdbcTemplate.query(
		                "select * from zanDeviceData.sensorData_"+yearmonth+" where deviceMacId = '"+deviceMacId+"' and topic = '"+topic+"' order by id desc limit 100;",
		                new BeanPropertyRowMapper<>(ZanDeviceData.class));
		        
		    } catch (Exception e) {
		        return null;
		    }
		}
	   	

}
