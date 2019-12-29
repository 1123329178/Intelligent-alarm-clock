package com.clock.intelligent.clock.SingleMap;

import io.netty.channel.Channel;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**单例hashmap 只是一个  做缓冲,只为存channel
 * @author hp
 */
@Component
public class SingletonHashMap<S, C> extends HashMap<String, Channel> {
    private Map<String, Channel> channelMap;

        public SingletonHashMap() {
            channelMap=new HashMap<String,Channel>();
    }

    private static volatile SingletonHashMap<String, Channel> instance = null;

    public static SingletonHashMap<String, Channel> getInstance() {
           if (instance == null) {
             synchronized (SingletonHashMap.class) {
                if (instance == null) {
                   instance = new SingletonHashMap<String, Channel>();
                }
             }
           }
           return instance;
    }





//            public static void main(String[] args){
//                SingletonHashMap<S, C> instance = SingletonHashMap.getInstance();
//                Map<String, Channel> channelMap = instance.channelMap;
//                Map<String, Channel> channelMap1 = instance.channelMap;
//
//
//
//                if(channelMap == channelMap1){
//                    System.out.println("创建的是同一个实例");
//                }else{
//                    System.out.println("创建的不是同一个实例");
//                }
//            }


}
