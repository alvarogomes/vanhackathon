package com.vanhack.jobmatch.dao;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import javax.persistence.Query;

import org.jsoup.Jsoup;

import com.aspose.words.BorderCollection;
import com.aspose.words.Cell;
import com.aspose.words.CellVerticalAlignment;
import com.aspose.words.ControlChar;
import com.aspose.words.Document;
import com.aspose.words.License;
import com.aspose.words.LineStyle;
import com.aspose.words.Paragraph;
import com.aspose.words.ParagraphAlignment;
import com.aspose.words.Row;
import com.aspose.words.Run;
import com.aspose.words.SaveFormat;
import com.aspose.words.Table;
import com.vanhack.jobmatch.controller.vo.EducationVO;
import com.vanhack.jobmatch.controller.vo.ExperienceVO;
import com.vanhack.jobmatch.controller.vo.ProfessionalVO;
import com.vanhack.jobmatch.controller.vo.ServiceReturnVO;
import com.vanhack.jobmatch.controller.vo.SkillVO;
import com.vanhack.jobmatch.persistence.Authentication;
import com.vanhack.jobmatch.persistence.Cvmodel;
import com.vanhack.jobmatch.persistence.Education;
import com.vanhack.jobmatch.persistence.Job;
import com.vanhack.jobmatch.persistence.Jobexperience;
import com.vanhack.jobmatch.persistence.User;
import com.vanhack.jobmatch.persistence.Userprocess;
import com.vanhack.jobmatch.persistence.Userskill;
import com.vanhack.jobmatch.persistence.Userskillcalculated;
import com.vanhack.jobmatch.useful.Base64;
import com.vanhack.jobmatch.useful.CommonDAO;
import com.vanhack.jobmatch.useful.DateFormatter;
import com.vanhack.jobmatch.useful.EntityManagerHelper;
import com.vanhack.jobmatch.useful.ReplaceTable;


public class ProfessionalDao extends CommonDAO<User> {

	public ProfessionalDao() {
		this.set(User.class);
	}
	private String generateMD5(String senha) {
		String s = "";
		try {
			MessageDigest m=MessageDigest.getInstance("MD5");
			byte messageDigest[] = m.digest(senha.getBytes("UTF-8"));
			StringBuilder hexString = new StringBuilder();
			for (byte b : messageDigest) {
			  hexString.append(String.format("%02X", 0xFF & b));
			}
			s = hexString.toString().toLowerCase();
			
		} catch ( Exception ex) {
		}
		
		return s;
	}
	public ServiceReturnVO login(String email, String password) throws Exception {
		
		ServiceReturnVO s = new ServiceReturnVO("1", "Success");
		
		CommonDAO<Authentication> auth = new CommonDAO<>();
		
		String senha = generateMD5(password);
		int i = 1;
		List<Authentication> list = auth.query("Select p from Authentication p where p.user.email = ? and p.password = ?", email, senha);
		
		if (list.size() == 0 ) {
			s.setCode("2");
			s.setDescription("Login/password invalid!");
		} else {
			s.getListProfessionals().addAll(this.professionalEntityConverterVO(Arrays.asList(list.get(0).getUser())));
		}
		return s;
	}
	
	public ServiceReturnVO detectSkill(String codigos) {
		ServiceReturnVO s = new ServiceReturnVO("1", "Success");
		EntityManagerHelper.beginTransaction();
		
		StringTokenizer st = new StringTokenizer(codigos,",");
		
		CommonDAO<Userprocess> daoUserProcess = new CommonDAO<>();
		daoUserProcess.set(Userprocess.class);
		while ( st.hasMoreElements() ) { 
    		String actualElement = st.nextToken(); 
    		
    		Userprocess up = daoUserProcess.findById(Integer.valueOf(actualElement));
    		
    		if (up == null) {
    			Userprocess x = new Userprocess();
    			
    			User user  = this.findById(Integer.valueOf(actualElement));
    			
    			Query q = this.getEntityManager().createNativeQuery("INSERT INTO Userprocess (iduser, status) VALUES ("+
    					user.getIduser()+ ",1)");
    			q.executeUpdate();
    			
    		}
    		
		}
		EntityManagerHelper.commit();
		
		return s;
	}
	public ServiceReturnVO detail(String code) throws Exception {
		ServiceReturnVO s = new ServiceReturnVO("1", "Success");
		
		User p = super.findById(Integer.valueOf(code));
		
		List<User> list = new ArrayList<>();
		list.add(p);
		
		s.getListProfessionals().addAll(this.professionalEntityConverterVO(list));
		
		return s;
	}
	
