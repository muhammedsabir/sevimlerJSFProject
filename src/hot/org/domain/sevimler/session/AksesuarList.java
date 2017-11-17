package org.domain.sevimler.session;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import org.domain.sevimler.entity.Aksesuar;

@Name("aksesuarList")
public class AksesuarList extends EntityQuery<Aksesuar>
{
    public AksesuarList()
    {
        setEjbql("select aksesuar from Aksesuar aksesuar");
    }
}
