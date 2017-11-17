package org.domain.sevimler.session;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.faces.FacesMessages;

@Name("mesaj")
public class Mesaj {
	
	@In FacesMessages facesMessages;
	private String mesaj="ilk seam projesi";
	public void deneme(){
		facesMessages.add("deneme mesaj");
	}
	public void setMesaj(String mesaj) {
		this.mesaj = mesaj;
	}
	public String getMesaj() {
		return mesaj;
	}

}
