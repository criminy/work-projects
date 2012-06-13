package us.gaje.efiling.dao;

public class Pair<K,V> {

	K k;
	V v;
	
	public K getK() {
		return k;
	}
	
	public V getV() {
		return v;
	}
	
	public static <K,V> Pair<K,V> pair(K k,V v)
	{
		Pair<K,V> p = new Pair<K, V>();
		p.v = v;
		p.k = k;
		return p;
	}
}
