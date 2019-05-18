package landakoop.landabus.mainapp.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import landakoop.landabus.mainapp.model.Geltokia;

/// Geltoki osoa beharrean bere IDa bakarrik nahi dugunean erabiltzeko
public class GeltokiaSimpleSerializer extends JsonSerializer<Geltokia> {

	@Override
	public void serialize(Geltokia geltokia, JsonGenerator jsonGenerator, SerializerProvider arg2) throws IOException {
		jsonGenerator.writeNumber(geltokia.getId());
	}

}
