package org.domain.sevimler.session;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.web.RequestParameter;
import org.jboss.seam.framework.EntityHome;

import org.domain.sevimler.entity.Sube;

@Name("subeHome")
public class SubeHome extends EntityHome<Sube>
{
    @RequestParameter Long subeId;

    @Override
    public Object getId()
    {
        if (subeId == null)
        {
            return super.getId();
        }
        else
        {
            return subeId;
        }
    }

    @Override @Begin
    public void create() {
        super.create();
    }

}
