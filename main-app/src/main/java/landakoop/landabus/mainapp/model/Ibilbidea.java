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

@Entity
@Table(name="ibilbidea")
public class Ibilbidea {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ibilbideaID")	
	int id;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="lineaID")
	Linea linea;
	
	@Column(name="autobusaID")
	int autobusaID;
	
	@Column(name="predikzioa")
	int predikzioa;
	
	@OneToMany(mappedBy = "ibilbidea", cascade=CascadeType.ALL)
	Set<AutobusGeldialdia> autobusGeldialdia;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Linea getLinea() {
		return linea;
	}

	public void setLinea(Linea linea) {
		this.linea = linea;
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
}
