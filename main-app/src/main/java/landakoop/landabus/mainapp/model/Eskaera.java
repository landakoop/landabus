package landakoop.landabus.mainapp.model;

import java.util.Date;

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
import javax.persistence.Transient;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import landakoop.landabus.mainapp.json.ErabiltzaileaSimpleSerializer;
import landakoop.landabus.mainapp.json.GeltokiaSimpleSerializer;

@Entity
@Table(name="eskaera")
public class Eskaera {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="eskaeraID")
	long id;
	
	@Transient
	long chatId;

	
	@ManyToOne(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@JoinColumn(name="bidaiariaID")
	@JsonSerialize(using = ErabiltzaileaSimpleSerializer.class)
	Erabiltzailea erabiltzailea;	

	@ManyToOne(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@JoinColumn(name="geltokiaa")
	@JsonSerialize(using = GeltokiaSimpleSerializer.class)
	Geltokia irteera;
	
	@ManyToOne(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@JoinColumn(name="geltokiab")
	@JsonSerialize(using = GeltokiaSimpleSerializer.class)
	Geltokia helmuga;
	
	@Column(name="onartua")
	boolean onartua;
	
	@Column(name="irteeraordua")
	int irteeraOrdua;
	
	@Column(name="data")
	Date data;
	
	@Column(name="helmugaordua")
	int helmugaOrdua;
	
	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Erabiltzailea getErabiltzailea() {
		return erabiltzailea;
	}

	public void setErabiltzailea(Erabiltzailea erabiltzailea) {
		this.erabiltzailea = erabiltzailea;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setChatId(long chatId) {
		this.chatId = chatId;
	}
	
	public long getChatId() {
		return chatId;
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
