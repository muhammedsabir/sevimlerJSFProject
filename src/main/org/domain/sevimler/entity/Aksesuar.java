package org.domain.sevimler.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Version;
import org.hibernate.validator.Length;

@Entity
public class Aksesuar implements Serializable
{
    /**
	 * msabir
	 */
	private static final long serialVersionUID = 1L;
	// seam-gen attributes (you should probably edit these)
    private Long id;
    private Integer version;
    private String name;
    
    
    //@ManyToMany(mappedBy="urunGiris")    
    //private List<UrunGiris> urunGiris;
    public Aksesuar(){
	   super();
	   //musteriGiris = new ArrayList<MusteriGiris>();
   }

    public Aksesuar(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}     
/*
	public List<UrunGiris> getUrunGiris() {
		return urunGiris;
	}

	public void setUrunGiris(List<UrunGiris> urunGiris) {
		this.urunGiris = urunGiris;
	}
*/
	@Id @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Version
    public Integer getVersion() {
        return version;
    }

    private void setVersion(Integer version) {
        this.version = version;
    }

    @Length(max = 20)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
