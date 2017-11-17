package org.domain.sevimler.session;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.web.RequestParameter;
import org.jboss.seam.framework.EntityHome;

import org.domain.sevimler.entity.Magaza;

@Name("magazaHome")
public class MagazaHome extends EntityHome<Magaza>
{
    @RequestParameter Long magazaId;

    @Override
    public Object getId()
    {
        if (magazaId == null)
        {
            return super.getId();
        }
        else
        {
            return magazaId;
        }
    }

    @Override @Begin
    public void create() {
        super.create();
    }

}
