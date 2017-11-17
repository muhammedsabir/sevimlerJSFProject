package org.domain.sevimler.session;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import org.domain.sevimler.entity.Marka;

@Name("markaList")
public class MarkaList extends EntityQuery<Marka>
{
    public MarkaList()
    {
        setEjbql("select marka from Marka marka");
    }
}
