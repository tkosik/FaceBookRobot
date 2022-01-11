import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ReadDataLogin {
    private String login;
    private String password;
    public String path;


    ReadDataLogin(String path) {
        this.path = path;
    }

    public String getLogin() {
        return login;
    }

    private void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    private void setPassword(String password) {
        this.password = password;
    }

    public String getPath(){
        return path;
    }

    public void readingTXT() throws IOException {
        String st;
        File file = new File(getPath());
        BufferedReader reader = new BufferedReader(new FileReader(file));
        HashMap<String, String> dataLogin = new HashMap<>();

        while((st = reader.readLine()) != null){
            System.out.println(st);
            String[] parts = st.split("=",2);
            if(parts.length >= 2){
                String key = parts[0];
                String value = parts[1];
                dataLogin.put(key,value);
            }
        }
        System.out.println(dataLogin);

        for(String key : dataLogin.keySet()){
           if(key.equals("haslo")){
               setPassword(dataLogin.get("haslo"));
           }else if(key.equals("login")){
               setLogin(dataLogin.get("login"));
           }
        }

        System.out.println(login + password);
        }
    }



