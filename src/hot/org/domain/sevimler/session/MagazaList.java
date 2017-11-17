package org.domain.sevimler.session;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import org.domain.sevimler.entity.Magaza;

@Name("magazaList")
public class MagazaList extends EntityQuery<Magaza>
{
    public MagazaList()
    {
        setEjbql("select magaza from Magaza magaza");
    }
}
