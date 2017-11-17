package org.domain.sevimler.session;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import org.domain.sevimler.entity.UrunGiris;

@Name("urunGirisList")
public class UrunGirisList extends EntityQuery<UrunGiris>
{
    public UrunGirisList()
    {
        setEjbql("select urunGiris from UrunGiris urunGiris order by urunGiris.id DESC");
    }
}
