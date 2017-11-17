package org.domain.sevimler.session;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import org.domain.sevimler.entity.Kullanici;

@Name("kullaniciList")
public class KullaniciList extends EntityQuery<Kullanici>
{
    public KullaniciList()
    {
        setEjbql("select kullanici from Kullanici kullanici");
    }
}
