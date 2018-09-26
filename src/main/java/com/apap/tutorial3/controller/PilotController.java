package com.apap.tutorial3.controller;

import java.util.List;
import java.util.Optional;

import com.apap.tutorial3.model.PilotModel;
import com.apap.tutorial3.service.PilotService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PilotController {
	@Autowired
	private PilotService pilotService;
	
	@RequestMapping("/pilot/add")
	public String add(@RequestParam(value="id",required=true) String id,
			@RequestParam(value="licenseNumber",required=true) String licenseNumber,
			@RequestParam(value="name",required=true) String name,
			@RequestParam(value="flyHour",required=true) int flyHour) {
		
		PilotModel pilot = new PilotModel (id,licenseNumber,name,flyHour);
		pilotService.addPilot(pilot);
		return "add";
	}
	
	@RequestMapping("/pilot/view")
	public String view(@RequestParam("licenseNumber") String licenseNumber, Model model) {
		PilotModel archive = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		
		model.addAttribute("pilot",archive);
		return "view-pilot";
	}
	
	@RequestMapping("/pilot/viewall")
	public String viewall (Model model) {
		List<PilotModel> archive= pilotService.getPilotList();
		
		model.addAttribute("listPilot",archive);
		return "viewall-pilot";
	}
	
	@RequestMapping(value= {"/pilot/view/license-number","/pilot/view/license-number/{licenseNumber}"})
	public String pilotPath(@PathVariable Optional<String> licenseNumber, Model model) {
		if(licenseNumber.isPresent()) {
						
			PilotModel archive = pilotService.getPilotDetailByLicenseNumber(licenseNumber.get());
			if(archive != null) {
				
				model.addAttribute("pilot",archive);
				return "view-pilot";
			}
		}
		
		return "error-license-number";
		
	}
	
	@RequestMapping(value= {"/pilot/update/license-number/fly-hour/{flyHour}","/pilot/update/license-number/{licenseNumber}/fly-hour/{flyHour}"})
	public String pilotUpdate(@PathVariable Optional<String> licenseNumber,@PathVariable Integer flyHour, Model model) {
		if(licenseNumber.isPresent()) {
						
			PilotModel archive = pilotService.getPilotDetailByLicenseNumber(licenseNumber.get());
			if(archive != null) {
				
				archive.setFlyHour(flyHour);
				
				model.addAttribute("pilot",archive);
				return "view-update";
			}
		}
		
		return "error-update";
		
	}
	
	@RequestMapping(value= {"/pilot/delete/id","/pilot/delete/id/{id}"})
	public String pilotDelete(@PathVariable Optional<String> id, Model model) {
		if(id.isPresent()) {
			for(int i=0;i<pilotService.getPilotList().size();i++) {
				if(pilotService.getPilotList().get(i).getId().equals(id.get())) {
					pilotService.getPilotList().remove(i);
					return "view-delete";
				}
				
			}
			
			return "error-delete";
			
					
			
		}
		
		return "error-delete";
		
	}
	
	
	
	
		


	
	
	
	
}
