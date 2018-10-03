package indi.monkey.webapp.commons.dto.response;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EchartsDto {
	private String title;
	private String[] legend;
	private String[] xAxis;
	private List<Series> series;
}
