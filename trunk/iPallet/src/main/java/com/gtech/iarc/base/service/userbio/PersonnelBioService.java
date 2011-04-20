package com.gtech.iarc.base.service.userbio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.gtech.iarc.base.model.personalinfo.Personnel;
import com.gtech.iarc.base.model.personalinfo.PersonnelSearchDTO;
import com.gtech.iarc.base.persistence.BaseDAO;
import com.gtech.iarc.base.persistence.BaseRepository;
@SuppressWarnings("unchecked")
public class PersonnelBioService {
	
	private BaseRepository baseRepository;
	
	public void setBaseRepository(BaseRepository baseRepository) {
		this.baseRepository = baseRepository;
	}

	@SuppressWarnings("unchecked")
	public List<Personnel> searchByName(String nameLike) {
		if(nameLike!=null && nameLike.trim().length()>0){
			return baseRepository.find("from Personnel pl where pl.firstName like ? or pl.lastName like ? or pl.middleName like ?", 
					new String[]{"%"+nameLike+"%","%"+nameLike+"%","%"+nameLike+"%"});
		}else{
			return baseRepository.find("from Personnel pl ");
		}
	}
	
	public void addPersonnel(Personnel p) {
		baseRepository.save(p);
	}
	

	public void addAllPersonnel(List<Personnel> ps) {
		for (Personnel p : ps) {
			addPersonnel(p);
		}
	}
	
	public void deleteAllPersonnel(List<Personnel> ps) {
		for (Personnel p : ps) {
			deletePersonnel(p.getId());
		}
	}
	
	public void deletePersonnel(long id){
		baseRepository.delete(Personnel.class, id);
	}
	
	public void updateAllPersonnel(List<Personnel> ps) {
		for (Personnel p : ps) {
			updatePersonnel(p);
		}
	}	
	
