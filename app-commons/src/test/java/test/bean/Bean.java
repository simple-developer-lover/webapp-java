package test.bean;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class Bean {
	@NotNull
	private String name;
	@Nullable
	private int age;
}
