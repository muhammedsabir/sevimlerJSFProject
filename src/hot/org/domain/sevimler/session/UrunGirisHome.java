package org.domain.sevimler.session;

import java.util.ArrayList;

import javax.persistence.EntityManager;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.End;
import org.jboss.seam.annotations.FlushModeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.web.RequestParameter;
import org.jboss.seam.contexts.Context;
import org.jboss.seam.contexts.Contexts;
import org.jboss.seam.framework.EntityHome;

import org.domain.sevimler.entity.Model;
import org.domain.sevimler.entity.UrunGiris;

@Name("urunGirisHome")
@AutoCreate
public class UrunGirisHome extends EntityHome<UrunGiris>
{
    @RequestParameter Long urunGirisId;
    @RequestParameter String modelBilgiId;
//    @In(create = true,required=false)
//    ComboDoldur comboDoldur;    
    
    private Model model; 
    boolean flag=true; 
    private ArrayList<Model> modelList;
	private ArrayList<Model> lst;
//	@In EntityManager entityManager;
    @Override
    public Object getId()
    {
        if (urunGirisId == null)
        {
            return super.getId();
        }
        else
        {
//        	if((!modelBilgiId.equals(""))&&(modelBilgiId!=null)){
//        		//System.out.println("UrunGiriþHome :"+modelBilgiId);
//        		flag = false; 
//        		model = new Model();
//        		model.setName(modelBilgiId);
//        		lst.add(model);
        		
//        		if(flag){
//        			flag = false;
//        		 Contexts.getSessionContext().set("urunGiris", this.getInstance());
//        		 
//        		}
//        		model.setName(getModelBilgiId());
//        		getInstance().setModel(model);
        	//}
           return urunGirisId;
        }
    }
//    public void modelDoldur(){
//	    //Component.getInstance("modelId");
//		lst = new ArrayList<Model>();
//	/*	facesMessages.add("id :"+markas.getName());*/
//		lst = (ArrayList<Model>) entityManager.createQuery("select model from Model model where model.marka.id = #{urunGirisHome.instance.marka.id}").getResultList();
//		// lst = resultList;
//		
//	}
    @Begin(join=true,flushMode=FlushModeType.MANUAL)
    public void setDurumKod(){
    	if((getInstance().getOnay().equals("Hazir"))||(getInstance().getOnay().equals("Rededildi")))
    	    getInstance().setDurumKod(1);
    }
    @End
    public String updateMy(){
       return super.update();
    }
    
    @Override @Begin(join=true)
    public void create() {
        super.create();
    }
//    
//   public void doldurModel(){
//	   if(modelBilgiId.equals("G")){
//		   comboDoldur.modelDoldur();
//	   }
//   }
	public String getModelBilgiId() {
		return modelBilgiId;
	}
	public void setModelBilgiId(String modelBilgiId) {
		this.modelBilgiId = modelBilgiId;
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public ArrayList<Model> getLst() {
		return lst;
	}
	public void setLst(ArrayList<Model> lst) {
		this.lst = lst;
	}

}
