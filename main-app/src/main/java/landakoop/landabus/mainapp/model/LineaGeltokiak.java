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
import javax.validation.constraints.NotNull;

import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@Entity
@Table(name="linea_geltokiak")
public class LineaGeltokiak {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="lineaGeltokiakID")
	long id;
	
	@ManyToOne(cascade=CascadeType.MERGE)
    @JoinColumn(name = "lineaID")
	@NotNull
    Linea linea;
	
	@ManyToOne(cascade=CascadeType.MERGE)
    @JoinColumn(name = "geltokiaID")
	@NotNull
    Geltokia geltokia;
	
	@NotNull
	int posizioa;

	public int getPosizioa() {
		return posizioa;
	}

	public void setPosizioa(int posizioa) {
		this.posizioa = posizioa;
	}

	public Linea getLinea() {
		return linea;
	}

	public void setLinea(Linea linea) {
		this.linea = linea;
	}

	public Geltokia getGeltokia() {
		return geltokia;
	}

	public void setGeltokia(Geltokia geltokia) {
		this.geltokia = geltokia;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((geltokia == null) ? 0 : geltokia.hashCode());
		result = prime * result + ((linea == null) ? 0 : linea.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LineaGeltokiak other = (LineaGeltokiak) obj;
		if (geltokia == null) {
			if (other.geltokia != null)
				return false;
		} else if (!geltokia.equals(other.geltokia))
			return false;
		if (linea == null) {
			if (other.linea != null)
				return false;
		} else if (!linea.equals(other.linea))
			return false;
		return true;
	}
}
