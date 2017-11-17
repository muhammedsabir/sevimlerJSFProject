package org.domain.sevimler.session;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.web.RequestParameter;
import org.jboss.seam.framework.EntityHome;

import org.domain.sevimler.entity.Aksesuar;

@Name("aksesuarHome")
public class AksesuarHome extends EntityHome<Aksesuar>
{
    @RequestParameter Long aksesuarId;

    @Override
    public Object getId()
    {
        if (aksesuarId == null)
        {
            return super.getId();
        }
        else
        {
            return aksesuarId;
        }
    }

    @Override @Begin
    public void create() {
        super.create();
    }

}
