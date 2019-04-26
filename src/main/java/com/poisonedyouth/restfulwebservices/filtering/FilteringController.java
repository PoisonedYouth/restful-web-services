package com.poisonedyouth.restfulwebservices.filtering;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/filtering")
public class FilteringController {

	@GetMapping("")
	public MappingJacksonValue getData() {
		Data data = new Data(1, "Max Mustermann", "passw0rd");
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(data);
		SimpleFilterProvider filterProvider = new SimpleFilterProvider();
		filterProvider.addFilter("DataFilter", SimpleBeanPropertyFilter.filterOutAllExcept("id", "name"));
		mappingJacksonValue.setFilters(filterProvider);
		return mappingJacksonValue;
	}

	@GetMapping("/list")
	public List<Data> getListData() {
		return Arrays.asList(new Data(1, "Max Mustermann", "passw0rd"),
				new Data(2, "John Doe", "secret"),
				new Data(3, "Tank Top", "very secure"));
	}
}
