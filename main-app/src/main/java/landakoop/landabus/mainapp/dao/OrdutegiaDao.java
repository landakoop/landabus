package landakoop.landabus.mainapp.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import landakoop.landabus.mainapp.model.Ordutegia;

public interface OrdutegiaDao extends CrudRepository<Ordutegia,Long> {
	@Query(value="select count(*)"
			+ "from ordutegia "
			+ "where (irteeraOrdua <= ?1) and (helmugaOrdua <= ?2) ",nativeQuery=true)	
	int autobusOkupatuak(int irteeraOrdua, int helmugaOrdua);
}
