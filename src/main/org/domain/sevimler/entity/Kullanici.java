package org.domain.sevimler.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToOne;
import javax.persistence.Version;
import org.hibernate.validator.Length;

@Entity
public class Kullanici implements Serializable
{
    // seam-gen attributes (you should probably edit these)
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    @Version
    private Integer version;
    private String name;
    private int pass;
    @ManyToOne
    private Role role;

    public int getPass() {
		return pass;
	}

	public void setPass(int pass) {
		this.pass = pass;
	}


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    
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

	public void setRole(Role role) {
		this.role = role;
	}

	public Role getRole() {
		return role;
	}

}
