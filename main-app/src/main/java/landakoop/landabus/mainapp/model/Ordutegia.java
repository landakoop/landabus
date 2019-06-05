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

@Entity
@Table(name="ordutegia")
public class Ordutegia {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ordutegiaID")	
	int id;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="lineaID")
	Linea linea;
	
	@Column(name="irteeraOrdua")
	int irteeraOrdua;
	
	@Column(name="helmugaOrdua")
	int helmugaOrdua;
	
	@Column(name="finkoa")
	boolean finkoa;
	
	@Temporal(TemporalType.DATE)
	@Column(name="timestamp")
	Date data;

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

	public int getIrteeraOrdua() {
		return irteeraOrdua;
	}

	public void setIrteeraOrdua(int irteeraOrdua) {
		this.irteeraOrdua = irteeraOrdua;
	}

	public int getHelmugaOrdua() {
		return helmugaOrdua;
	}

	public void setHelmugaOrdua(int helmugaOrdua) {
		this.helmugaOrdua = helmugaOrdua;
	}

	public boolean isFinkoa() {
		return finkoa;
	}

	public void setFinkoa(boolean finkoa) {
		this.finkoa = finkoa;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}
}
