package landakoop.landabus.mainapp.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="eskaera")
public class Eskaera {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="eskaeraID")
	long id;
	
	@ManyToOne(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@JoinColumn(name="geltokiaa")
	Geltokia irteera;
	
	@ManyToOne(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@JoinColumn(name="geltokiab")
	Geltokia helmuga;
	
	@Column(name="onartua")
	boolean onartua;
	
	@Column(name="irteeraordua")
	int irteeraOrdua;
	
	@Column(name="helmugaordua")
	int helmugaOrdua;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Geltokia getIrteera() {
		return irteera;
	}

	public void setIrteera(Geltokia irteera) {
		this.irteera = irteera;
	}

	public Geltokia getHelmuga() {
		return helmuga;
	}

	public void setHelmuga(Geltokia helmuga) {
		this.helmuga = helmuga;
	}

	public boolean isOnartua() {
		return onartua;
	}

	public void setOnartua(boolean onartua) {
		this.onartua = onartua;
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
	
}
