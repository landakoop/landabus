package landakoop.landabus.mainapp.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import landakoop.landabus.mainapp.model.Erabiltzailea;

public interface ErabiltzaileaDao extends CrudRepository<Erabiltzailea,Long>{
	List<Erabiltzailea> findAll();

	Erabiltzailea findByTelegramID(String chatId);
}
