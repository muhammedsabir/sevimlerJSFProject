package org.domain.sevimler.session;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.domain.sevimler.entity.Aksesuar;
import org.domain.sevimler.entity.Model;
import org.domain.sevimler.entity.UrunGiris;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.international.StatusMessages;





@Name("comboDoldur")
@Scope(ScopeType.PAGE)
public class ComboDoldur implements Serializable {
	
  
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int id;
	@In FacesMessages  facesMessages;
	
	//@In(create = true,required=false) 
    //UrunGiris UrunGiris;
    
	@In StatusMessages statusMessages;
	
    @In EntityManager entityManager;
    ArrayList<Model> lst;
    public ArrayList<Model> getLst() {
		return lst;
	}
    
    public void setLst(ArrayList<Model> lst) {
		this.lst = lst;
	}

	@DataModel
    List<SelectItem> aksesuarList;
	/*public List<String> getAksesuarList() {
		return aksesuarList;
	}*/
	/*	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}*/
    //@Factory
    public void ttt(){
    	 //request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
    	//statusMessages.add("reports "+UrunGiris.getMarka().getName());
    	//FacesMessages.instance().add("Hello World!"+UrunGiris.getMarka().getId());
    	facesMessages.add("combodoldur :"+"#{urunGirisHome.instance.marka.name}");
    }
/*    public void model(){
    	facesMessages.add(models.getName());
    }*/
    
	public void modelDoldur(){
	    //Component.getInstance("modelId");
		lst = new ArrayList<Model>();
	/*	facesMessages.add("id :"+markas.getName());*/
		lst = (ArrayList<Model>) entityManager.createQuery("select model from Model model where model.marka.id = #{urunGirisHome.instance.marka.id}").getResultList();
		// lst = resultList;
		
	}
	public void musteriModelDoldur(){
	    //System.out.println("l.getNewValue()"+l.getNewValue());
		lst = new ArrayList<Model>();
	
		lst = (ArrayList<Model>) entityManager.createQuery("select model from Model model where model.marka.id = #{musteriGirisHome.instance.marka.id}").getResultList();
		
		
	}
	public List<SelectItem> getAksesuarDoldur(){
		aksesuarList = new ArrayList<SelectItem>();
		List<Aksesuar> lst = (ArrayList<Aksesuar>)entityManager.createQuery("from Aksesuar aks").getResultList();
	    for (Aksesuar object : lst) {
			aksesuarList.add(new SelectItem( object,object.getName().toString()));
		}
		 
		
		 return aksesuarList;
	}
}
