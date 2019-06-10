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
}
