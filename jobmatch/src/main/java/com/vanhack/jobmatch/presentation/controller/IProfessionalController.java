package com.vanhack.jobmatch.presentation.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

import javax.enterprise.context.SessionScoped;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Named;


import com.vanhack.jobmatch.infra.controller.VanhackController;
import com.vanhack.jobmatch.infra.model.PageButton;
import com.vanhack.jobmatch.infra.rotines.StringUtils;
import com.vanhack.jobmatch.presentation.vo.ProfessionalVO;
import com.vanhack.jobmatch.presentation.vo.ServiceReturnVO;
import com.vanhack.jobmatch.service.JobMatchService;

@Named("professionalController")
@SessionScoped
public class IProfessionalController extends VanhackController implements Serializable {

	private static final long serialVersionUID = 1L;
	private ServiceReturnVO info;

	private ProfessionalVO professional;
	private Collection<ProfessionalVO> listProfessionals;
	
	private ProfessionalVO professionalSelected;
	private HashMap<String, Boolean> hashAnalysis;
	
	public ProfessionalVO getProfessionalSelected() {
		return professionalSelected;
	}

	public void setProfessionalSelected(ProfessionalVO professionalSelected) {
		this.professionalSelected = professionalSelected;
	}

	public ServiceReturnVO getInfo() {
		return info;
	}

	public void setInfo(ServiceReturnVO info) {
		this.info = info;
	}
	
	
	public ProfessionalVO getProfessional() {
		return professional;
	}

	public void setProfessional(ProfessionalVO professional) {
		this.professional = professional;
	}
	

	public Collection<ProfessionalVO> getListProfessionals() {
		return listProfessionals;
	}

	public void setListProfessionals(Collection<ProfessionalVO> listProfessionals) {
		this.listProfessionals = listProfessionals;
	}

	
	public HashMap<String, Boolean> getHashAnalysis() {
		return hashAnalysis;
	}

	public void setHashAnalysis(HashMap<String, Boolean> hashAnalysis) {
		this.hashAnalysis = hashAnalysis;
	}

	public void iniciar(ComponentSystemEvent event) throws Exception {
		if (!super.isPostBack()) {
			this.hashAnalysis = new HashMap<>();
			this.professional = new ProfessionalVO();
			this.info = new ServiceReturnVO();
			this.listProfessionals = new ArrayList<>();
			this.professionalSelected = new ProfessionalVO();
		}
	}
	
	public void limpar() {
		this.hashAnalysis = new HashMap<>();
		this.professional = new ProfessionalVO();
		this.info = new ServiceReturnVO();
		this.listProfessionals = new ArrayList<>();
		this.professionalSelected = new ProfessionalVO();
	}
	
	
	public void voltar() {
		super.redirect("../login/main.jsf");
	}
	
	public void consultar() {
		
		try {
			if (StringUtils.isEmpty(this.professional.getName())) {
				super.insertWarningMessage("Put some name to search...");
				return;
			}
			this.listProfessionals = new JobMatchService().searchProfessionals(this.professional);
			
			if (this.listProfessionals.size() ==0 ) {
				super.insertWarningMessage("No data found!");
			} else {
				for (ProfessionalVO p: this.listProfessionals) {
					this.hashAnalysis.put(p.getCode(), false);
				}
			}
			
		} catch (Exception ex) {
			super.redirectExceptionPage(ex);
		}
	}
	
	public void executarDetecaoDeSkills() {
		
		Collection<String> codigos = new ArrayList<>();
		
		for (String key: this.hashAnalysis.keySet()) {
			if (this.hashAnalysis.get(key)) {
				codigos.add(key);
			}
		}
		
		if (codigos.size() == 0) {
			super.insertWarningMessage("Select more than 1 Professional to execute this action...");
			return;
		}
		try {
			String detail = codigos.toString().replace("[","").replace("]","").replace(" ","");
			new JobMatchService().detectSkills(detail);
			
			super.successfulPage("Skills detection Request executed with success!", Arrays.asList(new PageButton("OK", "../professionals/professionalsFilter.jsf")));
			
		} catch (Exception ex) {
			super.redirectExceptionPage(ex);
		}
		
		
	}
	

	public void selectAll() {
		Collection<String> items = new ArrayList<>();
		
		for (String key: this.hashAnalysis.keySet()) {
			items.add(key);
		}
		
		for (String key: items) {
			this.hashAnalysis.put(key, true);
		}
		
	}
	

	public void unselectAll() {
		Collection<String> items = new ArrayList<>();
		
		for (String key: this.hashAnalysis.keySet()) {
			items.add(key);
		}
		
		for (String key: items) {
			this.hashAnalysis.put(key, false);
		}
		
	}
	
}
