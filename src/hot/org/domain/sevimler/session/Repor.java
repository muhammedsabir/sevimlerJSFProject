package org.domain.sevimler.session;

import javax.ejb.Local;

@Local
public interface Repor
{
    public void repor();
    public String getValue();
    public void setValue(String value);
    public void destroy();

    // add additional interface methods here

}
