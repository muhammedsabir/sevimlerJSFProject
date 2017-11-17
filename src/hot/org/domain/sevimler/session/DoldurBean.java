package org.domain.sevimler.session;



import org.domain.sevimler.entity.Marka;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.log.Log;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.international.StatusMessages;

//@Stateless
@Name("doldur")
public class DoldurBean implements Doldur
{
    @Logger private Log log;

    @In StatusMessages statusMessages;
   
    @In FacesMessages  facesMessages;
    
    private Marka code;
    
    public Marka getCode() {
		return code;
	}
	public void setCode(Marka code) {
		this.code = code;
	}
	public void doldur()
    {
        // implement your business logic here
        log.info("doldur.doldur() action called");
        statusMessages.add("doldur");
        facesMessages.add("@Request :");
    }
    public void ttt(){
   	 facesMessages.add("@Request :"+code.getId());
   }

	

    // add additional action methods

}
