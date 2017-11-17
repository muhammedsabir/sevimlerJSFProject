package org.domain.sevimler.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Version;
import org.hibernate.validator.Length;

@Entity
public class Model implements Serializable {
	// seam-gen attributes (you should probably edit these)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Version
	private Integer version;
	private String name;

	

	public Model() {
		super();
	}

	public Model(Long id, String name, Marka marka) {
		super();
		this.id = id;
		this.name = name;
		this.marka = marka;
	}

	@ManyToOne
	private Marka marka;


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



	public void setMarka(Marka marka) {
		this.marka = marka;
	}

	public Marka getMarka() {
		return marka;
	}

}
