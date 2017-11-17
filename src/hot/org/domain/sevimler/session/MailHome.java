package org.domain.sevimler.session;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.web.RequestParameter;
import org.jboss.seam.framework.EntityHome;

import org.domain.sevimler.entity.Mail;

@Name("mailHome")
public class MailHome extends EntityHome<Mail>
{
    @RequestParameter Long mailId;

    @Override
    public Object getId()
    {
        if (mailId == null)
        {
            return super.getId();
        }
        else
        {
            return mailId;
        }
    }

    @Override @Begin
    public void create() {
        super.create();
    }

}
