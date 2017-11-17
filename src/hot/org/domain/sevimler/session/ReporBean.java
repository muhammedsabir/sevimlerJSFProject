package org.domain.sevimler.session;

//import javax.ejb.Remove;
//import javax.ejb.Stateful;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.log.Log;
import org.jboss.seam.international.StatusMessages;
import org.hibernate.validator.Length;

//@Stateful
@Name("Repor")
public class ReporBean implements Repor
{
    @Logger private Log log;

    @In StatusMessages statusMessages;

    private String value;

    public void repor()
    {
        // implement your business logic here
        log.info("Repor.repor() action called with: #{Repor.value}");
        //statusMessages.add("repor #{Repor.value}");
    }

    // add additional action methods

    @Length(max = 10)
    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    //@Remove
    public void destroy() {}

}
