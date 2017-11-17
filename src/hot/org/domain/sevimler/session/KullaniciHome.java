package org.domain.sevimler.session;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.web.RequestParameter;
import org.jboss.seam.framework.EntityHome;

import org.domain.sevimler.entity.Kullanici;

@Name("kullaniciHome")
public class KullaniciHome extends EntityHome<Kullanici>
{
    @RequestParameter Long kullaniciId;

    @Override
    public Object getId()
    {
        if (kullaniciId == null)
        {
            return super.getId();
        }
        else
        {
            return kullaniciId;
        }
    }

    @Override @Begin
    public void create() {
        super.create();
    }

}
