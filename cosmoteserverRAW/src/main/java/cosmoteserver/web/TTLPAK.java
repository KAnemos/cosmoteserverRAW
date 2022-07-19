package cosmoteserver.web;




public class TTLPAK {
private Long id;
private String TTLP_id;
private String AK_id;
private String AK_label;
private String AK_xlong,AK_ylat;
private String mdfsessionactive;

public Long getId() {
return id;
}
public void setId(Long id) {
this.id = id;
}
public String getTTLPid() {
return TTLP_id;
}
public void setTTLPid(String ttlpid) {
this.TTLP_id = ttlpid;
}
public String getAKid() {
return AK_id;
}
public void setAKid(String akid) {
this.AK_id = akid;
}
public String getAKlabel() {
return AK_label;
}
public void setAKlabel(String aklabel) {
this.AK_label = aklabel;
}
public String getAKxlong() {
return AK_xlong;
}
public void setAKxlong(String xlong) {
this.AK_xlong = xlong;
}
public String getAKylat() {
return AK_ylat;
}
public void setAKylat(String ylat) {
this.AK_ylat = ylat;
}
public String getmdfsessionactive() {
return mdfsessionactive;
}
public void setmdfsessionactive(String mdfsessionactive) {
this.mdfsessionactive = mdfsessionactive;
}
/*public String getPhonenumber(int index){
    return userphone.get(index);
}
public void deletePhonenumber(String phonenumber){
    this.userphone.remove(phonenumber);
}*/
}
