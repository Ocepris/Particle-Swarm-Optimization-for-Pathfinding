package PSO.simulation;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class StatsContainer {
    private Map<String, String> stats;

    public StatsContainer(String version, String mapName){
        stats = new HashMap<>();
        stats.put("version", version);
        stats.put("map_name", mapName);
        stats.put("iteration", "0");
    }

    public void setValue(String key, String value){
        stats.put(key, value);
    }

    public String getValue(String key){
        return stats.get(key);
    }

    public Set<String> getKeys(){
        return stats.keySet();
    }
}
