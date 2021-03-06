package landakoop.landabus.mainapp.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="ibilbidea")
public class Ibilbidea {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ibilbideaID")	
	int id;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="ordutegiaID")
	Ordutegia ordutegia;
	
	@Column(name="autobusaID")
	int autobusaID;
	
	@JsonIgnore
	@Column(name="predikzioa")
	Integer predikzioa;
	
	@Column(name="eguraldia")
	String eguraldia;
	
	@JsonIgnore
	@OneToMany(mappedBy = "ibilbidea", cascade=CascadeType.ALL)
	Set<AutobusGeldialdia> autobusGeldialdia;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Ordutegia getOrdutegia() {
		return ordutegia;
	}

	public void setOrdutegia(Ordutegia ordutegia) {
		this.ordutegia = ordutegia;
	}

	public int getAutobusaID() {
		return autobusaID;
	}

	public void setAutobusaID(int autobusaID) {
		this.autobusaID = autobusaID;
	}

	public int getPredikzioa() {
		return predikzioa;
	}

	public void setPredikzioa(int predikzioa) {
		this.predikzioa = predikzioa;
	}
	
	public Set<AutobusGeldialdia> getAutobusGeldialdia() {
		return autobusGeldialdia;
	}

	public void setAutobusGeldialdia(Set<AutobusGeldialdia> autobusGeldialdia) {
		this.autobusGeldialdia = autobusGeldialdia;
	}

	public String getEguraldia() {
		return eguraldia;
	}

	public void setEguraldia(String eguraldia) {
		this.eguraldia = eguraldia;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
		if(!(obj instanceof Geltokia)) return false;
		Ibilbidea i = (Ibilbidea) obj;
		
		return (i.getId() == id);
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return Long.valueOf(id).hashCode();
	}
	
}
