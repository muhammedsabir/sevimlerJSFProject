package org.domain.sevimler.session;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.web.RequestParameter;
import org.jboss.seam.framework.EntityHome;

import org.domain.sevimler.entity.Marka;

@Name("markaHome")
public class MarkaHome extends EntityHome<Marka>
{
    @RequestParameter Long markaId;

    @Override
    public Object getId()
    {
        if (markaId == null)
        {
            return super.getId();
        }
        else
        {
            return markaId;
        }
    }

    @Override @Begin
    public void create() {
        super.create();
    }

}
