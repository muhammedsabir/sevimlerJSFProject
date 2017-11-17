package org.domain.sevimler.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import org.hibernate.validator.Email;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;
import org.jboss.seam.annotations.Name;



@Entity
@Name("musteriGiris")
public class MusteriGiris implements Serializable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// seam-gen attributes (you should probably edit these)
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Version
    private Integer version;
    
    @Length(max = 25)
    @Column(name="musteriAd",nullable=false)
    private String name;
    
    @Column(name="adres",nullable=true)
    private String adres;
    
   
    @Column(name="cihazSeriNo",nullable=true,length=25)
	private Long cihazSeriNo;
	
    
    @Column(name="emailAdres",nullable=false,updatable=true)
    @NotNull
    @Email
    private String emailAdres;
	
    @Column(name="telefon",nullable=true)
    private String telefon;
    
	@ManyToOne
	private Marka marka;
		
	@ManyToOne	
	private Model model;
	
   /* @ManyToMany
    @JoinTable(name = "musteri_aksesuar",
    		joinColumns = {
    		@JoinColumn(name="musteriId") 
    		},
    		inverseJoinColumns = {
    		@JoinColumn(name="aksesuarId")
    		}
    		)
	private List<Aksesuar> aksesuar;*/
	
	@Column(name="ariza",nullable=false)
	private String ariza;
	
	@Column(name="arizaTespit",nullable=true)
	private String arizaTespit;
	
	@Column(name="aciklama",nullable=true)
	private String aciklama;
	
	@Column(name="ucret",nullable=true)
	private Integer ucret;
	
	@Column(name="kayitTarihi",nullable=false)
	private Date kayitTarihi;
	
	@Column(name="cikisTarihi",nullable=true)
	private Date cikisTarihi;
	
	@Column(name="onay",nullable=true)
	private String onay;

	@Column(name="fizikselDurumu",nullable=false)
	private String fizikselDurumu;
	
	@ManyToMany
	private List<Aksesuar> aksesuarType = new ArrayList<Aksesuar>();	

	public List<Aksesuar> getAksesuarType() {
		return aksesuarType;
	}

	public void setAksesuarType(List<Aksesuar> aksesuarType) {
		this.aksesuarType = aksesuarType;
	}

/*	@Column(name="aksesuarAciklama",nullable=true)
	private String aksesuarAciklama;

   public String getAksesuarAciklama() {
		return aksesuarAciklama;
	}

	public void setAksesuarAciklama(String aksesuarAciklama) {
		this.aksesuarAciklama = aksesuarAciklama;
	}*/

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

    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public String getAdres() {
		return adres;
	}

	public void setAdres(String adres) {
		this.adres = adres;
	}

	public Long getCihazSeriNo() {
		return cihazSeriNo;
	}

	public void setCihazSeriNo(Long cihazSeriNo) {
		this.cihazSeriNo = cihazSeriNo;
	}

	public String getEmailAdres() {
		return emailAdres;
	}

	public void setEmailAdres(String emailAdres) {
		this.emailAdres = emailAdres;
	}


	public String getTelefon() {
		return telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

	public Marka getMarka() {
		return marka;
	}

	public void setMarka(Marka marka) {
		this.marka = marka;
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public String getAriza() {
		return ariza;
	}

	public void setAriza(String ariza) {
		this.ariza = ariza;
	}

	public String getArizaTespit() {
		return arizaTespit;
	}

	public void setArizaTespit(String arizaTespit) {
		this.arizaTespit = arizaTespit;
	}

	public String getAciklama() {
		return aciklama;
	}

	public void setAciklama(String aciklama) {
		this.aciklama = aciklama;
	}

	public Integer getUcret() {
		return ucret;
	}

	public void setUcret(Integer ucret) {
		this.ucret = ucret;
	}

	public Date getKayitTarihi() {
		return kayitTarihi;
	}

	public void setKayitTarihi(Date kayitTarihi) {
		this.kayitTarihi = kayitTarihi;
	}

	public Date getCikisTarihi() {
		return cikisTarihi;
	}

	public void setCikisTarihi(Date cikisTarihi) {
		this.cikisTarihi = cikisTarihi;
	}

	public String getOnay() {
		return onay;
	}

	public void setOnay(String onay) {
		this.onay = onay;
	}

	public String getFizikselDurumu() {
		return fizikselDurumu;
	}

	public void setFizikselDurumu(String fizikselDurumu) {
		this.fizikselDurumu = fizikselDurumu;
	}
/*
	public List<Aksesuar> getAksesuar() {
		return aksesuar;
	}

	public void setAksesuar(List<Aksesuar> aksesuar) {
		this.aksesuar = aksesuar;
	}*/

	



}
