package org.domain.sevimler.session;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import org.domain.sevimler.entity.Model;

@Name("modelList")
public class ModelList extends EntityQuery<Model>
{
    public ModelList()
    {
        setEjbql("select model from Model model");
    }
}
