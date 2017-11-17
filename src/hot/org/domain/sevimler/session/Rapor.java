package org.domain.sevimler.session;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.io.Serializable;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


import javax.ejb.SessionContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.Basic;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.Query;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;


import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.domain.sevimler.entity.Aksesuar;
import org.domain.sevimler.entity.MusteriGiris;
import org.domain.sevimler.entity.UrunGiris;
import org.jboss.seam.ScopeType;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;

import org.jboss.seam.annotations.Scope;

import org.jboss.seam.annotations.web.RequestParameter;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.log.Log;


import com.mysql.jdbc.Connection;

@Name("rapor")
@Scope(ScopeType.PAGE)

//@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class Rapor implements Serializable{

	private static final long serialVersionUID = 1L;
	@In	FacesMessages facesMessages;
	@In EntityManager entityManager;
	@RequestParameter Long memberid;
	//@Out(scope = ScopeType.SESSION)
    UrunGiris urunGiris;
    MusteriGiris musteriGiris;
    public ArrayList<UrunGiris> ucGunGecen;
    public List<UrunGiris> ucGunCevapGelmeyen;
    @Logger private Log log;
    private boolean flag=false;
//    @In(value="org.jboss.seam.transaction.transaction", required=false) 
//	UserTransaction userTransaction;
    private String aksesuarlar="";
    public Rapor(){
    	
    }
    
	public ArrayList<UrunGiris> getUcGunGecen() {
		return ucGunGecen;
	}

	public List<UrunGiris> getUcGunCevapGelmeyen() {
		return ucGunCevapGelmeyen;
	}

	public void setUcGunCevapGelmeyen(List<UrunGiris> ucGunCevapGelmeyen) {
		this.ucGunCevapGelmeyen = ucGunCevapGelmeyen;
	}

	public void setUcGunGecen(ArrayList<UrunGiris> ucGunGecen) {
		this.ucGunGecen = ucGunGecen;
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
//    @Begin(join = true, flushMode=FlushModeType.MANUAL)
    //@Observer("org.jboss.seam.afterTransactionSuccess")
	public void sevkiyatRapor() throws JRException, IOException, SQLException, NamingException, NotSupportedException, SystemException {
		boolean r = false;
//		InitialContext ic = new InitialContext();
//		TransactionManager utm = (TransactionManager) ic.lookup("java:/TransactionManager");
		if(sevkiyatRaporUpdateDurumBir()){
			sevkiyatListRapor();	
	    if(flag){	
		if(sevkiyatRaporUpdateDurumIki()){
				log.info("Islem Basarili sevkiyatRaporUpdateDurumIki DurumKod = 3 Guncellendi","");
				facesMessages.add("Sevkiyat Rapor Islem Basarili Guncelleme 3 olarak yapildi");
			}else{
				log.error("Hata Beklenmeyen Durum : sevkiyatRaporUpdateDurumIki DurumKod = 3 Guncellenemedi!..","");
				facesMessages.add("Hata Beklenmeyen Durum : sevkiyatRaporUpdateDurumIki DurumKod = 3 Guncellenemedi!..");
			}//ic.close();
	   }
		//return r;
	}else{
		 facesMessages.add("Sevkiyat Listesi Alýnacak Hazir veya Ýade Urun Bulunamadi!.."); 
		 log.error("Hata Beklenmeyen Durum : sevkiyatRaporUpdateDurumBir DurumKod = 2 Guncellenemedi!.. veya Sevkiyat hazýr ürün yok","");
	  }
	
}
//    @End
    public void raporDurumKodUpdate(){
	    if(flag){	

			 UserTransaction userTx = (UserTransaction) org.jboss.seam.Component.getInstance("org.jboss.seam.transaction.transaction");
			 String sql = "UPDATE urungiris urn SET urn.durumKod = 2 WHERE urn.durumKod !=3 and( urn.onay = 'Hazir' or urn.onay = 'Rededildi') ";
			 
			 try {
				 userTx.begin();				 
				 Query query = entityManager.createNativeQuery(sql);
				 entityManager.joinTransaction();
	             int k = query.executeUpdate();
				 userTx.commit();
				if(k>0)
					facesMessages.add("Toplu Sevkiyat Rapor DurumKod Basari ile Guncellendi");
					
				
			} catch (Exception e) {
//				entityManager.close();
				facesMessages.add("sevkiyatList Durum Bir Rapor DurumKod Guncelleme hata ayrýntý :"+e.getMessage()+" "+e.toString());
			}
	    }

    }

	private void sevkiyatListRapor() {
		HashMap<String,String> jasperParameter = new HashMap<String,String>();
		try{
			     String jrxmlFile = "c:/reports/sevimlerListReport.jrxml";//ireport ile oluï¿½turduï¿½umuz ve derlememiz gereken dosyanï¿½n yolu
					
			        JREmptyDataSource dataSource = new JREmptyDataSource();
					JasperDesign jasperDesign = JRXmlLoader.load(jrxmlFile);
					JasperReport jasperReport;
					jasperReport = JasperCompileManager.compileReport(jasperDesign);//raporumuzu derliyoruz

					//JasperPrint jasperPrint = JasperFillManager.fillReport ( jasperReport,jasperParameter);

					//raporumuzu oluï¿½turuyoruz ve ï¿½ï¿½ktï¿½sï¿½nï¿½ almak iï¿½in jasperprint e aktarï¿½yoruz

					//byte bytes[] = new byte[10];
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
		    	 log.info("Islem Basarili sevkiyatRaporUpdateDurumBir DurumKod = 2 Guncellendi","");
			     this.con().close();

			}catch (Exception e) {
				e.printStackTrace();
				
				if(urunGiris.equals(null)){
					facesMessages.add("Rapor da hata kayýt bulunamadý ayrýntý :"+e.getMessage());
				}else{
					facesMessages.add("Rapor oluþturalamadý ayrýntý :"+e.getMessage());
				}
				
			  }
			
         
		
	}

	public  void dizaynReport() throws JRException, IOException{
		    urunGiris = dataGetir();
		    
		    try {
						
		    HashMap jasperParameter = new HashMap();
		    //Object ucret="";
			//facesMessages.add("urunGirisHome Magaza",memberid);
            //if((urunGiris.getOnay().equals("Hazir"))||(urunGiris.getOnay().equals("Rededildi"))){
			//sube kesa ise ucret tekli rapor gozukmeyecek
			if(urunGiris.getMagaza().getId()==2){
			  urunGiris.setUcret(null);
			}if(urunGiris.getOnay()!=null){
			if(urunGiris.getOnay().equals("Rededildi")){//rededilen cagrýlarda ucret gozukemeyecek
			  urunGiris.setUcret(null);	
			  }
			 }
			
		    String ucret = String.valueOf(urunGiris.getUcret()==null ? "" :urunGiris.getUcret());
			jasperParameter.put("parameter6",  urunGiris.getMagaza().getName()+" "+urunGiris.getSube().getName());// firma adý
			jasperParameter.put("parameter7",urunGiris.getMarka().getName()+"  "+ urunGiris.getModel().getName());//cihaz marka model
			jasperParameter.put("parameter8",urunGiris.getAriza());//
			jasperParameter.put("parameter9",urunGiris.getArizaTespit());//yapýlan iþlem
			jasperParameter.put("parameter10",urunGiris.getAciklama());//acýklama
			jasperParameter.put("parameter16","444 888 0");//tlf
			jasperParameter.put("parameter17",urunGiris.getSr());//firma seri no
			jasperParameter.put("parameter18",urunGiris.getSeri());//Cihaz seri no
			jasperParameter.put("parameter19",aksesuarlar);//aksesuar
			jasperParameter.put("parameter20",ucret);//ucret
			jasperParameter.put("parameter1",Long.toString(urunGiris.getId()));	
			//ServletContext servletContext = (ServletContext) javax.faces.context.FacesContext.getCurrentInstance().getExternalContext().getContext();
			//String jrxmlFile = servletContext.getRealPath("") + File.separator + "raports\\report2.jrxml";
			String jrxmlFile = "c:/reports/report7.jrxml";//ireport ile oluï¿½turduï¿½umuz ve derlememiz gereken dosyanï¿½n yolu
	        JREmptyDataSource dataSource = new JREmptyDataSource();
			JasperDesign jasperDesign = JRXmlLoader.load(jrxmlFile);
			JasperReport jasperReport;
			jasperReport = JasperCompileManager.compileReport(jasperDesign);//raporumuzu derliyoruz

			//JasperPrint jasperPrint = JasperFillManager.fillReport ( jasperReport,jasperParameter);

			//raporumuzu oluï¿½turuyoruz ve ï¿½ï¿½ktï¿½sï¿½nï¿½ almak iï¿½in jasperprint e aktarï¿½yoruz

			//byte bytes[] = new byte[10];
			ByteArrayOutputStream rrtReport = new ByteArrayOutputStream();

			JasperPrint jasperPrint = JasperFillManager.fillReport ( jasperReport,jasperParameter,dataSource);

			JRRtfExporter rtfExporter = new JRRtfExporter(); 

			rtfExporter.setParameter(JRExporterParameter. JASPER_PRINT, jasperPrint);

			rtfExporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, jrxmlFile + "/sevimler.rtf");

			rtfExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, rrtReport);

			rtfExporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, "sevimler.doc");

			rtfExporter.exportReport();

			byte bytes[]=rrtReport.toByteArray();

			rrtReport.close();
			javax.faces.context.FacesContext faces = javax.faces.context.FacesContext.getCurrentInstance();

			HttpServletResponse response = (HttpServletResponse) faces.getExternalContext().getResponse();

			ServletOutputStream out = null;

			response.setContentType("application/vnd.ms-word");

			response.setHeader("Content-disposition","attachment; filename=sevimler.doc");

			out = response.getOutputStream();

			out.write(bytes);
			
			faces.responseComplete();
           /* }else{
				facesMessages.add("Sevkiyat Raporu Alabilmek için tüm kayýtlarýn Onay biligisi Hazýr veya Rededildi olmasý gerekli...");
				
			}*/
		    } catch (Exception e) {
				e.printStackTrace();
				if(urunGiris.equals(null)){
					facesMessages.add("Rapor da hata kayýt bulunamadý ayrýntý :"+e.getMessage());
				}else{
					facesMessages.add("Rapor oluþturalamadý ayrýntý :"+e.getMessage());
				}
				
			}
		    urunGiris.setDurumKod(1);
		    if(!tekliRaporDurumKodUpdate(urunGiris)){
		     	log.error("Hata Tekli Rapor UrunGiris Table DurumKod Guncellenemedi!..","");
		    }
	    }
	public  void dizaynReportMusteri() throws JRException, IOException{
	    musteriGiris = dataGetirmusteri();
	    
	    try {
					
	    HashMap jasperParameter = new HashMap();
	    
		
	    String ucret = String.valueOf(musteriGiris.getUcret()==null ? "" :musteriGiris.getUcret());
		jasperParameter.put("parameter6",  musteriGiris.getName());// firma adý
		jasperParameter.put("parameter7",musteriGiris.getMarka().getName()+"  "+ musteriGiris.getModel().getName());//cihaz marka model
		jasperParameter.put("parameter8",musteriGiris.getAriza());//
		jasperParameter.put("parameter9",musteriGiris.getArizaTespit());//yapýlan iþlem
		jasperParameter.put("parameter10",musteriGiris.getAciklama());//acýklama
		jasperParameter.put("parameter16",musteriGiris.getTelefon());//tlf
		jasperParameter.put("parameter17"," ");//firma seri no
		jasperParameter.put("parameter18",musteriGiris.getCihazSeriNo().toString());//Cihaz seri no
		jasperParameter.put("parameter19",aksesuarlar);//aksesuar
		jasperParameter.put("parameter20",ucret);//ucret
		jasperParameter.put("parameter1",Long.toString(musteriGiris.getId()));	
		//ServletContext servletContext = (ServletContext) javax.faces.context.FacesContext.getCurrentInstance().getExternalContext().getContext();
		//String jrxmlFile = servletContext.getRealPath("") + File.separator + "raports\\report2.jrxml";
		String jrxmlFile = "c:/reports/report7.jrxml";//ireport ile oluï¿½turduï¿½umuz ve derlememiz gereken dosyanï¿½n yolu
        JREmptyDataSource dataSource = new JREmptyDataSource();
		JasperDesign jasperDesign = JRXmlLoader.load(jrxmlFile);
		JasperReport jasperReport;
		jasperReport = JasperCompileManager.compileReport(jasperDesign);//raporumuzu derliyoruz

		ByteArrayOutputStream rrtReport = new ByteArrayOutputStream();

		JasperPrint jasperPrint = JasperFillManager.fillReport ( jasperReport,jasperParameter,dataSource);

		JRRtfExporter rtfExporter = new JRRtfExporter(); 

		rtfExporter.setParameter(JRExporterParameter. JASPER_PRINT, jasperPrint);

		rtfExporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, jrxmlFile + "/sevimler.rtf");

		rtfExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, rrtReport);

		rtfExporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, "sevimler.doc");

		rtfExporter.exportReport();

		byte bytes[]=rrtReport.toByteArray();

		rrtReport.close();
		javax.faces.context.FacesContext faces = javax.faces.context.FacesContext.getCurrentInstance();

		HttpServletResponse response = (HttpServletResponse) faces.getExternalContext().getResponse();

		ServletOutputStream out = null;

		response.setContentType("application/vnd.ms-word");

		response.setHeader("Content-disposition","attachment; filename=sevimler.doc");

		out = response.getOutputStream();

		out.write(bytes);
		
		faces.responseComplete();
       /* }else{
			facesMessages.add("Sevkiyat Raporu Alabilmek için tüm kayýtlarýn Onay biligisi Hazýr veya Rededildi olmasý gerekli...");
			
		}*/
	    } catch (Exception e) {
			e.printStackTrace();
			if(musteriGiris.equals(null)){
				facesMessages.add("Rapor da hata kayýt bulunamadý ayrýntý :"+e.getMessage());
			}else{
				facesMessages.add("Rapor oluþturalamadý ayrýntý :"+e.getMessage());
			}
			
		}

    }
	 
	 public UrunGiris dataGetir(){
		// try {
			 UrunGiris urunGiris = new UrunGiris();
			 urunGiris = entityManager.find(UrunGiris.class,memberid);
			 aksesuarGetir(urunGiris.getId());
/*		} catch (Exception e) {
			e.printStackTrace();
			facesMessages.add("UrunGiris find class hata :"+e.getMessage());
		}*/
		 
		 return urunGiris;
	 }
	 public MusteriGiris dataGetirmusteri(){
		
				 MusteriGiris musteriGiris = new MusteriGiris();
				 musteriGiris = entityManager.find(MusteriGiris.class,memberid);
				 aksesuarGetirmusteri(musteriGiris.getId());
			 return musteriGiris;
		 }
	 private void aksesuarGetir(Long id) {
		//Aksesuar aksesuar = new Aksesuar(); 
		List<Aksesuar> list = new ArrayList<Aksesuar>();
		//aksesuar  = entityManager.find(Aksesuar.class,id);
		//aksesuarlar = aksesuar.getName();
		//Query query = entityManager.createQuery("select aks from urungiris_aksesuar aks where aks.UrunGiris_id :ID").setParameter("ID",id);
		String sql = "select * from sevimler.aksesuar aks where aks.id in(select aksesuarType_id from sevimler.urungiris_aksesuar where UrunGiris_id = '"+id+"' )";
		Query query = entityManager.createNativeQuery(sql,Aksesuar.class);
		 List resultList = query.getResultList();
		list = resultList;
		for (int i=0;i<list.size();i++) {
			       Aksesuar aksesuar = (Aksesuar)list.get(i);
			       aksesuarlar += aksesuar.getName();
			       aksesuarlar +=" ";
			       //aksesuarlar +="/n";
		}
//		for (Aksesuar aksesuar2 : lst) {
//			 aksesuarlar = aksesuar2.getName();
//			 aksesuarlar +="/n";
//		}
		
	}
	 private void aksesuarGetirmusteri(Long id) {
			//Aksesuar aksesuar = new Aksesuar(); 
			List<Aksesuar> list = new ArrayList<Aksesuar>();
			//aksesuar  = entityManager.find(Aksesuar.class,id);
			//aksesuarlar = aksesuar.getName();
			//Query query = entityManager.createQuery("select aks from urungiris_aksesuar aks where aks.UrunGiris_id :ID").setParameter("ID",id);
			String sql = "select * from sevimler.aksesuar aks where aks.id in(select aksesuarType_id from sevimler.musterigiris_aksesuar where MusteriGiris_id = '"+id+"' )";
			Query query = entityManager.createNativeQuery(sql,Aksesuar.class);
			 List resultList = query.getResultList();
			list = resultList;
			for (int i=0;i<list.size();i++) {
				       Aksesuar aksesuar = (Aksesuar)list.get(i);
				       aksesuarlar += aksesuar.getName();
				       aksesuarlar +=" ";
				       //aksesuarlar +="/n";
			}
			
		}
	//3 günü geçen (bakýlmayan) çaðrýlar
	 public int ucGunuGecenCagrilar(){
		 Calendar cal = Calendar.getInstance();
		 cal.add(Calendar.DAY_OF_YEAR, -3);
		 Date maxModDate = cal.getTime();
		 ucGunGecen = new ArrayList<UrunGiris>();
		 // query database
		 //String query="select urn.kayitTarihi from UrunGiris urn where (day(current_date()) - day(urn.kayitTarihi)) <=3";
		 //Query query = entityManager.createQuery("from UrunGiris urn where urn.kayitTarihi <= :maxModDate");
		 Query query = entityManager.createNativeQuery("SELECT * FROM urungiris urn WHERE urn.kayitTarihi <= SYSDATE()-3 AND urn.onay is null");
		 //query.setParameter("maxModDate", maxModDate);
		 //query.setParameter("durum","null");
		 this.ucGunGecen = (ArrayList<UrunGiris>) query.getResultList();
		 
		 return ucGunGecen.size();
	 }
	 //3 gündür cevap gelmeyen çaðrýlar
	 public int ucGundurCevapGelmeyenCagrilar(){
		 Calendar cal = Calendar.getInstance();
		 cal.add(Calendar.DAY_OF_YEAR,-3);
		 Date dateMod = cal.getTime();
		 ucGunCevapGelmeyen = new ArrayList<UrunGiris>();
		 Query query = entityManager.createNativeQuery("select * from UrunGiris u where u.kayitTarihi <= SYSDATE()-3 and u.onay = 'Bekliyor' ");
		 ucGunCevapGelmeyen = (ArrayList<UrunGiris>) query.getResultList();
		 return ucGunCevapGelmeyen.size();
	 }//bakýlmayan cagrýlar
	 public List<UrunGiris> getDataUrunGiris(){
		 ArrayList<UrunGiris> lst = new ArrayList<UrunGiris>();
		 Calendar cal = Calendar.getInstance();
		 cal.add(Calendar.DAY_OF_YEAR, -3);
		 Date maxModDate = cal.getTime();
		 ucGunGecen = new ArrayList<UrunGiris>();
		 // query database
		 //String query="select urn.kayitTarihi from UrunGiris urn where (day(current_date()) - day(urn.kayitTarihi)) <=3";
		 Query query = entityManager.createNativeQuery("SELECT * FROM urungiris urn WHERE urn.onay is null",UrunGiris.class);		 
		 //query.setParameter("maxModDate", maxModDate);
		 //query.setParameter("onay", "Bekliyor");
		 // Query query = entityManager.createNamedQuery("select * from urungiris as urn where urn.onay not in('B','O','H',null)");
		 List<UrunGiris> list = (ArrayList<UrunGiris>) query.getResultList();
		 
		 return list;
	 }
	 public List<UrunGiris> getUcgunCevapBekleyenCagrilarList(){
		 ArrayList<UrunGiris> lst = new ArrayList<UrunGiris>();

		 ucGunCevapGelmeyen = new ArrayList<UrunGiris>();// from UrunGiris u where u.onay = :bekliyor
		 Query query = entityManager.createNativeQuery("select * from UrunGiris u where u.kayitTarihi <= SYSDATE()-3 and u.onay = 'Bekliyor' ",UrunGiris.class);
		 List<UrunGiris> list =  query.getResultList();		 
		 
		 return list;
	 }
	 public boolean tekliRaporDurumKodUpdate(UrunGiris urunGiris){
		 boolean r = false;
		 if(!urunGiris.getId().equals(null)){
		  try{	 
			 Query query = entityManager.createNativeQuery("update UrunGiris set durumKod ='"+urunGiris.getDurumKod()+"' ");
			 System.out.println("query.executeUpdate() :"+query.executeUpdate());
			 r = true;
		  }catch (Exception e) {
			e.printStackTrace();
			facesMessages.add("UrunGiris durumKod field guncelleme de sorun ayrýntý:"+e.getMessage());
		  } 
		 }
		 return r;
	 }
	 //@Transactional(TransactionPropagationType.NEVER)
	 //@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	 //@Transactional
	 private boolean sevkiyatRaporUpdateDurumBir() throws NamingException, NotSupportedException, SystemException {
		 boolean r = false;
		 //UserTransaction userTx = (UserTransaction) org.jboss.seam.Component.getInstance("org.jboss.seam.transaction.transaction");
		 InitialContext ic = new InitialContext();
	     UserTransaction utx = (UserTransaction) ic.lookup("java:comp/UserTransaction");
		 String sql = "UPDATE urungiris urn SET urn.durumKod = 2 WHERE urn.durumKod !=3 and( urn.onay = 'Hazir' or urn.onay = 'Rededildi') ";
		 
		 try {
			
			//utx.begin();
			Query query = entityManager.createNativeQuery(sql);
			entityManager.joinTransaction();
			 int k = query.executeUpdate();
			if(k>0)
				r = true;
			utx.commit();
//			 List<UrunGiris> urunGirisList = new ArrayList<UrunGiris>();
//			// entityManager.isOpen(); 
//			 entityManager.setFlushMode(javax.persistence.FlushModeType.AUTO);
//			 Query query = entityManager.createQuery("select u from UrunGiris u where u.durumKod !=3 and u.durumKod !=2 and( u.onay =:Hazir or u.onay =:Rededildi)")
//			 .setParameter("Hazir","Hazir")
//			 .setParameter("Rededildi", "Rededildi");
//			 urunGirisList = query.getResultList();
//			 
//			 for(UrunGiris giris : urunGirisList){
//				 giris.setDurumKod(2);
//				 entityManager.merge(giris);
//				 entityManager.flush();
//				 r = true;
//			 }

             

		
			
		} catch (Exception e) {
//			e.printStackTrace();
			entityManager.close();
//			userTransaction.rollback();
			//userTx.rollback();
			facesMessages.add("sevkiyatList Durum Bir Rapor DurumKod Guncelleme hata ayrýntý :"+e.getMessage()+" "+e.toString());
		}
		     
		    //entityManager.flush();
		    //entityManager.close();
		 	flag = r;
			return r;
     }
	 //@Transactional
	 @Basic(fetch=FetchType.LAZY)
	 public boolean sevkiyatRaporUpdateDurumIki() throws NamingException {
		 boolean r = false;
		InitialContext ic = new InitialContext();
		
		UserTransaction utm = (UserTransaction) ic.lookup("java:comp/UserTransaction");
		 String sql = "UPDATE urungiris urn SET urn.durumKod = 3 WHERE urn.durumKod = 2 and (urn.onay = 'Hazir' or urn.onay = 'Rededildi') ";
		 try {
			utm.begin();
			Query query = entityManager.createNativeQuery(sql);
            entityManager.joinTransaction();
			int k = query.executeUpdate();
			if(k>0)
				r = true;
			utm.commit();
			/* 
			 List<UrunGiris> urunGirisList = new ArrayList<UrunGiris>();
			
			 Query query = entityManager.createQuery("select u from UrunGiris u where u.durumKod !=3 and u.durumKod =2 and( u.onay = 'Hazir' or u.onay = 'Rededildi')");
			 urunGirisList = query.getResultList();
			 if(urunGirisList.size() > 0){
				 for(UrunGiris giris : urunGirisList){
					 giris.setDurumKod(3);
					 entityManager.merge(giris);
					 entityManager.flush();
				 }
				 r = true;
			 }*/
			 
//			userTx.commit();
//			transaction.commit();
			//entityManager.close();
		} catch (Exception e) {
//			e.printStackTrace();
			//entityManager.close();
			//userTransaction.rollback();
//			userTransaction.rollback();
			facesMessages.add("sevkiyatList Rapor DurumKod 3 Guncelleme hata ayrýntý :"+e.toString());
			
		}
		 	
			return r;
     }
	 public ArrayList<UrunGiris> getsevkiyatListesi(){
		 ArrayList< UrunGiris> list = new ArrayList<UrunGiris>();
		 UrunGiris giris = new UrunGiris();
		 
		 return list;
	 }
	 
}
