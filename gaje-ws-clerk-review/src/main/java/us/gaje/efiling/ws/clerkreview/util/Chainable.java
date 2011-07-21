package us.gaje.efiling.ws.clerkreview.util;

/**
 * Interface which provides the ability to chain classes together, to make
 * a stack of wrapping proxy classes.
 * 
 * @author artripa
 *
 * @param <T> The interface we are chaining.
 */
public interface Chainable<T> {

	public void setNext(T t);
	
}
