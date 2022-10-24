package com.movieapi.model.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum MovieType {
	FULL("Полнометражный"),
	SHORT("Короткометражный"),
	SERIES("Сериал");
	
	
	private static final List<String> constants = Arrays.stream(values()).map(t -> t.type).collect(Collectors.toList());
	private final String type;
	
	MovieType(String type) {
		this.type = type;
	}
	
	public static List<String> getConstants() {
		return constants;
	}
	
	public static MovieType fromString(String text) {
		for (MovieType typeEnum : MovieType.values()) {
			if (typeEnum.type.equalsIgnoreCase(text))
				return typeEnum;
		}
		return null;
	}
	
	@JsonValue
	public String getType() {
		return type;
	}
}
