package org.domain.sevimler.session;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;

import org.domain.sevimler.entity.Mail;
import org.domain.sevimler.entity.UrunGiris;
import org.jboss.seam.Component;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.contexts.Contexts;
import org.jboss.seam.log.Log;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.faces.Renderer;
import org.jboss.seam.international.StatusMessages;

@Scope(ScopeType.SESSION)
@Name("Mailinformation")
@AutoCreate
public class Mailinformation implements Serializable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Logger private Log log;
    @In StatusMessages statusMessages;
    @In FacesMessages facesMessages;
    @In EntityManager entityManager;
    @In(required = true)
    //@Out(required = true , scope = ScopeType.CONVERSATION)
    UrunGirisHome urunGirisHome;
    
    //@In(create = true, required = true)
    //@Out(required = true , scope = ScopeType.CONVERSATION)
    UrunGiris urunGiris;
    public UrunGiris getUrunGiris() {
		return urunGiris;
	}

	public void setUrunGiris(UrunGiris urunGiris) {
		this.urunGiris = urunGiris;
	}
	List<UrunGiris> lst;
    //@In(create=true,required=false) Mail mail;
    public Mailinformation() {
		super();
		//lst = doldur();
	}

	public void mailinformation()
    {
        // implement your business logic here
        log.info("Mailinformation.mailinformation() action called");
        statusMessages.add("mailinformation");
    }
    @In(create=true)
    private Renderer renderer;
	public List<String> lstMail;
    
//    @Create
//    public void init(){
//    	urunGiris = new UrunGiris();
//    }
       
    public void send() {
        try {
//        	System.out.println("mail urun giriþ :");
        	
//        	if( urunGirisHome.getInstance() == null)
//        		System.out.println("nulllll");
        	urunGiris = urunGirisHome.getInstance();
        	
        	//urunGiris = (UrunGiris) Contexts.getSessionContext().get(UrunGiris.class); //Component.getInstance(UrunGiris.class);
           mailAdresBul();
           renderer.render("/mailinformation.xhtml");
           facesMessages.add("Email baþarý ile gönderildi");
       } catch (Exception e) {
           facesMessages.add("Email sending failed: " + e.getMessage());
           log.info("Email sending failed: " + e.getMessage());
       }
    }

	private void mailAdresBul() {
		lstMail = new ArrayList<String>();
		String query = "select"+ 
         " mail.mailAdres "+
         "  from Mail mail "+
         "  where"+
         " mail.sube.id =:sube ";
		lstMail =  (ArrayList<String>) entityManager.createQuery(query)
		.setParameter("sube", urunGiris.getSube().getId()).getResultList();
		
		
	}

//	private List<UrunGiris> doldur() {
//		List<UrunGiris> list = new ArrayList<UrunGiris>();
//		list.add(urunGiris);
//		return list;
//	}
//
//	public UrunGiris getUrunGiris() {
//		return urunGiris;
//	}
//
//	public void setUrunGiris(UrunGiris urunGiris) {
//		this.urunGiris = urunGiris;
//	}

	public List<UrunGiris> getLst() {
		return lst;
	}

	public void setLst(List<UrunGiris> lst) {
		this.lst = lst;
	}

	public List<String> getLstMail() {
		return lstMail;
	}

	public void setLstMail(List<String> lstMail) {
		this.lstMail = lstMail;
	}



    // add additional action methods

}
