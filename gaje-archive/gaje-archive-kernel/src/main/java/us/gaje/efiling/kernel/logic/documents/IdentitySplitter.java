package us.gaje.efiling.kernel.logic.documents;

import java.util.List;

import org.apache.camel.Body;
import org.springframework.stereotype.Component;

@Component
public class IdentitySplitter<T> {

	public List<T> split(@Body List<T> l)
	{
		return l;
	}
	
}
