package org.domain.sevimler.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.persistence.Version;
import org.jboss.seam.annotations.Name;



@Entity
@Name("urunGiris")
public class UrunGiris implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// seam-gen attributes (you should probably edit these)
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@Version
	private Integer version;
	private String sr;
	private String seri;
	private String mkodu;
	private String tlf;
	private @Transient boolean select = false;
	
	@Column(name="durumKod",nullable=true)
	private int durumKod;
	//private Integer aksesuarlar;

	@ManyToOne
	private Magaza magaza;
	
	@ManyToOne
	private Marka marka;
	
	@ManyToOne
	private Sube sube;
	/*
	@OneToMany
	private List<Model> modelType = new ArrayList<Model>();*/

	@ManyToOne
	private Model model;
	
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

	/*
	@ManyToMany
	private List<Aksesuar> aksesuarType;
 
	public UrunGiris(){
		aksesuarType = new ArrayList<Aksesuar>();
	}

	public List<Aksesuar> getAksesuarType() {
		return aksesuarType;
	}

	public void setAksesuarType(List<Aksesuar> aksesuarType) {
		this.aksesuarType = aksesuarType;
	}
*/
	
	
	public Long getId() {
		return id;
	}

	public int getDurumKod() {
		return durumKod;
	}

	public void setDurumKod(int durumKod) {
		this.durumKod = durumKod;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getSr() {
		return sr;
	}

	public void setSr(String sr) {
		this.sr = sr;
	}

	public String getSeri() {
		return seri;
	}

	public void setSeri(String seri) {
		this.seri = seri;
	}

	public String getMkodu() {
		return mkodu;
	}

	public void setMkodu(String mkodu) {
		this.mkodu = mkodu;
	}

	public Magaza getMagaza() {
		return magaza;
	}

	public void setMagaza(Magaza magaza) {
		this.magaza = magaza;
	}

	public Marka getMarka() {
		if(this.marka!=null){
			//System.out.println(marka.getId()+" "+"marka :"+marka.getName()+" model :"+this.getModel().getName()+" id :"+this.getModel().getId());
			//this.setModel(new Model())
		}
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

	public void setKayitTarihi(Date kayitTarihi) {
		this.kayitTarihi = kayitTarihi;
	}

	public Date getKayitTarihi() {
		return kayitTarihi;
	}

	public void setOnay(String onay) {
		this.onay = onay;
	}

	public String getOnay() {
		return onay;
	}

	public void setFizikselDurumu(String fizikselDurumu) {
		this.fizikselDurumu = fizikselDurumu;
	}

	public String getFizikselDurumu() {
		return fizikselDurumu;
	}




	public void setCikisTarihi(Date cikisTarihi) {
		this.cikisTarihi = cikisTarihi;
	}

	public Date getCikisTarihi() {
		return cikisTarihi;
	}
/*
	public void setModelType(List<Model> modelType) {
		this.modelType = modelType;
	}

	public List<Model> getModelType() {
		return modelType;
	}*/

	public void setSube(Sube sube) {
		this.sube = sube;
	}

	public Sube getSube() {
		return sube;
	}
	public String getTlf() {
		return tlf;
	}

	public void setTlf(String tlf) {
		this.tlf = tlf;
	}

	public boolean isSelect() {
		return select;
	}

	public void setSelect(boolean select) {
		this.select = select;
	}
   
}
