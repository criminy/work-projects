package us.gaje.efiling.ws.clerkreview.util;

/**
 * Builder class for creating Proxy objects.
 * 
 * @author artripa
 *
 * @param <T>
 */

// might be useless.
public class ProxyBuilder {

	/**
	 * Start building a proxy.
	 * 
	 * @return The proxy object.
	 */
	public <T> Proxy<T> build(Class<T> clazz)
	{
		Proxy<T> proxy = new Proxy<T>();
		return proxy;				
	}
	
}
