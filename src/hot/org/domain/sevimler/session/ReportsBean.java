package org.domain.sevimler.session;


import java.io.ByteArrayOutputStream;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;




import javax.faces.event.ActionEvent;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.domain.sevimler.entity.Magaza;
import org.domain.sevimler.entity.Marka;
import org.domain.sevimler.entity.UrunGiris;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.contexts.ApplicationContext;
import org.jboss.seam.contexts.Context;
import org.jboss.seam.log.Log;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.international.StatusMessages;

import com.mysql.jdbc.Connection;

import javax.faces.event.ActionEvent; 
import java.util.HashSet;  
import java.util.Set;
//@Stateless
@Name("reports")
@Scope(ScopeType.SESSION)
public class ReportsBean implements Reports
{
    @Logger private Log log;

    @In StatusMessages statusMessages;
    
    @In EntityManager entityManager;
    
    @In FacesMessages facesMessages;
    
    private String sortMode = "single";

    private String selectionMode = "multi";
    
    private Date bitisTarihi;    
    private Date baslangicTarihi;
    private String startDate;
    private String endDate;
    UrunGiris urunGiris;
    
    //private List<UrunGiris> urunler = new ArrayList<UrunGiris>(); 
    
    //Selection selection = new SimpleSelection();
    //@DataModel
    List<UrunGiris> lst;
    
    private UrunGiris selectedFriend;  
    private Set<Integer> rowsToUpdate; 
    
    Marka code;
    private Magaza magazap;
    private String magazaname;
    String value;


	public ReportsBean()
    {
		rowsToUpdate = new HashSet<Integer>(); 
    }
	@SuppressWarnings("unchecked")
	public void data(){
		lst = new ArrayList<UrunGiris>();
		if((!this.getValue().equals("is null")&&(this.getValue().equals("Bekliyor")))){
			Query query = entityManager.createQuery("select u from UrunGiris u where u.onay = :values and u.magaza = :magaza")
			.setParameter("values", this.getValue())
			.setParameter("magaza", magazap);						
			lst = query.getResultList();
			if(lst.isEmpty())
				facesMessages.add("Kayýt Bulunamadý!..");
	    }else if((!this.getValue().equals("is null"))&&(this.getValue().equals("Hazir"))){
			Query query = entityManager.createQuery("select u from UrunGiris u where u.onay = :values or u.onay = :red and u.magaza = :magaza")
			.setParameter("values", this.getValue())
			.setParameter("red","Rededildi")
			.setParameter("magaza", magazap);			
			lst = query.getResultList();
			if(lst.isEmpty())
				facesMessages.add("Kayýt Bulunamadý!..");
		}else{
			System.out.println("su an values :"+this.getValue());
			Query query = entityManager.createQuery("select u from UrunGiris u where u.magaza = :magaza and u.onay is null")
			.setParameter("magaza", magazap);
			lst = query.getResultList();
			if(lst.isEmpty())
				facesMessages.add("Kayýt Bulunamadý!..");
		}
		
	}
	@SuppressWarnings("unchecked")
	public void manueldata(){
		lst = new ArrayList<UrunGiris>();
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println("getBaslangicTarihi() :"+getBaslangicTarihi());
		if((getBaslangicTarihi()!=null)||(getBitisTarihi()!=null)){
		String dateE = sd.format(getBitisTarihi());
		String dateS = sd.format(getBaslangicTarihi());
		Query query = entityManager.createNativeQuery("select * from UrunGiris u where( u.cikisTarihi between '"+dateS+"' and '"+dateE+"' ) and u.magaza_id = '"+magazap.getId()+"' ",UrunGiris.class);		
		startDate = dateS;
		endDate   = dateE;
		List<UrunGiris> resultList = query.getResultList();
		lst = resultList;
		if(lst.isEmpty())
			facesMessages.add("Kayýt Bulunamadý!..");
		}else{
			facesMessages.add("Baþlangýç veya Bitiþ tarihlerini eksik girdiniz!...");
		}
	}
	@SuppressWarnings("unchecked")
	public void manueldataemail(){
		lst = new ArrayList<UrunGiris>();
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println("getBaslangicTarihi() :"+getBaslangicTarihi());
		if((getBaslangicTarihi()!=null)||(getBitisTarihi()!=null)){
		String dateE = sd.format(getBitisTarihi());
		String dateS = sd.format(getBaslangicTarihi());
		Query query = entityManager.createNativeQuery("select * from UrunGiris u where( u.cikisTarihi between '"+dateS+"' and '"+dateE+"' ) and u.onay = 'Hazir' or u.onay = 'Rededildi'",UrunGiris.class);		
		startDate = dateS;
		endDate   = dateE;
		List<UrunGiris> resultList = query.getResultList();
		lst = resultList;
		if(lst.isEmpty())
			facesMessages.add("Kayýt Bulunamadý!..");
		}else{
			facesMessages.add("Baþlangýç veya Bitiþ tarihlerini eksik girdiniz!...");
		}
		//Contexts.getSessionContext().set("",object);
	}
	 public String rowClickAction() {  
	        System.out.println("Row clicked...qqqqqqqqqq");  
	          
	        selectedFriend.setSelect(!selectedFriend.isSelect());  
	          
	        rowsToUpdate.clear();  
	        rowsToUpdate.add(lst.indexOf(selectedFriend));  
	          
	        return null;  
	    }  
//	public void secilenUrunler(ActionEvent event){
//		Iterator<UrunGiris> iterator = lst.iterator();
//		while(iterator.hasNext()){
//			UrunGiris urun = iterator.next();
//			facesMessages.add("secilen urunler :"+urun.isSelect());
//			System.out.println(urun.isSelect());
//		}
//	}
	public void liste(){
		//UrunGiris urunGiris = new UrunGiris();
		for(UrunGiris urunGiris : lst){
			System.out.println(" "+urunGiris.getId() +" "+urunGiris.getDurumKod());
		}
	}

