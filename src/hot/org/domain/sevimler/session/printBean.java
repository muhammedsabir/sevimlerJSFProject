package org.domain.sevimler.session;


import java.util.ArrayList;
import java.util.List;


import javax.persistence.EntityManager;

import org.domain.sevimler.entity.UrunGiris;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.datamodel.DataModel;


@Name("printBean")
@Scope(ScopeType.EVENT)
public class printBean {
	
	@In EntityManager entityManager;
	
	//@DataModel(scope= ScopeType.EVENT)
	//private List<UrunGiris> lst;
	
	public List<UrunGiris> veriListele(){
		List<UrunGiris> lst = new ArrayList<UrunGiris>();
		//lst = entityManager.createQuery("from urunGiris urun").getResultList();
		return lst;
		
	}

}
