package landakoop.landabus.mainapp.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import landakoop.landabus.mainapp.model.Ordutegia;
import landakoop.landabus.mainapp.model.rest.OrdutegiaRest;

public interface OrdutegiaDao extends CrudRepository<Ordutegia,Long> {
	@Query(value="select count(*)"
			+ "from ordutegia "
			+ "where (((irteeraOrdua >= ?1) and (irteeraOrdua <= ?2)) or "
			+ "((helmugaOrdua >= ?1) and (helmugaOrdua <= ?2)) or "
			+ "((irteeraOrdua <= ?1) and (helmugaOrdua >= ?2)) or "
			+ "((irteeraOrdua >= ?1) and (helmugaOrdua <= ?2)))",nativeQuery=true)	
	int autobusOkupatuak(int irteeraOrdua, int helmugaOrdua);
	
	@Query(value="SELECT o.ordutegiaID, o.irteeraOrdua, o.helmugaOrdua, data, finkoa "
			+ "from ordutegia as o join linea as l on o.lineaID = l.lineaID "
			+ "where o.lineaID=?1", nativeQuery=true)
	List<OrdutegiaRest> getOrdutegiak(long lineaID);
}
