package org.domain.sevimler.session;

import javax.persistence.EntityManager;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
@Name("sevkiyat")
@Scope(ScopeType.EVENT)
public class Sevkiyat {
	
	@In EntityManager entityManager;

}
