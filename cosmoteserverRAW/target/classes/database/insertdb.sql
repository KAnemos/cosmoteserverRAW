insert into user_item (id,username,password) values (1,'admin','admin');
insert into user_item (id,username,password,loginstate,homesessionactive,TTLP_id) values (2,'kostas','kostas','OFF','NO','TTLP_KALAMARIAS');
insert into TTLP_AK (id,TTLP_id,AK_id,AK_label,AK_xlong,AK_ylat,mdfsessionactive) values (3,'TTLP_KALAMARIAS','KLM','KALAMARIA','40.585082','22.959748','NO');
insert into TTLP_AK (id,TTLP_id,AK_id,AK_label,AK_xlong,AK_ylat,mdfsessionactive) values (4,'TTLP_KALAMARIAS','BYZ','BYZANTIO','40.590882', '22.958221','NO');
insert into TTLP_AK (id,TTLP_id,AK_id,AK_label,AK_xlong,AK_ylat,mdfsessionactive) values (5,'TTLP_KALAMARIAS','RYS','RYSIO','40.494383', '22.989374','NO');
insert into TTLP_AK (id,TTLP_id,AK_id,AK_label,AK_xlong,AK_ylat,mdfsessionactive) values (6,'TTLP_TOYMPAS','TOY','TOYMPA','40.597524', '22.965238','NO');
insert into TTLP_AK_KV (id,AK_id,KV_id) values (7,'KLM',101);
insert into KLM_box(id,ip,Ak_id,box_label,CP_state,CMD_state,Fissa1_Open,Fissa1_Short,Fissa1_Signal,Fissa1_Newnet,Fissa2_Open,Fissa2_Short,Fissa2_Signal,Fissa2_Newnet,Fissa3_Open,Fissa3_Short,Fissa3_Signal,Fissa3_Newnet) 
values (8,'unknown','KLM','KLM_box','DOWN','IDLE','0','0','0','0','0','0','0','0','0','0','0','0');
insert into BYZ_box(id,ip,Ak_id,box_label,CP_state,CMD_state,Fissa1_Open,Fissa1_Short,Fissa1_Signal,Fissa1_Newnet,Fissa2_Open,Fissa2_Short,Fissa2_Signal,Fissa2_Newnet,Fissa3_Open,Fissa3_Short,Fissa3_Signal,Fissa3_Newnet)
values (9,'unknown','BYZ','BYZ_box','DOWN','IDLE','0','0','0','0','0','0','0','0','0','0','0','0');
insert into RYS_box(id,ip,Ak_id,box_label,CP_state,CMD_state,Fissa1_Open,Fissa1_Short,Fissa1_Signal,Fissa1_Newnet,Fissa2_Open,Fissa2_Short,Fissa2_Signal,Fissa2_Newnet,Fissa3_Open,Fissa3_Short,Fissa3_Signal,Fissa3_Newnet)
values (10,'unknown','RYS','RYS_box','DOWN','IDLE','0','0','0','0','0','0','0','0','0','0','0','0');
insert into TOY_box(id,ip,Ak_id,box_label,CP_state,CMD_state,Fissa1_Open,Fissa1_Short,Fissa1_Signal,Fissa1_Newnet,Fissa2_Open,Fissa2_Short,Fissa2_Signal,Fissa2_Newnet,Fissa3_Open,Fissa3_Short,Fissa3_Signal,Fissa3_Newnet)
values (11,'unknown','TOY','TOY_box','DOWN','IDLE','0','0','0','0','0','0','0','0','0','0','0','0');
insert into sequence value (1000);