package org.domain.sevimler.session;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.web.RequestParameter;
import org.jboss.seam.framework.EntityHome;

import org.domain.sevimler.entity.Model;

@Name("modelHome")
public class ModelHome extends EntityHome<Model>
{
    @RequestParameter Long modelId;

    @Override
    public Object getId()
    {
        if (modelId == null)
        {
            return super.getId();
        }
        else
        {
            return modelId;
        }
    }

    @Override @Begin
    public void create() {
        super.create();
    }

}
