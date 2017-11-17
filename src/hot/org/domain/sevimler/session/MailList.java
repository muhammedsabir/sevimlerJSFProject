package org.domain.sevimler.session;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import org.domain.sevimler.entity.Mail;

@Name("mailList")
public class MailList extends EntityQuery<Mail>
{
    public MailList()
    {
        setEjbql("select mail from Mail mail");
    }
}
