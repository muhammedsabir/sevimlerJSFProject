package org.domain.sevimler.session;

import java.io.Serializable;

import javax.persistence.Entity;

import org.jboss.seam.annotations.Name;
@Name("excelAktarPojo")
@Entity
public class ExcelAktarPojo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String firstDate;
	private String lastDate;
	
	public ExcelAktarPojo(){}	
	
	public ExcelAktarPojo(String firstDate, String lastDate) {
		super();
		this.firstDate = firstDate;
		this.lastDate = lastDate;
	}

	public String getFirstDate() {
		return firstDate;
	}
	public void setFirstDate(String firstDate) {
		this.firstDate = firstDate;
	}
	public String getLastDate() {
		return lastDate;
	}
	public void setLastDate(String lastDate) {
		this.lastDate = lastDate;
	}
	

}
