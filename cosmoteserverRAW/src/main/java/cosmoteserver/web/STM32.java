package cosmoteserver.web;


import java.util.List;

public class STM32 {  
private Long id;
private String AK_id;
private String box_label;
private String ip;
private String CP_state;
private String CMD_state;
private String Action_request;
private String Fissa1_Open;
private String Fissa1_Short;
private String Fissa1_Signal;
private String Fissa1_Newnet;
private String Fissa2_Open;
private String Fissa2_Short;
private String Fissa2_Signal;
private String Fissa2_Newnet;
private String Fissa3_Open;
private String Fissa3_Short;
private String Fissa3_Signal;
private String Fissa3_Newnet;


public Long getId() {
return id;
}
public void setId(Long id) {
this.id = id;
}

public void setAK_id(String AK_id) {
this.AK_id = AK_id;
}
public String getAK_id() {
return AK_id;
}
public void setbox_label(String box_label) {
this.box_label = box_label;
}
public String getbox_label() {
return box_label;
}
public void set_ip(String ip) {
this.ip = ip;
}
public String get_ip() {
return ip;
}
public void setCP_state(String CP_state) {
this.CP_state = CP_state;
}
public String getCP_state() {
return CP_state;
}

public void setCMD_state(String CMD_state) {
this.CMD_state = CMD_state;
}
public String getCMD_state() {
return CMD_state;
}

/*
public void setAction_request(String Action_request) {
this.Action_request = Action_request;
}
public String getAction_request() {
return Action_request;
}*/
public void Fissa1_Open(String Fissa1_Open) {
this.Fissa1_Open = Fissa1_Open;
}
public String getFissa1_Open() {
return Fissa1_Open;
}
public void Fissa2_Open(String Fissa2_Open) {
this.Fissa2_Open = Fissa2_Open;
}
public String getFissa2_Open() {
return Fissa2_Open;
}
public void Fissa3_Open(String Fissa3_Open) {
this.Fissa3_Open = Fissa3_Open;
}
public String getFissa3_Open() {
return Fissa3_Open;
}
public void Fissa1_Short(String Fissa1_Short) {
this.Fissa1_Short = Fissa1_Short;
}
public String getFissa1_Short() {
return Fissa1_Short;
}
public void Fissa2_Short(String Fissa2_Short) {
this.Fissa2_Short = Fissa2_Short;
}
public String getFissa2_Short() {
return Fissa2_Short;
}
public void Fissa3_Short(String Fissa3_Short) {
this.Fissa3_Short = Fissa3_Short;
}
public String getFissa3_Short() {
return Fissa3_Short;
}
//*********************************************
public void Fissa1_Signal(String Fissa1_Signal) {
this.Fissa1_Signal = Fissa1_Signal;
}
public String getFissa1_Signal() {
return Fissa1_Signal;
}
public void Fissa2_Signal(String Fissa2_Signal) {
this.Fissa2_Signal = Fissa2_Signal;
}
public String getFissa2_Signal() {
return Fissa2_Signal;
}
public void Fissa3_Signal(String Fissa3_Signal) {
this.Fissa3_Signal = Fissa3_Signal;
}
public String getFissa3_Signal() {
return Fissa3_Signal;
}
//*******************************************
public void Fissa1_Newnet(String Fissa1_Newnet) {
this.Fissa1_Newnet = Fissa1_Newnet;
}
public String getFissa1_Newnet() {
return Fissa1_Newnet;
}
public void Fissa2_Newnet(String Fissa2_Newnet) {
this.Fissa2_Newnet = Fissa2_Newnet;
}
public String getFissa2_Newnet() {
return Fissa2_Newnet;
}
public void Fissa3_Newnet(String Fissa3_Newnet) {
this.Fissa3_Newnet = Fissa3_Newnet;
}
public String getFissa3_Newnet() {
return Fissa3_Newnet;
}
}


