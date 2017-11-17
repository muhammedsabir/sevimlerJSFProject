package org.domain.sevimler.session;




import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.domain.sevimler.entity.Aksesuar;
import org.jboss.seam.annotations.Name;
@Name("aksesuarConverter")
public class AksesuarConverter implements Converter{
	
	
	Aksesuar aksesuar;
	public Object getAsObject(FacesContext context, UIComponent component, String label) {
		/* aksesuar.setName(label);
		return aksesuar.getName();*/
		 //return label.toString();//choiceMap.get(label);
		String[] data = label.split(":");
		long id   = Long.valueOf(data[0]);
		String name = data[1];
	    aksesuar = new Aksesuar(id,name);
		return aksesuar;
	}

	public String getAsString(FacesContext context, UIComponent component, Object obj) {
		aksesuar = (Aksesuar) obj;
		String label = aksesuar.getName();
		return label;
		//return value.toString();
		 
	}



}
