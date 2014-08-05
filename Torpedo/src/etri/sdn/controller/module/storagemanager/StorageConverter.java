package etri.sdn.controller.module.storagemanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * This class provides useful static methods to convert 
 * various types of objects to perform I/O operations on persistent storage. 
 * 
 * Since Aug 11, 2013
 * Last Modified Aug 20, 2013
 * @author SaeHyong Park (labry@etri.re.kr)
 * 
 */
public class StorageConverter {
	
	private static final Logger logger = OFMStorageManager.logger;

	private final static String DOT_STRING = "U+FF04";

	
	/**
	 * This method converts a Map<String, object> object into a BasicDBObject, and 
	 * returns it to the caller. It also substitutes DOT (".") to DOT_STRING ("U+FF04"). 
	 *  
	 * @param map
	 * Map object which will be converted into BasicDBObject.
	 * @return BasicDBObject BasicDBobject from a given Map object.
	 */
	@SuppressWarnings("unchecked")
	public static BasicDBObject MapToDBObject(Map<String, Object> map) {
		BasicDBObject dbObject = new BasicDBObject();

		map = (Map<String, Object>) jsonTraverse(map, true);
		dbObject.putAll(map);

		return dbObject;
	}

	/**
	 * This method converts a BasicDBObject object into a Map<String, object> object, and 
	 * returns it to the caller. It also substitutes DOT_STRING("U+FF04") to  DOT("."). 
	 *  
	 * @param dbObject 
	 * BasicDBObject which will be converted into Map object.
	 * @return The converted Map object from a given BasicDBobject object.
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> DBObjectToMap(BasicDBObject dbObject) {
		Map<String, Object> map = (Map<String, Object>) jsonTraverse(dbObject.toMap(), false);

		return map;
	}

	/**
	 * This method converts an object into a JSON object, and 
	 * returns it to the caller. 
	 *  
	 * @param obj
	 * An object turns into JSON object.
	 * @return The converted JSON object.
	 */
	@SuppressWarnings("rawtypes")
	public static Object toJSON(Object obj) {

		if(obj == null) {
			return null;
		} else if(obj instanceof Number) {
			return obj;
		} else if(obj instanceof Boolean) {
			return obj;
		} else if(obj instanceof String) {
			return obj;
		} else if(obj instanceof List) {

			List<Object> list = new LinkedList<Object>();
			List<?> oldList = (List<?>)obj;

			for(Object o: oldList) {
				list.add(toJSON(o));
			}
			return list;

		} else if(obj instanceof DBObject) {

			Map<String, Object> map = new HashMap<String, Object>();
			Map<?, ?> oldMap = ((DBObject) obj).toMap();
			for(Object e: oldMap.entrySet()) {
				Map.Entry entry = (Map.Entry)e;
				map.put(replaceDotUtfToDot((String) entry.getKey()), toJSON(entry.getValue()));
			}

			return map;
		} else {
			logger.error("Unknown type: {}:{}", obj, obj.getClass());
			return obj;
		}
	}

	public static Object toDBObject(Object obj) {
		return null;
	}


	@SuppressWarnings("unchecked")
	private static Object jsonTraverse(Map<String, Object> map, boolean dot) {

		Map<String, Object> result = new HashMap<String, Object>();

		Set<Entry<String, Object>> entrySet = map.entrySet();

		Iterator<Entry<String, Object>> itr = entrySet.iterator();

		while(itr.hasNext()) {
			Entry<String, Object> e = itr.next();
			String r = e.getKey();
			Object o = e.getValue();

			if(!dot && r.equals("_id")) {
				continue;
			}

			String s = null;
			if(dot) {
				s = replaceDotToDotUtf(r);
			} else {
				s = replaceDotUtfToDot(r);
			}

			if(!(o instanceof Map || o instanceof List)) {
				result.put(s, o);

				if(!itr.hasNext()) {
					return result;
				}
			} else if(o instanceof List) {
				result.put(s, listTraverse((List<Object>) o, dot));
			} else {
				result.put(s, jsonTraverse((Map<String, Object>) o, dot));
			}			
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	private static Object listTraverse(List<Object> list, boolean dot) {

		List<Object> result = new ArrayList<Object>();

		Iterator<Object> itr = list.iterator();

		while(itr.hasNext()) {
			Object o = itr.next();

			if(!(o instanceof Map || o instanceof List)) {
				result.add(o);
				if(!itr.hasNext()) {
					return result;
				}
				
			} else if(o instanceof List) {
				result.add(listTraverse((List<Object>) o, dot));
			} else {
				result.add(jsonTraverse((Map<String, Object>) o, dot));
			}			
		}
		return result;
	}

	/*
	@SuppressWarnings("unchecked")
	public static Object mapTraverse(Map<String, Object> map, boolean init, boolean dot) {

		Map<String, Object> result = new HashMap<String, Object>();

		Set<Entry<String, Object>> entrySet = map.entrySet();

		Iterator<Entry<String, Object>> itr = entrySet.iterator();

		while(itr.hasNext()) {
			Entry<String, Object> e = itr.next();
			String r = e.getKey();
			Object o = e.getValue();

			if(!dot && r.equals("_id")) {
				continue;
			}

			String s = null;
			if(dot) {
				s = replaceDotToDotUtf(r);
			} else {
				s = replaceDotUtfToDot(r);
			}

			if(!(o instanceof Map)) {
				result.put(s, o);

				if(!init && !itr.hasNext()) {
					return result;
				}
			} else {
				result.put(s, mapTraverse((Map<String, Object>) o, false, dot));
				if(!init){
					return o;
				}
			}			
		}
		return result;
	}
	*/

	/**
	 * This method replaces DOT_STRING("U+FF04") to  DOT(".").
	 */
	static String replaceDotUtfToDot(String original) {
		return original.replace(DOT_STRING, ".");
	}

	/**
	 * This method replaces DOT(".") to DOT_STRING("U+FF04").
	 */
	static String replaceDotToDotUtf(String original) {
		return original.replace(".",DOT_STRING);
	}

//	@SuppressWarnings("unchecked")
//	public static void main(String[] args) {
//
//		Map<String, Object> map = new HashMap<String, Object>();
//
//		map.put("name.txt", "1.1");
//		map.put("key.txt", "2.2");
//		map.put("hello.txt", "33.3");
//		map.put("hey.txt", "4.........4");
//		map.put("strawberry.txt", "5....$%^&*");
//
//		Map<String, Object> map2 = new HashMap<String, Object>();		
//		map2.put("kaist", map);
//		map2.put("etri", map);
//		map2.put("_id", "hihi");
//
//		List<Map<?,?>> list = new ArrayList<Map<?,?>>();
//
//		list.add(map);
//		list.add(map2);
//
//		Map<String, Object> map3 = new HashMap<String, Object>();		
//		map3.put("recur.hi", list);
//
//		Map<String, Object> replace = (Map<String, Object>) jsonTraverse(map3, true);
//		//Map<String, Object> replace = (Map<String, Object>) mapTraverse(map3, true, true);
//
//		System.out.println(replace);
//
//		replace = (Map<String, Object>) jsonTraverse(replace, false);
//		//replace = (Map<String, Object>) mapTraverse(replace, true, false);
//		System.out.println(replace);
//	}
}
