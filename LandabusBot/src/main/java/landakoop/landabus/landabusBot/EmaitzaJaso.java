package landakoop.landabus.landabusBot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/telegram/emaitza")
public class EmaitzaJaso {
	@Autowired
	LandabusBot bot;
	
	public EmaitzaJaso() {}

	@PostMapping(path = "onartuakPostFromJson", consumes = "application/json")
	private void onartuakPostFromJson(@RequestBody Emaitza onartuak) {
		System.out.println("Posta egin da");
		for(Long chatId: onartuak.getListId()) {
			bot.notifikatuOnarpena(chatId, onartuak.getLinea());
		}
	}

	@PostMapping(path = "ezeztatuakPostFromJson", consumes = "application/json")
	private void ezeztatuakPostFromJson(@RequestBody Emaitza ezeztatuak) {

		for(Long chatId: ezeztatuak.getListId()) {
			bot.notifikatuEzezkoa(chatId);
		}
	}

}
