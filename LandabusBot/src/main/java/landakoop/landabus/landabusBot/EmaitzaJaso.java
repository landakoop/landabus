package landakoop.landabus.landabusBot;

import java.util.List;

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
	private void onartuakPostFromJson(@RequestBody List<Emaitza> onartuak) {
		int i = 0;
		for(Long chatId: onartuak.get(i).getListId()) {
			bot.notifikatuOnarpena(chatId, onartuak.get(i).getLinea());
			i++;
		}
	}

	@PostMapping(path = "ezeztatuakPostFromJson", consumes = "application/json")
	private void ezeztatuakPostFromJson(@RequestBody List<Emaitza> ezeztatuak) {
		int i = 0;
		for(Long chatId: ezeztatuak.get(i).getListId()) {
			bot.notifikatuEzezkoa(chatId);
			i++;
		}
	}

}
