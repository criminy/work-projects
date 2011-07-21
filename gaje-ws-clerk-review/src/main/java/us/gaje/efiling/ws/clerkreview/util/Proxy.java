package us.gaje.efiling.ws.clerkreview.util;

/**
 * The class which provides the chaining logic
 * for a stack of chainable interfaces. 
 * 
 * @author artripa
 *
 * @param <T>
 */
public class Proxy<T> {
	T original;
	
	T bottomOfChain;
	
	public Proxy<T> proxy(T t)
	{
		if(bottomOfChain != null)
		{			
			((Chainable<T>)bottomOfChain).setNext(t);			
		}else{
			original = t;
		}
		bottomOfChain = t;
					
		return this;
	}
	
	public T impl(T t)
	{
		((Chainable<T>)bottomOfChain).setNext(t);
		return original;		
	}

}
