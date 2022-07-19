package cosmoteserver.web;

import java.util.ArrayList;
import java.util.List;

public class User {
private Long id;
private String username;
private String password;
private String loginstate;
private String homesessionactive;
private String TTLPid;
public List<String> AKlist;
public List<String> AKlabellist;
private byte maxallowedphones;
private short maxalloweddataperphone;
//private List<String> userphone =  new ArrayList<String>(10);


public Long getId() {
return id;
}
public void setId(Long id) {
this.id = id;
}
public String getPassword() {
return password;
}
public void setPassword(String password) {
this.password = password;
}
public String getLoginstate() {
return loginstate;
}
public void setLoginstate(String loginstate) {
this.loginstate = loginstate;
}
public String getUsername() {
return username;
}
public void setUsername(String username) {
this.username = username;
}
public void setAKlist(List<String> AKlist){
this.AKlist = AKlist;
}
public void setTTLPid(String TTLPid){
this.TTLPid = TTLPid;
}
public String getTTLPid(){
return TTLPid;
}
public void setAKlabellist(List<String> labellist){
    this.AKlabellist=labellist;
}
public List<String> getAKlabelist(){    
return AKlabellist;
}

public byte getMaxAllowedPhones(){
    return maxallowedphones;
}
public void setMaxAllowedPhones(byte number) {
this.maxallowedphones = number;
}
public short getMaxAllowedDataPerPhone(){
    return maxalloweddataperphone;
}
public void setMaxAllowedDataPerPhone(short number) {
this.maxalloweddataperphone = number;
}
public String gethomesessionactive() {
return homesessionactive;
}
public void sethomesessionactive(String homesessionactive) {
this.homesessionactive = homesessionactive;
}

}