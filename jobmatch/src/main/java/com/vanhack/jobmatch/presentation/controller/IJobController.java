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
import com.vanhack.jobmatch.infra.rotines.Base64;
import com.vanhack.jobmatch.infra.rotines.SendMail;
import com.vanhack.jobmatch.infra.rotines.StringUtils;
import com.vanhack.jobmatch.presentation.vo.JobVO;
import com.vanhack.jobmatch.presentation.vo.MatchVO;
import com.vanhack.jobmatch.presentation.vo.ProfessionalVO;
import com.vanhack.jobmatch.presentation.vo.ServiceReturnVO;
import com.vanhack.jobmatch.presentation.vo.SkillVO;
import com.vanhack.jobmatch.service.JobMatchService;

@Named("jobController")
@SessionScoped
public class IJobController extends VanhackController implements Serializable {

	private static final long serialVersionUID = 1L;
	private ServiceReturnVO info;

	private JobVO job;
	private Collection<JobVO> listJobs;
	
	private JobVO jobSelected;
	private HashMap<String, Boolean> hashAnalysis;
	private HashMap<String, Boolean> hashAnalysisCV;
	private HashMap<String, String> hashSimilarityProfessional;
	
	private MatchVO matchSelected;
	private String emailCompany;
	
	
	public String getEmailCompany() {
		return emailCompany;
	}

	public void setEmailCompany(String emailCompany) {
		this.emailCompany = emailCompany;
	}

	public HashMap<String, String> getHashSimilarityProfessional() {
		return hashSimilarityProfessional;
	}

	public void setHashSimilarityProfessional(HashMap<String, String> hashSimilarityProfessional) {
		this.hashSimilarityProfessional = hashSimilarityProfessional;
	}

	public HashMap<String, Boolean> getHashAnalysisCV() {
		return hashAnalysisCV;
	}