	public ServiceReturnVO search(String description) throws Exception  {
		ServiceReturnVO s = new ServiceReturnVO("1", "Success");
		
		super.getEntityManager().clear();
		
		List<User> list = super.query("Select p from User p where p.firstname like '" + description + "%'");
		
		s.getListProfessionals().addAll(this.professionalEntityConverterVO(list));
		
		return s;
		
	}
	
	public ServiceReturnVO searchCVs(String codigos) throws Exception {
		ServiceReturnVO s = new ServiceReturnVO("1", "Success");
		
		StringTokenizer st = new StringTokenizer(codigos,",");
		
		List<User> list = new ArrayList<>();
		while ( st.hasMoreElements() ) { 
    		String actualElement = st.nextToken(); 
    		User up = this.findById(Integer.valueOf(actualElement));
    		list.add(up);
		}
		
		s.getListProfessionals().addAll(this.professionalEntityConverterVO(list, true));
		return s;
	}
	
	public List<ProfessionalVO> professionalEntityConverterVO(List<User> list) throws Exception {
		return professionalEntityConverterVO(list, false);
	}
	
	public List<ProfessionalVO> professionalEntityConverterVO(List<User> list, boolean generateCV) throws Exception  {
		
		List<ProfessionalVO> aux = new ArrayList<>();
		
		HashMap<Integer, String> h = new HashMap<>();
		h.put(null, "Pending");
		h.put(1, "Waiting for process");
		h.put(2, "In process");
		h.put(3, "Done");
		
		for (User p: list) {
			ProfessionalVO pvo = new ProfessionalVO();
			pvo.setCode(String.valueOf( p.getIduser()));
			pvo.setName(p.getFirstname() + " " + p.getLastname());
			pvo.setEmail(p.getEmail());
			pvo.setTitle(p.getPosition());
			if (p.getBio() != null)
				pvo.setDescription(Jsoup.parse(p.getBio()).body().text());
			
			
			HashMap<String, String> skills = new HashMap<>();
			HashMap<String, String> levelSkills = new HashMap<String, String>();
			//Geting self skills...
			for (Userskill ps : p.getUserskills()) {
				skills.put(ps.getSkill().getSkillname().toLowerCase(), String.valueOf(ps.getSkill().getIdskill()));
				levelSkills.put(ps.getSkill().getSkillname().toLowerCase(), String.valueOf(ps.getYearsofexperience()));
			}
			//Getting calculated Skills...
			for (Userskillcalculated uc: p.getUserskillcalculateds()) {
				skills.put(uc.getSkill().getSkillname().toLowerCase(), String.valueOf(uc.getSkill().getIdskill()));
				levelSkills.put(uc.getSkill().getSkillname().toLowerCase(), String.valueOf(uc.getLevel()));
			}
			
			//After mix the skills create a collection...
			List<SkillVO> listSkills = new ArrayList<>();
			for (String key: skills.keySet()) {
				SkillVO svo = new SkillVO();
				
				svo.setCode(skills.get(key));
				svo.setDescription(key);

				svo.setLevel(levelSkills.get(key));
				
				listSkills.add(svo);
			}
			
			//pvo.setListSkills(this.skillEntityConverterVO(p.getUserskills()));
			pvo.setListSkills(listSkills);
			
			pvo.setListProfessionalExperience(experienceEntityConverterVO(p.getJobexperiences()));
			pvo.setListEducation(educationEntityConverterVO(p.getEducations()));
			
			pvo.setStatus(h.get(null));
			
			if (p.getUserprocess() != null) {
				pvo.setStatus(h.get(p.getUserprocess().getStatus()));
			}
			
			if (generateCV) {
				String cvBase64 = generateCV(pvo);
				pvo.setCv(cvBase64);
			}
			
			aux.add(pvo);
		}
		
		return aux;
	}
	