	public void updatePersonnel(Personnel newP){
		baseRepository.save(newP);
	}
//	private void setupPersonnelList() {
//		List<Personnel> personnels = new ArrayList<Personnel>();
//				
//		try {
//			personnels.add(createPersonnel("Jeannette","Zullo","9269","Saddle Lane","Ottine","TX","78658","576-268-5018","576-746-0615","576-480-1855","411-56-9977","1941-09-12","1992-08-25","jzullo@onjuneu.com", Department.FINANCE));
//			personnels.add(createPersonnel("Juan","Kunkel","2287","Normanwood Drive","Camden","MI","49232","744-245-5892","744-488-7913","744-402-1944","135-30-5321","1942-05-27","1998-12-25","jkunkel@arecur.com", Department.FINANCE));
//			personnels.add(createPersonnel("Angel","Gallant","2586","Ogden","South Holland","IL","60473","790-511-2357","790-815-5242","790-629-4150","374-29-8198","1960-12-03","2002-04-08","agallant@ae.net", Department.FINANCE));
//			personnels.add(createPersonnel("Paige","Loving","1676","Wilson","Mc Henry","KY","42354","706-373-4145","706-696-1554","706-282-9105","401-67-6381","1927-09-23","2004-01-22","ploving@becurbriare.com", Department.FINANCE));
//			personnels.add(createPersonnel("Nathaniel","Blanchette","9683","Compton","Washington Mills","NY","13479","481-237-9492","481-664-0613","481-274-4134","460-18-9877","1936-02-19","1990-07-12","nblanchette@kimcurkonsa.biz", Department.FINANCE));
//			personnels.add(createPersonnel("Davin","Hudgens","2388","Lafayette","Heisson","WA","98622","334-705-1750","334-567-3590","334-883-2214","193-45-6256","1980-02-23","1994-11-10","dhudgens@poponarelac.com", Department.FINANCE));
//			personnels.add(createPersonnel("Tai","Canton","9291","Millbrook","Perry","FL","32347","516-557-6589","516-888-7572","516-272-3171","878-09-0988","1966-11-09","1999-01-24","tcanton@euin.biz", Department.FINANCE));
//			personnels.add(createPersonnel("Marlin","Kowalski","2768","Oberlin Circle","Falls Village","CT","06031","694-684-4015","694-595-5490","694-271-9957","288-98-2021","1969-09-28","1995-11-14","mkowalski@criing.com", Department.FINANCE));
//			personnels.add(createPersonnel("Damian","Lightfoot","5912","Brewster","Glen Gardner","NJ","08826","621-503-8999","621-821-2729","621-885-6428","575-25-1867","1938-09-01","2001-09-21","dlightfoot@arecuroher.com", Department.FINANCE));
//			personnels.add(createPersonnel("Rosemary","Carbonell","3395","Osmun","White Plains","NY","10607","799-875-8276","799-892-7179","799-302-5745","409-58-6425","1946-04-18","1990-02-08","rcarbonell@konatesu.gov", Department.FINANCE));
//			personnels.add(createPersonnel("Mario","Sales","545","Bywood Way","Washington","DC","20506","221-898-0323","221-278-6099","221-254-4514","403-16-9090","1985-01-17","2006-01-04","msales@fearnfocu.com", Department.HR));
//			personnels.add(createPersonnel("Estevan","Moree","3888","Main","Huntington","WV","25718","252-293-2690","252-645-1664","252-437-3975","788-19-6542","1979-01-17","1998-01-18","emoree@herkytecu.edu", Department.HR));
//			personnels.add(createPersonnel("Renee","Bounds","4292","Angier","Stephens","GA","30667","568-924-8518","568-635-7406","568-712-5979","125-22-5879","1950-12-15","2000-02-15","rbounds@geeulacor.gov", Department.HR));
//			personnels.add(createPersonnel("Asia","Quarterman","5836","Moreland","Francis","OK","74844","761-742-1403","761-773-6008","761-395-2831","137-00-2335","1961-02-07","2002-09-14","aquarterman@ikearnpo.com", Department.HR));
//			personnels.add(createPersonnel("Earnest","Guinn","5122","Sunderland","Tacoma","WA","98450","323-613-0174","323-612-7259","323-794-2206","759-43-5361","1962-01-20","2004-10-27","eguinn@re.org", Department.HR));
//			personnels.add(createPersonnel("Percy","Peterman","5685","Brafferton Square","Port Ewen","NY","12466","447-631-0053","447-831-9095","447-759-4071","993-45-9712","1947-04-01","1995-10-14","ppeterman@curjablin.org", Department.HR));
//			personnels.add(createPersonnel("Alesha","Rendon","9137","Hillcrest","Red Oak","NC","27868","882-303-9692","882-697-9708","882-559-0183","807-77-8652","1948-02-21","1994-10-21","arendon@dredrcur.com", Department.HR));
//			personnels.add(createPersonnel("Rickey","Fossum","3643","Elwood Gardens","Rio Medina","TX","78066","418-445-6991","418-592-2715","418-661-1698","656-32-4249","1946-07-07","2004-04-11","rfossum@re.net", Department.HR));
//			personnels.add(createPersonnel("Monty","Ivy","1220","Hamlet Way","Tampa","FL","33689","920-228-3276","920-398-7315","920-558-2520","246-05-7341","1969-01-05","1992-10-24","mivy@kyficu.net", Department.HR));
//			personnels.add(createPersonnel("Abbie","Apple","5299","Beaubien","Gracemont","OK","73042","266-269-3700","266-947-8439","266-306-6804","853-86-3037","1937-09-07","1994-08-14","aapple@ing.com", Department.HR));
//			personnels.add(createPersonnel("Damion","Millwood","864","Clark Gate","Kingston","NY","12402","221-572-9748","221-630-5701","221-812-9164","247-45-9845","1950-09-17","1991-10-26","dmillwood@gearn.biz", Department.IT));
//			personnels.add(createPersonnel("Jarvis","Kinnison","4850","Faircourt Mews","Pattonsburg","MO","64670","883-953-6160","883-821-9854","883-901-2846","509-65-8131","1947-04-02","1994-12-23","jkinnison@blabarmxe.com", Department.IT));
//			personnels.add(createPersonnel("Terrance","Verdin","5645","Moon","Mitchellville","IA","50169","460-899-5782","460-713-2032","460-671-5687","469-36-0705","1950-02-18","2003-10-15","tverdin@iketebesa.com", Department.IT));
//			personnels.add(createPersonnel("Victor","Rhames","6987","Pontiac Loop","Moro","OR","97039","871-723-8866","871-675-7582","871-623-6460","789-31-5484","1947-01-24","2003-09-03","vrhames@sicrosu.com", Department.IT));
//			personnels.add(createPersonnel("Edward","Schnieders","6753","Nebraska Avenue","Cotopaxi","CO","81223","807-433-8050","807-314-6134","807-529-6298","881-28-9804","1963-01-27","1991-03-26","eschnieders@xesire.com", Department.IT));
//			personnels.add(createPersonnel("Gina","Declue","4435","Andover Alley","Bostwick","GA","30623","606-308-7238","606-613-9876","606-769-3837","980-14-7701","1985-01-15","2001-08-13","gdeclue@ky.org", Department.IT));
//			personnels.add(createPersonnel("Melissa","Ginn","1852","Person Cove","Denver","CO","80291","290-517-1020","290-878-9949","290-362-9539","660-17-6927","1950-12-15","2002-01-21","mginn@su.com", Department.IT));
//			personnels.add(createPersonnel("Sara","Harwell","42","Sumter","Heathsville","VA","22473","647-508-8931","647-413-3470","647-368-9327","922-01-6683","1956-05-10","1991-07-25","sharwell@arm.biz", Department.IT));
//			personnels.add(createPersonnel("Tony","Orth","7562","Stamford","Pyote","TX","79777","876-396-3158","876-549-7474","876-653-6806","124-51-5828","1973-08-16","1994-12-03","torth@ponfo.com", Department.IT));
//			personnels.add(createPersonnel("Richard","Swearengin","7536","Kaline","Fort Leonard Wood","MO","65473","710-825-1270","710-460-5982","710-876-0085","801-69-5383","1962-12-11","2002-03-05","rswearengin@on.gov", Department.IT));
//			personnels.add(createPersonnel("Joanne","Breland","7699","Person Place","Plymouth","NC","27962","927-332-6196","927-877-5735","927-649-8214","190-91-9400","1930-04-02","1998-05-26","jbreland@ike.com", Department.SALES));
//			personnels.add(createPersonnel("Geneva","Palmeri","8651","Seneca Road","Sherrills Ford","NC","28673","625-742-1272","625-873-0996","625-533-9815","922-70-6962","1955-03-08","2001-02-10","gpalmeri@dreinateky.org", Department.SALES));
//			personnels.add(createPersonnel("Martin","Messina","3339","Luther Avenue","Roma","TX","78584","885-372-2471","885-456-8772","885-639-2694","381-80-9830","1938-09-10","1997-08-17","mmessina@dre.com", Department.SALES));
//			personnels.add(createPersonnel("Demetria","Savell","6997","Kevin","Marne","MI","49435","567-393-9609","567-475-7388","567-976-7465","932-26-6917","1973-11-11","1999-10-22","dsavell@we.com", Department.SALES));
//			personnels.add(createPersonnel("Mindi","Lahey","6700","Barnstable","Atkinson","NC","28421","322-435-9814","322-384-7343","322-553-7201","667-33-1260","1954-11-12","1999-11-16","mlahey@orcro.com", Department.SALES));
//			personnels.add(createPersonnel("Cortney","Hillyard","426","Woodbridge","Neville","OH","45156","669-653-8200","669-247-4646","669-522-6304","806-20-5394","1942-08-15","1999-02-19","chillyard@herfe.gov", Department.SALES));
//			personnels.add(createPersonnel("Ronnie","Kitchens","5029","Fremont","Waite","ME","04492","820-823-8132","820-960-0075","820-712-8571","822-11-2907","1940-09-19","2005-02-06","rkitchens@itelifo.edu", Department.SALES));
//			personnels.add(createPersonnel("Qiana","Noonan","7030","Mays","Eau Claire","WI","54703","571-658-9619","571-820-6557","571-320-1449","834-72-7214","1943-03-11","2000-08-26","qnoonan@are.com", Department.SALES));
//			personnels.add(createPersonnel("Lazaro","Cerny","7249","Farmers Loop","Fpo","AP","96681","774-481-4472","774-773-5110","774-510-8359","256-32-6173","1977-10-01","1993-07-07","lcerny@rosicro.net", Department.SALES));
//			personnels.add(createPersonnel("Shelley","Narvaez","7977","Bafferton Lane","Paris","AR","72855","393-794-1861","393-485-7329","393-259-9485","823-31-5644","1969-01-15","1995-12-04","snarvaez@jasixe.com", Department.SALES));
//			personnels.add(createPersonnel("Zackery","Steely","2883","Ogden Mews","Lemoyne","OH","43441","594-324-1956","594-942-2959","594-232-7340","469-12-6294","1972-03-23","2001-12-11","zsteely@foab.com", Department.SALES));
//			personnels.add(createPersonnel("Percy","Beeson","5565","Indian Trail","Baton Rouge","LA","70808","742-320-8471","742-695-9569","742-292-3345","483-57-5422","1987-05-25","1995-08-05","pbeeson@meropon.com", Department.SALES));
//			personnels.add(createPersonnel("Ernesto","Deslauriers","7723","Bagley Avenue","Lynn","MA","01905","919-786-0861","919-829-8107","919-227-7954","104-34-4626","1982-09-15","1993-05-24","edeslauriers@ge.com", Department.SALES));
//			personnels.add(createPersonnel("Paul","Millman","7378","Sunderland","Vanderbilt","MI","49795","954-727-0428","954-687-6052","954-518-1800","657-63-4404","1971-01-25","2003-06-28","pmillman@te.com", Department.SALES));
//			personnels.add(createPersonnel("Gabriela","Behrens","4055","Westover Cove","Folsom","PA","19033","526-346-9930","526-445-7544","526-765-5213","562-97-2547","1935-10-11","1997-08-24","gbehrens@kyurnoncur.com", Department.SALES));
//			personnels.add(createPersonnel("Jeannette","Stapler","7037","Sasser","Memphis","TN","38152","685-476-5980","685-581-6607","685-787-3157","812-23-7799","1985-04-09","1994-10-05","jstapler@junwesare.com", Department.SALES));
//			personnels.add(createPersonnel("Don","Wetzel","5019","Fort","El Sobrante","CA","94803","953-966-5281","953-229-8966","953-797-0759","465-93-8618","1972-11-10","1992-10-22","dwetzel@akeglecuher.info", Department.SALES));
//			personnels.add(createPersonnel("Esteban","Schrader","7461","Moon Lake Blvd","Wolf Run","OH","43970","337-268-4115","337-233-0209","337-516-3379","360-34-9212","1965-02-04","1993-06-21","eschrader@kyeublpon.info", Department.SALES));
//			personnels.add(createPersonnel("Joe","Buhr","1613","Pembroke","Jet","OK","73749","338-418-8545","338-241-0563","338-874-5216","472-10-5033","1987-09-04","2005-02-03","jbuhr@eupon.com", Department.SALES));
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}		
//		addAllPersonnel(personnels);
//	}
//		
//	private static Personnel createPersonnel(
//			String firstName, String lastName, String house, String street, 
//			String city, String state, String zip, String phone, String fax, 
//			String mobile, String personnelNumber, String birthday, String hireDate, String email,
//			Department department) throws ParseException {
//		
//		final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//		
//		Personnel p = new Personnel();
//		
//		p.setFirstName(firstName);
//		p.setLastName(lastName);
////		p.setHouse(house);
////		p.setStreet(street);
////		p.setCity(city);
////		p.setState(state);
////		p.setZip(zip);
//		p.setPhone(phone);
//		p.setFax(fax);
//		p.setMobile(mobile);
//		p.setPersonnelNumber(personnelNumber);
//		p.setBirthday(df.parse(birthday));
////		p.setHireDate(df.parse(hireDate));
//		p.setEmail(email);
////		p.setDepartment(department);
//		
//		return p;
//	}

	
	
	public List<Personnel> getAllStaff() {
		return baseRepository.find("from Personnel");
	}
	
	public List<Personnel> searchStaff(PersonnelSearchDTO inSearch) {

		StringBuffer hql = new StringBuffer(
				"from Personnel as pl  where pl.id!=0 ");
		List tmpParams = new ArrayList();

		if (inSearch.getFullName() != null
				&& inSearch.getFullName().trim().length() != 0) {
			hql.append(" and upper(pl.fullName) like ? ");
			tmpParams.add("%" +  StringUtils.replace(inSearch.getFullName().trim().toUpperCase(),"*","%")+ "%");
		}
		if (inSearch.getEmail() != null
				&& inSearch.getEmail().trim().length() != 0) {
			hql.append(" and upper(pl.email) like ? ");
			tmpParams.add("%" + StringUtils.replace(inSearch.getEmail().trim().toUpperCase(),"*","%") + "%");
		}

		if (inSearch.getStaffNo() != null
				&& inSearch.getStaffNo().length() != 0) {
			hql.append(" and upper(pl.staffNo) like ?");
			tmpParams.add("%" + StringUtils.replace(inSearch.getStaffNo().trim().toUpperCase(),"*","%")+ "%");
		}

		//hql.append(" order by pl.");
		List rs = baseRepository.query("select count(*) "+hql, tmpParams.toArray());
		int totalSize = 0;
		if(rs!=null && !rs.isEmpty()){
			totalSize=((Long)rs.get(0)).intValue();
		}
		
		inSearch.setTotalResultSize(totalSize);
		if(totalSize == 0){					
			return Collections.EMPTY_LIST;
		}
		
		return (List) baseRepository.query(hql.toString(), tmpParams.toArray(),
				inSearch.getFirstPosition(), inSearch.getMaxResult());
	}
}
