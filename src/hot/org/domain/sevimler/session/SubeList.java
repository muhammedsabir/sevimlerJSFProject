package org.domain.sevimler.session;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import org.domain.sevimler.entity.Sube;

@Name("subeList")
public class SubeList extends EntityQuery<Sube>
{
    public SubeList()
    {
        setEjbql("select sube from Sube sube");
    }
}
