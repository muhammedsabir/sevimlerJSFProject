package org.domain.sevimler.session;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.web.RequestParameter;
import org.jboss.seam.framework.EntityHome;

import org.domain.sevimler.entity.MusteriGiris;

@Name("musteriGirisHome")
public class MusteriGirisHome extends EntityHome<MusteriGiris>
{
    @RequestParameter Long musteriGirisId;

    @Override
    public Object getId()
    {
        if (musteriGirisId == null)
        {
            return super.getId();
        }
        else
        {
            return musteriGirisId;
        }
    }

    @Override @Begin
    public void create() {
        super.create();
    }

}
