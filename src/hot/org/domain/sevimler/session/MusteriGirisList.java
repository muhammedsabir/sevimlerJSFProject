package org.domain.sevimler.session;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import org.domain.sevimler.entity.MusteriGiris;

@Name("musteriGirisList")
public class MusteriGirisList extends EntityQuery<MusteriGiris>
{
    public MusteriGirisList()
    {
        setEjbql("select musteriGiris from MusteriGiris musteriGiris order by musteriGiris.id DESC");
    }
}
