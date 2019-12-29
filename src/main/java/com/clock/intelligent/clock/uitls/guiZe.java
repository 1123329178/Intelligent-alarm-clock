package com.clock.intelligent.clock.uitls;

import org.springframework.stereotype.Component;

@Component
public class guiZe {
    private String [] weas = { "小雨","中雨","大雨","","多云","晴","阴","小雪","中雪","大雪","B"};
    //当第一次搜索不到直接搜索关键字
    private String [] weaa = { "","雨","","","多云","","","","雪","雪","B"};
    private String [] wins = { "东南风","东北风","西南风","西北风","东风","南风","西风","北风","微风"};
    private String [] airs = {"优","良","中","差"};
    private String [] weeks = { "星期一","星期二","星期三","星期四","星期五","星期六","星期日"};
    public String getWea(String wea) {
        String wea1="",wea2="";
        int wea3 = 0,wea4=0;
        if(wea.length()>2) {
            if(wea.contains("转")) {
                //分割后的天气
                String aa[]=wea.split("转");
                    //第一个天气  第一次检测
                    for(int i=0;i<weas.length;i++){
                        if(aa[0].equals(weas[i])) {
                            wea3=i+1;
                        }
                    }
                    if(wea3==0) {//没有常规天气  进行第二次检测
                        for(int i1=0;i1<weaa.length;i1++){
                            if(aa[0].equals(weaa[i1])) {
                                wea3=i1+1;
                            }
                        }
                    }
                //第2个天气  第一次检测
                for(int i=0;i<weas.length;i++){
                    if(aa[1].equals(weas[i])) {
                        wea4=i+1;
                    }
                }
                if(wea4==0) {//没有常规天气  进行第二次检测
                    for(int i1=0;i1<weaa.length;i1++){
                        if(aa[1].equals(weaa[i1])) {
                            wea4=i1+1;
                        }
                    }
                }
            }
            String resu="";
            if(wea3==0) wea3=1;
            if(wea4==0) wea4=11;
            if (wea4==10){
                resu=wea3+",A";
            }
            if (wea4==11)resu=wea3+",B";
            return resu;
        }
        else {
            for(int i=0;i<weas.length;i++){
                if(wea.equals(weas[i])) {
                    return i+1+",B";
                }
            }
        }
        return (String) "1,B";
    }
    public String getWeek(String wea) {

        for(int i=0;i<weeks.length;i++){
            if(wea.equals(weeks[i])) {
                return i+1+",";
            }
        }
        return (String) "1,";
    }
    public String getWin(String win) {

        for(int i=0;i<wins.length;i++){
            if(win.equals(wins[i])) {
                return i+1+",";
            }
        }
        return (String) "1,";
    }
    public String getAir(String air) {

        for(int i=0;i<airs.length;i++){
            if(air.equals(airs[i])) {
                return i+1+",";
            }
        }
        return (String) "1,";
    }
    public String getTem(String air) {
        air=air.replaceAll("℃", "");
        int a =Integer.parseInt(air);
        if(a>0) {
            return "+"+a;
        }else if(a<0)
        {return ""+a;}else {
            return "000";}
    }
    /*
     * 风的级数
     */
    public String getWin_speed(String winspeed) {
        String winspee = "";
        boolean flag=false;
        char wins[]=winspeed.toCharArray();
        for(int i =0;i<15;i++) {
            char c = Character.forDigit(i,10);
            for(int j=0;j<wins.length-1;j++) {

                if(wins[j]=='<') {
                    flag=true;

                }

                if(wins[j]==c) {

                    if(flag) {
                        winspee=(i-1)+",";
                    }
                    winspee=winspee+i+",";
                }

            }


        }
        return winspee;
    }

    public String getAir_level(int i) {

        String level = "1";
        if(i>0&&i<=50) {
            level="1";
        }else if(i>50&&i<=100){
            level="2";
        }else if(i>100&&i<=150) {
            level="3";
        }else if(i>150&&i<=200) {
            level="4";
        }
        return level;

    }
}
