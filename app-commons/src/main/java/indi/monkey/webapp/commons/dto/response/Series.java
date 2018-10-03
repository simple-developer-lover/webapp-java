package indi.monkey.webapp.commons.dto.response;

import lombok.Data;

@Data
public class Series {
	private String name;
	private String type;
	private Integer[] data;
}
