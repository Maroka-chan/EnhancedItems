package EnhancedItems.util;

import java.util.*;

public final class Convert {
    private Convert(){}

    public static String mapToString(Map<String, String[]> map){
        StringBuilder mapString = new StringBuilder();

        Iterator<String> iterator = map.keySet().iterator();
        for (int i = 0; i < map.size(); i++){
            String key = iterator.next();
            mapString.append(key).append(":[");
            for (String s : map.get(key))
                mapString.append(s).append(",");

            if(mapString.charAt(mapString.length()-1) == ',')
                mapString.deleteCharAt(mapString.length()-1);
            mapString.append("]\n");
        }
        return mapString.toString();
    }

    public static Map<String, String[]> stringToMap(String mapString){
        Map<String,String[]> map = new HashMap<>();
        String[] pairs = mapString.split("\n");
        for (String pair : pairs){
            String[] KV = pair.split(":", 2);
            String[] args = KV[1].substring(1, KV[1].length()-1).split(",");
            map.put(KV[0], args);
        }
        return map;
    }
}