	@SuppressWarnings("unchecked")
	public List<String> aksesuarListele(){
		ArrayList<String> lst = new ArrayList<String>();
		Query query = entityManager.createQuery("select aks from Aksesuar aks");
		lst = (ArrayList<String>) query.getResultList();
		return lst;
		
	}
	public void manuelraporlist(){
		HashMap<String,Date> jasperParameter = new HashMap<String,Date>();
		try{
			     String jrxmlFile = "c:/reports/sevimlerListReport2.jrxml";//ireport ile oluï¿½turduï¿½umuz ve derlememiz gereken dosyanï¿½n yolu
					jasperParameter.put("startDate", getBaslangicTarihi());
			        jasperParameter.put("endDate", getBitisTarihi());
			        JasperDesign jasperDesign = JRXmlLoader.load(jrxmlFile);
					JasperReport jasperReport;
					jasperReport = JasperCompileManager.compileReport(jasperDesign);//raporumuzu derliyoruz
					ByteArrayOutputStream rrtReport = new ByteArrayOutputStream();
					JasperPrint jasperPrint = JasperFillManager.fillReport ( jasperReport,jasperParameter,this.con());
					JRRtfExporter rtfExporter = new JRRtfExporter(); 
					rtfExporter.setParameter(JRExporterParameter. JASPER_PRINT, jasperPrint);
					rtfExporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, jrxmlFile + "/sevimlerSevkiyat.rtf");
					rtfExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, rrtReport);
					rtfExporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, "sevimlerSevkiyat.doc");
					rtfExporter.exportReport();
					byte bytes[]=rrtReport.toByteArray();
					rrtReport.close();
					javax.faces.context.FacesContext faces = javax.faces.context.FacesContext.getCurrentInstance();
					HttpServletResponse response = (HttpServletResponse) faces.getExternalContext().getResponse();
					ServletOutputStream out = null;
					response.setContentType("application/vnd.ms-word");
					response.setHeader("Content-disposition","attachment; filename=sevimlerSevkiyat.doc");
					out = response.getOutputStream();
					out.write(bytes);
					faces.responseComplete();
		    	 log.info("Islem Basarili sevkiyatRaporList2","");
			     this.con().close();

			}catch (Exception e) {
				e.printStackTrace();
				facesMessages.add("Rapor Listesi Alýnamadý ayrýntý : "+e.getMessage());
				
			  }
			
		
	}
	public ArrayList<UrunGiris> urunlist(){
		ArrayList<UrunGiris> lst = new ArrayList<UrunGiris>();
		//System.out.println("first :"+getBaslangicTarihi());
		//System.out.println("last :"+getBitisTarihi());
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		if((!getBaslangicTarihi().equals(""))||(!getBitisTarihi().equals(""))){
		String dateE = sd.format(getBitisTarihi());
		String dateS = sd.format(getBaslangicTarihi());
		Query query = entityManager.createNativeQuery("select * from UrunGiris u where( u.cikisTarihi between '"+dateS+"' and '"+dateE+"' )",UrunGiris.class);		
		startDate = dateS;
		endDate   = dateE;
		List<UrunGiris> resultList = query.getResultList();
		lst = (ArrayList<UrunGiris>) resultList;
		if(lst.isEmpty())
			facesMessages.add("Kayýt Bulunamadý!..");
		}else{
			facesMessages.add("Baþlangýç veya Bitiþ tarihlerini eksik girdiniz!...");
		}
		return lst;
	}
    public Connection con() throws SQLException{
	    Connection connection = null;
	String path="com.mysql.jdbc.Driver";
    String user="root";
    String pass="root";
    String url ="jdbc:mysql://localhost:3306/sevimler"; 
		try {
			Class.forName(path).newInstance();
			connection = (Connection) DriverManager.getConnection(url, user, pass);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	return connection;	
}
	public String manuelSelected(UrunGiris urn) {
		facesMessages.add("select rowwwwwww :"+urn.getId());
//        Iterator<UrunGiris> entries = this.getLst().iterator();
//        while (entries.hasNext()) {
//                UrunGiris urun = entries.next();
//                if (urun.isSelect()) {
//                		urunler.add(urun);
//                		System.out.println("selects urun :"+urun.getId());
//                        //entries.remove();
//                }
//        }
        return null;
    }
//	public String manuelSelected() {
//        Iterator<UrunGiris> entries = lst.iterator();
//        while (entries.hasNext()) {
//                UrunGiris urun = entries.next();
//                if (urun.isSelect()) {
//                		urunler.add(urun);
//                		System.out.println("selects urun :"+urun.getId());
//                        //entries.remove();
//                }
//        }
//        return null;
//    }

//	  public void takeSelection() {
//
//	        lst.clear();
//	        Iterator<Object> iterator = getSelection().getKeys();
//	        while (iterator.hasNext()) {
//	            Object key = iterator.next();
//	            //UrunGiris urun = (UrunGiris) key;
//	            System.out.println("selected row urunGrisi :"+key.toString());
//	            
//	            //getCapitalsDataModel().setRowKey(key);
//	            //selectedCapitals.add(getCapitalsDataModel().getRowData());
//          
//	        }
//	 }
	
	public void setCode(Marka code) {
		this.code = code;
	}

	public Marka getCode() {
		return code;
	}
	
	
    public Magaza getMagazap() {
		return magazap;
	}
    
    
    public void setMagazap(Magaza magazap) {
		this.magazap = magazap;
	}
    
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
    // add additional action methods
	public List<UrunGiris> getLst() {
		return lst;
	}
	public void setLst(List<UrunGiris> lst) {
		this.lst = lst;
	}
//	public Selection getSelection() {
//		return selection;
//	}
//	public void setSelection(Selection selection) {
//		this.selection = selection;
//	}
	public String getSortMode() {
		return sortMode;
	}
	public void setSortMode(String sortMode) {
		this.sortMode = sortMode;
	}
	public String getSelectionMode() {
		return selectionMode;
	}
	public void setSelectionMode(String selectionMode) {
		this.selectionMode = selectionMode;
	}
	public UrunGiris getSelectedFriend() {
		return selectedFriend;
	}
	public void setSelectedFriend(UrunGiris selectedFriend) {
		this.selectedFriend = selectedFriend;
	}
	public Set<Integer> getRowsToUpdate() {
		return rowsToUpdate;
	}
	public void setRowsToUpdate(Set<Integer> rowsToUpdate) {
		this.rowsToUpdate = rowsToUpdate;
	}
	public Date getBitisTarihi() {
		return bitisTarihi;
	}
	public void setBitisTarihi(Date bitisTarihi) {
		this.bitisTarihi = bitisTarihi;
	}
	public Date getBaslangicTarihi() {
		return baslangicTarihi;
	}
	public void setBaslangicTarihi(Date baslangicTarihi) {
		this.baslangicTarihi = baslangicTarihi;
	}
	public String getMagazaname() {
		return magazaname;
	}
	public void setMagazaname(String magazaname) {
		this.magazaname = magazaname;
	}

	

}
