package org.domain.sevimler.session;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import org.domain.sevimler.entity.Role;

@Name("roleList")
public class RoleList extends EntityQuery<Role>
{
    public RoleList()
    {
        setEjbql("select role from Role role");
    }
}
