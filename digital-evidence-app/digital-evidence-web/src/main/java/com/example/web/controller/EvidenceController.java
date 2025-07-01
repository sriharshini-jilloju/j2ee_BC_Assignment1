package com.example.web.controller;

import javax.validation.Valid;

import org.digital.evidence.ejb.entity.Evidence;
import org.digital.evidence.ejb.service.EvidenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/evidence")
public class EvidenceController {

	@Autowired
	EvidenceService evidenceService;

	@GetMapping("/create-evidence")
	public String showCreateForm(Model model) {
		model.addAttribute("evidence", new Evidence());
		return "create-evidence"; 
	}

	@PostMapping("/save")
	public String saveEvidence(@Valid @ModelAttribute Evidence evidence, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "create-evidence"; 
		}
		evidenceService.saveEvidence(evidence);
		return "redirect:/evidence/all";
	}

	@GetMapping("/all")
	public String getAll(Model model) {
		model.addAttribute("evidences", evidenceService.getAllEvidence());
		model.addAttribute("total", evidenceService.getAllEvidence().size());
		return "all";
	}

	@GetMapping("/incustody")
	public String getInCustody(Model model) {
		model.addAttribute("evidences", evidenceService.filterEvidenceByCustodyStatus("IN_CUSTODY"));
		model.addAttribute("total", evidenceService.filterEvidenceByCustodyStatus("IN_CUSTODY").size());
		return "incustody";
	}

	@GetMapping("/released")
	public String getReleased(Model model) {
		model.addAttribute("evidences", evidenceService.filterEvidenceByCustodyStatus("RELEASED"));
		model.addAttribute("total", evidenceService.filterEvidenceByCustodyStatus("RELEASED").size());
		return "released";
	}
}