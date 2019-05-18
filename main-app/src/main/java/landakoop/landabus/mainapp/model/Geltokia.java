package landakoop.landabus.mainapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
}
