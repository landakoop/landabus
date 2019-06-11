package landakoop.landabus.mainapp.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="ibilbidea_geltokia_predikzioa")
public class Predikzioa {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="lineaGeltokiakID")
	long id;
	
	@ManyToOne(cascade=CascadeType.MERGE)
    @JoinColumn(name = "ibilbideaID")
	@NotNull
    Ibilbidea ibilbidea;
	
	@ManyToOne(cascade=CascadeType.MERGE)
    @JoinColumn(name = "geltokiaID")
	@NotNull
    Geltokia geltokia;
	
	@Column(name="igo")
	int igo;
	
	@Column(name="jaitsi")
	int jaitsi;
	
	@Temporal(TemporalType.DATE)
	@Column(name="data")
	Date data;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Ibilbidea getIbilbidea() {
		return ibilbidea;
	}

	public void setIbilbidea(Ibilbidea ibilbidea) {
		this.ibilbidea = ibilbidea;
	}

	public Geltokia getGeltokia() {
		return geltokia;
	}

	public void setGeltokia(Geltokia geltokia) {
		this.geltokia = geltokia;
	}

	public int getIgo() {
		return igo;
	}

	public void setIgo(int igo) {
		this.igo = igo;
	}

	public int getJaitsi() {
		return jaitsi;
	}

	public void setJaitsi(int jaitsi) {
		this.jaitsi = jaitsi;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}
}
