package etri.sdn.controller.module.storage;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class StorageConverter {

	private final static String DOT_STRING = "U+FF04";

	@SuppressWarnings("unchecked")
	public static BasicDBObject MapToDBObject(Map<String, Object> map) {
		BasicDBObject dbObject = new BasicDBObject();

		map = (Map<String, Object>) traverseMap(map, true, true);
		dbObject.putAll(map);

		return dbObject;
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> DBObjectToMap(BasicDBObject dbObject) {
		Map<String, Object> map = (Map<String, Object>) traverseMap(dbObject.toMap(), true, false);

		return map;
	}

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
				for(Object o: oldList)
					list.add(toJSON(o));
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
				System.out.println("Unknown type: " + obj + ":" + obj.getClass());
				return obj;
			}
	}
	
	public static Object toDBObject(Object obj) {
		return null;
	}

	@SuppressWarnings("unchecked")
	public static Object traverseMap(Map<String, Object> map, boolean init, boolean dot) {

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
				result.put(s, traverseMap((Map<String, Object>) o, false, dot));
				if(!init){
					return o;
				}
			}			
		}
		return result;
	}

	static String replaceDotUtfToDot(String original) {
		return original.replace(DOT_STRING, ".");
	}

	static String replaceDotToDotUtf(String original) {
		return original.replace(".",DOT_STRING);
	}

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name.txt", "1.1");
		map.put("key.txt", "2.2");
		map.put("hello.txt", "33.3");
		map.put("hey.txt", "4.........4");
		map.put("strawberry.txt", "5....$%^&*");

		Map<String, Object> map2 = new HashMap<String, Object>();		
		map2.put("kaist", map);
		map2.put("etri", map);
		map2.put("_id", "hihi");

		Map<String, Object> replace = (Map<String, Object>) traverseMap(map2, true, true);

		System.out.println(replace);

		replace = (Map<String, Object>) traverseMap(replace, true, false);
		System.out.println(replace);
	}


}
