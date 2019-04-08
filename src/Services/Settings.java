package Services;

import org.ini4j.Ini;
import org.ini4j.Profile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Settings {
    private final String INI_FILEPATH = System.getProperty("user.dir");
    private final String SETTINGS_FILE_NAME = "Settings.ini";
    private final String NEIGHBORS_SECTION = "Neighbors";
    private final String SHARED_PASSWORD_SECTION = "SharedPassword";
    private final String SHARED_PASSWORD_KEY = "Password";

    private Map<String,String> neighborsAndIP;

    Ini iniSettings;

    public Settings(){
        neighborsAndIP = new HashMap<>();
        try {
            iniSettings = new Ini(new File(INI_FILEPATH + "\\" + SETTINGS_FILE_NAME));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addNeighbor(String neighbor, String ip_port){
        iniSettings.put(NEIGHBORS_SECTION,neighbor,ip_port);
    }

    public void removeNeighbor(String neighbor){
        Profile.Section section = iniSettings.get(NEIGHBORS_SECTION);
        section.remove(neighbor);
    }

    public String getSharedPassword(){
        return iniSettings.get(SHARED_PASSWORD_SECTION,SHARED_PASSWORD_KEY);
    }

    public void setSharedPassword(String password){
        iniSettings.put(SHARED_PASSWORD_SECTION,SHARED_PASSWORD_KEY,password);
    }

    public void SaveChanges(){
        try {
            iniSettings.store(iniSettings.getFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String,String> getAllNeighbors(){
        Ini.Section section = iniSettings.get(NEIGHBORS_SECTION);
        neighborsAndIP.clear();
        if(section == null){
            iniSettings.add(NEIGHBORS_SECTION);
            section = iniSettings.get(NEIGHBORS_SECTION);
        }
        Set<String> neighborsNames = section.keySet();
        if(neighborsNames.size() == 0) {
            return neighborsAndIP;
        }

        for (String neighborName:neighborsNames) {
            neighborsAndIP.put(neighborName,section.get(neighborName));
        }
        return neighborsAndIP;
    }
}
