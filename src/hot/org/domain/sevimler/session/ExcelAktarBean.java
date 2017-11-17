package org.domain.sevimler.session;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.domain.sevimler.entity.UrunGiris;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.web.RequestParameter;
import org.jboss.seam.faces.FacesMessages;

@Name("excelaktarBean")
@Scope(ScopeType.SESSION)
public class ExcelAktarBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@In FacesMessages facesMessages;
	@RequestParameter String firstdate;
	@RequestParameter String lastdate;
	ExcelAktarPojo aktarPojo;
	@RequestParameter String first;
	@RequestParameter String last;
	


	
	public ArrayList<UrunGiris> urunlist() throws ParseException{
		
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd",new Locale("tr,TR"));
		//System.out.println("pojo :"+getAktarPojo().getFirstDate().toString());
		System.out.println("getFirt() :"+first);
		
		
		Date d = new Date();
		first = first.replace("EET","");
		d = sd.parse(first);
		String _firstdate = sd.format(d);
//		String _lastdate  = sd.format(last);
		System.out.println("firstdate :"+_firstdate);
//		System.out.println("lastdate  :"+_lastdate);
		List<UrunGiris> lst = new ArrayList<UrunGiris>();
		if((aktarPojo.getFirstDate()!=null)){
			facesMessages.add("Aktar dan Gelen firstDate:"+aktarPojo.getFirstDate());
		}else{
			facesMessages.add("Baþlangýç ve Bitiþ Tarih paremetreleri eksik");
		}
		return (ArrayList<UrunGiris>) lst;
	}

	public ExcelAktarPojo getAktarPojo() {
		return aktarPojo;
	}

	public void setAktarPojo(ExcelAktarPojo aktarPojo) {
		this.aktarPojo = aktarPojo;
	}


}