	public void setHashAnalysisCV(HashMap<String, Boolean> hashAnalysisCV) {
		this.hashAnalysisCV = hashAnalysisCV;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public HashMap<String, Boolean> getHashAnalysis() {
		return hashAnalysis;
	}

	public void setHashAnalysis(HashMap<String, Boolean> hashAnalysis) {
		this.hashAnalysis = hashAnalysis;
	}

	public ServiceReturnVO getInfo() {
		return info;
	}

	public void setInfo(ServiceReturnVO info) {
		this.info = info;
	}
	

	public JobVO getJob() {
		return job;
	}

	public void setJob(JobVO job) {
		this.job = job;
	}

	public Collection<JobVO> getListJobs() {
		return listJobs;
	}

	public void setListJobs(Collection<JobVO> listJobs) {
		this.listJobs = listJobs;
	}

	public JobVO getJobSelected() {
		return jobSelected;
	}

	public void setJobSelected(JobVO jobSelected) {
		this.jobSelected = jobSelected;
	}

	
	public MatchVO getMatchSelected() {
		return matchSelected;
	}

	public void setMatchSelected(MatchVO matchSelected) {
		this.matchSelected = matchSelected;
	}

	public void iniciar(ComponentSystemEvent event) throws Exception {
		if (!super.isPostBack()) {
			this.matchSelected = new MatchVO();
			this.hashAnalysis = new HashMap<>();
			this.job = new JobVO();
			this.info = new ServiceReturnVO();
			this.listJobs = new ArrayList<>();
			this.jobSelected = new JobVO();
		}
	}
	
	public void limpar() {
		this.matchSelected = new MatchVO();
		this.hashAnalysis = new HashMap<>();
		this.job = new JobVO();
		this.info = new ServiceReturnVO();
		this.listJobs = new ArrayList<>();
		this.jobSelected = new JobVO();
	}
	
	
	public void voltar() {
		super.redirect("../login/main.jsf");
	}
	
	public void consultar() {
		
		try {
			if (StringUtils.isEmpty(this.job.getDescription())) {
				super.insertWarningMessage("Put some description to search...");
				return;
			}
			this.listJobs = new JobMatchService().searchJobs(this.job);
			
			if (this.listJobs.size() ==0 ) {
				super.insertWarningMessage("No data found!");
			} else {
				for (JobVO p: this.listJobs) {
					this.hashAnalysis.put(p.getCode(), false);
				}
			}
			
			
		} catch (Exception ex) {
			super.redirectExceptionPage(ex);
		}
	}
	
	public void detectCandidates() {
		Collection<String> codigos = new ArrayList<>();
		
		for (String key: this.hashAnalysis.keySet()) {
			if (this.hashAnalysis.get(key)) {
				codigos.add(key);
			}
		}
		
		if (codigos.size() == 0) {
			super.insertWarningMessage("Select more than 1 Job to execute this action...");
			return;
		}
		try {
			String detail = codigos.toString().replace("[","").replace("]","").replace(" ","");
			new JobMatchService().indexJob(detail);
			
			super.successfulPage("Jobmatch Request executed with success!", Arrays.asList(new PageButton("OK", "../jobmatch/jobmatchFilter.jsf")));
			
		} catch (Exception ex) {
			super.redirectExceptionPage(ex);
		}
	}
	
	public void detail() {
		
		//this.listJobs = new JobMatchService().(this.job);
		try {
			this.hashAnalysisCV = new HashMap<>();
			this.hashSimilarityProfessional = new HashMap<>();
			this.jobSelected = new JobMatchService().detailJob(this.jobSelected);
			
			for (MatchVO m: this.jobSelected.getListMatchs()) {
				this.hashAnalysisCV.put(m.getProfessional().getCode(),false);
			}
			super.redirect("jobmatchDetail.jsf");
		} catch (Exception ex) {
			super.redirectExceptionPage(ex);
		}
	}

	public void detailProfessional() {
		
		this.hashSimilarityProfessional = new HashMap<>();
		for (SkillVO v: this.matchSelected.getProfessional().getListSkills()) {
			hashSimilarityProfessional.put(v.getCode(), v.getLevel());
		}
	}
	
	public void loadCVs() {
		Collection<String> codigos = new ArrayList<>();
		emailCompany = "";
		for (String key: this.hashAnalysisCV.keySet()) {
			if (this.hashAnalysisCV.get(key)) {
				codigos.add(key);
			}
		}
		
		if (codigos.size() == 0) {
			super.insertWarningMessage("Select more than 1 Professional to execute this action...");
			return;
		}
		
		try {
			String detail = codigos.toString().replace("[","").replace("]","").replace(" ","");
			this.info = new JobMatchService().findCVs(detail);
			
			if (!info.getCode().equals("1")) {
				throw new Exception (info.getDescription());
			}
		} catch (Exception ex) {
			super.redirectExceptionPage(ex);
		}
		
	}

	public void sendCVsToCompany() {
		
		try {
		if (StringUtils.isEmpty(emailCompany)) {
			super.insertWarningMessage("Please put the e-mail to execute this option");
			return;
		}
		
			String message = "<html><body><br/><img src=\"http://app.vanhack.com/Content/Img/logo.png\" />"+
			"<br/><br/><p> Hi #company# !</p> <br/><br/><p>This email is related to: #joboffer# </p><br/>"+
			"<br/> Here is the list of the selected candidates: <br/><br/> #candidates# "+		
			"<br/><br/><p> Any questions or problems please send your feedback.</p> <p>Best Regards,</p><p>VanHack team</p><br/>"+
			"</body></html>";
			
			message = message.replace("#joboffer#", this.jobSelected.getJobtype());
			message = message.replace("#company#", this.jobSelected.getCompany());
			
			String candidates = "";
			
			for (MatchVO m: this.jobSelected.getListMatchs()) {
				candidates = candidates + "<p> - Name: <b>"+m.getProfessional().getName() +"</b> - Match for this job: <b>" + m.getSimilarity() + "%</b> CV: " + m.getProfessional().getCode() + ".pdf </p>";
				
			}
			
			HashMap<String, byte[]> list = new HashMap<>();
			for (ProfessionalVO p: this.info.getListProfessionals()) {
				list.put(p.getCode()+".pdf", Base64.decodeWebSafe(p.getCv()));
			}
			message = message.replace("#candidates#", candidates);
			
			String email = this.getEmailCompany();
			new SendMail().from("suporte@virtualsolucoesti.com")
						  .to(email)
						  .subject("Candidates for - " + this.jobSelected.getJobtype())
						  .message(message)
						  .attachments(list)
						  .send();
						
			super.successfulPage("E-mail sended to company with success!", Arrays.asList(new PageButton("OK", "../jobmatch/jobmatchFilter.jsf")));
			
		} catch (Exception ex) {
			super.redirectExceptionPage(ex);
		}
	}
	
}