	private void loadLicense(){
		try {
			String licenseFile = "Aspose.Words.lic";
			
			InputStream stream = null;
			License license = new License();
			
			if (stream == null) {
				ClassLoader classLoader = ClassLoader.getSystemClassLoader();
				stream = classLoader.getResourceAsStream(licenseFile);
			}

			if (stream == null) {
				stream = ProfessionalDao.class.getClassLoader()
						.getResourceAsStream(licenseFile);
			}

			if (stream == null) {
				stream = ProfessionalDao.class.getResourceAsStream(licenseFile);
			}
			
			license.setLicense(stream);
			
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
	private Cell createCell(Document doc, String text, Integer w, boolean bold, Integer align) throws Exception {
		Cell cell = new Cell(doc);
		cell.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
		cell.getCellFormat().setWidth(400);
		Paragraph pp = new Paragraph(doc);
		pp.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
		cell.appendChild(/* new Paragraph(doc) */pp);

		if (text == null)
			text = "";
		Run texto = new Run(doc, text);
		
		texto.getFont().setSize(Double.valueOf(12));
		if (bold) texto.getFont().setBold(true);
		
		texto.getFont().setName("Arial");
		BorderCollection borders = cell.getCellFormat().getBorders();
		borders.getLeft().setLineStyle(LineStyle.NONE);
		borders.getRight().setLineStyle(LineStyle.NONE);
		borders.getTop().setLineStyle(LineStyle.NONE);
		borders.getBottom().setLineStyle(LineStyle.NONE);
		
		cell.getFirstParagraph().appendChild(texto);
		
		
		return cell;
	}

	@SuppressWarnings("deprecation")
	private String generateCV(ProfessionalVO vo) throws Exception {
		CommonDAO<Cvmodel> c = new CommonDAO<>();
		c.set(Cvmodel.class);
		
		List<Cvmodel> cv = c.findAll();
		
		loadLicense();
		
		//Creating the CV (Getting the word model and converting to PDF...
		ByteArrayOutputStream out = new ByteArrayOutputStream();
	
		InputStream myInputStream = new ByteArrayInputStream(cv.get(0).getFile());
		Document doc = new Document(myInputStream);
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		
		//Load data...
		parametros.put("#name", vo.getName());
		parametros.put("#title", vo.getTitle());
		parametros.put("#email", vo.getEmail());
		parametros.put("#description", vo.getDescription());
		Collection<String> a = new ArrayList<>();
		for( SkillVO s: vo.getListSkills()) {
			a.add(s.getDescription());
		}
		parametros.put("#skills", a.toString().replace("[", "").replace("]", ""));
		
		Table t = new Table(doc);
		
		for (ExperienceVO e: vo.getListProfessionalExperience()) {
			Row r = new Row(doc);
			t.appendChild(r);
			
			Row r2 = new Row(doc);
			t.appendChild(r2);
			
			Row r3 = new Row(doc);
			t.appendChild(r3);
			
			//Insert First line...
			Cell col = createCell(doc, e.getCompanyName(), 20, true, 1);
			Cell col2 = createCell(doc, e.getDateStart() + " - " + e.getDateEnd(), 20, true, 2);
			r.appendChild(col);
			r.appendChild(col2);
			Cell col3 = createCell(doc, e.getTitle(), 40, false, 1);
			r2.appendChild(col3);
			Cell col4 = createCell(doc, e.getDescription()==null ? "" : e.getDescription(), 40, false, 1);
			r3.appendChild(col4);
		}
		parametros.put("#workexperience", t);
		
		
		Table t2 = new Table(doc);
		for (EducationVO e: vo.getListEducation()) {
			Row r = new Row(doc);
			t2.appendChild(r);
			
			Row r2 = new Row(doc);
			t2.appendChild(r2);
			
			Row r3 = new Row(doc);
			t2.appendChild(r3);
			
			//Insert First line...
			Cell col = createCell(doc, e.getName(), 20, true, 1);
			Cell col2 = createCell(doc, e.getDateStart() + " - " + e.getDateEnd(), 20, true, 1);
			r.appendChild(col);
			r.appendChild(col2);
			Cell col3 = createCell(doc, e.getCourse()==null ? "" : e.getCourse(), 40, false, 1);
			r2.appendChild(col3);
			Cell col4 = createCell(doc, e.getLocation()==null ? "" : e.getLocation(), 40, false, 1);
			r3.appendChild(col4);
			
		}
		parametros.put("#education", t2);
		
		//ReplaceData...
		
		for (String key : parametros.keySet()) {
			Object value = parametros.get(key);
			
			String hashtag = key;
			if (value == null) {
				value = "";
			}
			hashtag = hashtag.toUpperCase();
			
			if (value instanceof Table) {
				doc.getRange().replace(Pattern.compile(hashtag), new ReplaceTable(doc, (Table) value),
						false);
				doc.getRange().replace(Pattern.compile(hashtag.toLowerCase()),
						new ReplaceTable(doc, (Table) value), false);
			} else {
				String valorEmString = ((String) value).replace("\n", ControlChar.LINE_BREAK);
				doc.getRange().replace(hashtag, valorEmString, false, false);
				doc.getRange().replace(hashtag.toLowerCase(), valorEmString, false, false);
			}
		}
		doc.save(out,SaveFormat.PDF);
		
		return Base64.encodeWebSafe(out.toByteArray(), false);
	}
	
	private List<EducationVO> educationEntityConverterVO(List<Education> je) {
		List<EducationVO> result = new ArrayList<>();
		
		for(Education j: je) {
			EducationVO e = new EducationVO();
			
			e.setIdEducation(j.getIdeducation().toString());
			e.setCourse(j.getCourse());
			e.setLocation(j.getLocation());
			e.setCountry(j.getCountry());
			e.setDateStart(DateFormatter.format(j.getStart()));
			e.setDateEnd(DateFormatter.format(j.getEnd()));
			
			result.add(e);
		}
		
		return result;
	}
	private List<ExperienceVO> experienceEntityConverterVO(List<Jobexperience> je) {
		
		List<ExperienceVO> result = new ArrayList<>();
		
		for(Jobexperience j: je) {
			ExperienceVO aux = new ExperienceVO();
			
			aux.setIdExperience(j.getIdjobexperience().toString());
			aux.setCompanyName(j.getCompanyname());
			aux.setTitle(j.getTitle());
			aux.setLocation(j.getLocation());
			aux.setDateStart(DateFormatter.format(j.getStart()));
			aux.setDateEnd(DateFormatter.format(j.getEnd()));
			result.add(aux);
		}
		
		return result;
	}
	
	private List<SkillVO> skillEntityConverterVO(List<Userskill> list) {
		
		List<SkillVO> aux = new ArrayList<>();
		
		for (Userskill ps : list) {
			SkillVO svo = new SkillVO();
			svo.setCode(String.valueOf(ps.getSkill().getIdskill()));
			svo.setDescription(ps.getSkill().getSkillname());
			
			svo.setLevel(String.valueOf(ps.getYearsofexperience()));
			
			aux.add(svo);
		}
		return aux;
	}
	
}
