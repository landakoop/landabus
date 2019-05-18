package landakoop.landabus.mainapp.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="distantzia")
public class Distantzia {
	
	@Id
	@JsonIgnore
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="distantziaID")
	long id;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="geltokiaa")
	Geltokia irteera;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="geltokiab")	
	Geltokia helmuga;
	
	@Column(name="denbora")
	int denbora;

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

	public int getDenbora() {
		return denbora;
	}

	public void setDenbora(int denbora) {
		this.denbora = denbora;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
}
