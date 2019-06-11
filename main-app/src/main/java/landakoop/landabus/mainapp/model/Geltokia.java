package landakoop.landabus.mainapp.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="geltokia")
public class Geltokia {
	@JsonProperty("id")
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="geltokiaID")
	long id;

	@Column(name="izena")
	String izena;
	
	@Column(name="x")
	Double x;
	
	@Column(name="y")
	Double y;
	
	@OneToMany(mappedBy = "geltokia", cascade=CascadeType.ALL)
	@JsonIgnore
	Set<AutobusGeldialdia> autobusGeldialdia;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getIzena() {
		return izena;
	}

	public void setIzena(String izena) {
		this.izena = izena;
	}
	
	public Set<AutobusGeldialdia> getAutobusGeldialdia() {
		return autobusGeldialdia;
	}

	public void setAutobusGeldialdia(Set<AutobusGeldialdia> autobusGeldialdia) {
		this.autobusGeldialdia = autobusGeldialdia;
	}

	public Double getX() {
		return x;
	}

	public void setX(Double x) {
		this.x = x;
	}

	public Double getY() {
		return y;
	}

	public void setY(Double y) {
		this.y = y;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
		if(!(obj instanceof Geltokia)) return false;
		Geltokia g = (Geltokia) obj;
		
		return (g.getId() == id);
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return Long.valueOf(id).hashCode();
	}
	
}
