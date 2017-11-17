package org.domain.sevimler.session;

import java.util.List;

import javax.ejb.Local;

import org.domain.sevimler.entity.Magaza;
import org.domain.sevimler.entity.Marka;
import org.domain.sevimler.entity.UrunGiris;

@Local
public interface Reports
{
    // seam-gen method
    //public void reports();
	public void setCode(Marka code);
	public Marka getCode();
	public void setValue(String value);
	public String getValue();
	public void data();
	public void setMagazaname(String magazaname);
	public String getMagazaname();
	public List<UrunGiris> getLst();
	public void setLst(List<UrunGiris> lst);
	public List<String> aksesuarListele();
	public Magaza getMagazap();
	public void setMagazap(Magaza magazap);
    // add additional interface methods here

}
