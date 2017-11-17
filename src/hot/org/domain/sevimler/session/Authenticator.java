package org.domain.sevimler.session;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.domain.sevimler.entity.Kullanici;
import org.domain.sevimler.entity.Role;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.log.Log;
import org.jboss.seam.security.Credentials;
import org.jboss.seam.security.Identity;
import org.jboss.xb.binding.metadata.AddMethodMetaData;

@Name("authenticator")
public class Authenticator
{
    @Logger private Log log;

    @In Identity identity;
    @In Credentials credentials;
    @In EntityManager entityManager;
    //private List<Kullanici> user;
    public boolean authenticate()
    {
    	boolean kontrol=false;
    	//user = new ArrayList<Kullanici>();
        log.info("authenticating {0}", credentials.getUsername());
        //String sql = "SELECT k FROM Kullanici WHERE k.name=: credentials.username AND k.pass=: credentials.password";
        Kullanici user = (Kullanici) entityManager.createQuery("from Kullanici where name =:username and pass =:password")
        .setParameter("username",credentials.getUsername())
        .setParameter("password", Integer.parseInt(credentials.getPassword()))
        .getSingleResult();
        //user = query.getResultList();
        if (!user.equals(null))
        {
        	//FacesMessages.instance().add("Kullanici "+user.getName()+" "+user.getPass()+" "+user.getRole().getName());
        	if (user.getRole() != null) {
        		//for (Role mr : user.getRole())
        		identity.addRole(user.getRole().getName());
        		}
            kontrol = true;
        }else{
        	FacesMessages.instance().add("Kullanici Adi veya Sifreyi Kontrol edip tekrar deneyiniz!...");
        }
        return kontrol;
    }

}
