package com.clock.intelligent.clock.uitls;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

/**
 * 网页传过来的数据的整改工具
 * @author hp
 */
@Component
public class PublishUtils {


    /**获得时钟的小时
     * @param timeLengthM
     * @return
     */
    public String getTimeLengthM(String timeLengthM) {
        if (StringUtils.isNotBlank(timeLengthM)){
            String[] timeH = this.getTimeMS(timeLengthM);
            if (StringUtils.isBlank(timeH[0])){
                return "00";
            }
            return timeH[0];
        }
        return null;
    }
    /**获得时钟的
     * @param timeLengthM
     * @return
     */
   public  String getTimeLengthS(String timeLengthM) {
        if (StringUtils.isNotBlank(timeLengthM)){
            String[] timeM = this.getTimeMS(timeLengthM);
            if (StringUtils.isBlank(timeM[1])){
                return "00";
            }
            return timeM[1];
        }
        return null;
    }
    private String[] getTimeMS(String timeLengthM){
        String[] MS = timeLengthM.split(":");
        return MS;
    }
}
