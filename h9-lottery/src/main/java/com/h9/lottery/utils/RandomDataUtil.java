package com.h9.lottery.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.RandomUtils;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;


import java.util.*;

import static org.jboss.logging.Logger.getLogger;

/**
 * Created with IntelliJ IDEA.
 *
 * RandomDataUtil:刘敏华 shadow.liu@hey900.com
 * Date: 2017/11/6
 * Time: 11:07
 */
@Component
public class RandomDataUtil {
     Logger logger = getLogger(RandomDataUtil.class);

    public final static int TWO=2;
    /**
     * 生成随机集合（不重复）  有可能会出现死循环
     * <p>
     *     使用Set的值唯一的特性。
     *     最佳适用场合：集合中数目多，取相对较少对象时。在取对象相对较多时（超过集合的一半时）采用逆向方法，
     *                在取得对象个数是集合总数的1/2左右时是效率最慢的。
     * </p>
     * @param list
     * @param generateNum 生成集合中元素的个数
     * @param <T>
     * @return
     */
    public <T> List<T> generateRandomDataNoRepeat(List<T> list, Integer generateNum){
        List<T> tList = new ArrayList<T>();
        if(!CollectionUtils.isEmpty(list)) {
            for (Integer num : generateRandomNoRepeat(list.size(), generateNum)) {
                tList.add(list.get(num));
            }
        }
        return tList;
    }

    /**
     * 生成随机集合，随机置换位置、随机截取位置法。
     * <p>
     *     随机置换法：将集合的每个位置值与随机位置的值调换，并随机截取位置.
     *     最佳适合场合：集合的数量相对较少，获取较多的随机个数集合。
     * </p>
     * @param list
     * @param generateNum
     * @param <T>
     * @return
     */
    public <T> List<T> generateRandomPermutation(List<T> list,Integer generateNum){
        if(!CollectionUtils.isEmpty(list)) {
            checkParams(list.size(),generateNum);
            List<T> randomAllList = randomPermutation(list, generateNum);
            if(generateNum >= list.size()){
                return randomAllList;
            }
            int initPosition=interceptPosition(list.size(),generateNum);
            return randomAllList.subList(initPosition,initPosition+generateNum);
        }
        return Collections.emptyList();
    }

    /**
     * 随机置换算法
     * @param list
     * @param generateNum
     * @param <T>
     * @return
     */
    private <T> List<T> randomPermutation(List<T> list,Integer generateNum){
        for (int i = 0; i < list.size(); i++) {
            Integer random= RandomUtils.nextInt(0,list.size());
            T t = list.get(random);
            list.set(random,list.get(i));
            list.set(i,t);
        }
        return list;
    }

    /**
     * 随机生成截取位置
     * @param totalCount
     * @param generateNum
     * @return
     */
    private Integer interceptPosition(Integer totalCount,Integer generateNum){
        int num=RandomUtils.nextInt(0,totalCount);
        logger.debugv("num="+num+",generateNum="+generateNum);
        if(num+generateNum>totalCount){
            num=num-generateNum;
        }

        return  num;
    }
    /**
     * 生成不重复的随机数
     * @param totalCount
     * @param generateNum
     * @param
     * @return
     */
    public Set<Integer> generateRandomNoRepeat(Integer totalCount,Integer generateNum){
        if(isLessThanHalfTotalCount(totalCount,generateNum)){
            return getRandomNoRepeat(totalCount,generateNum);
        }
        return getReverseRandomNoRepeat(totalCount,generateNum);
    }

    /**
     * 验证参数是否合法
     * @param totalCount
     * @param generateNum
     */
    private void checkParams(Integer totalCount,Integer generateNum){
        if(totalCount<generateNum){
            throw new IllegalArgumentException("generateNum is out of totalCount");
        }
    }

    /**
     * 判断使用哪种生成机制
     * @param totalCount
     * @param generateNum
     * @return
     */
    private Boolean isLessThanHalfTotalCount(Integer totalCount,Integer generateNum){
        if(generateNum<totalCount/TWO){
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    /**
     * 生成set，不重复
     * @param totalCount
     * @param generateNum
     * @return
     */
    private Set<Integer> getRandomNoRepeat(Integer totalCount,Integer generateNum){
        Set<Integer> set = new HashSet<Integer>();
        while (true) {
            set.add(RandomUtils.nextInt(0,totalCount));
            if(set.size() == generateNum){
                return set;
            }
        }
    }

    /**
     * 逆向生成set，不重复
     * @param totalCount
     * @param generateNum
     * @return
     */
    private Set<Integer> getReverseRandomNoRepeat(Integer totalCount, Integer generateNum){
        Set<Integer> set = new HashSet<Integer>();
        while (true) {
            set.add(RandomUtils.nextInt(0,totalCount));
            if(set.size() == totalCount-generateNum){
                Set<Integer> setALL=getSetALL(totalCount);
                setALL.removeAll(set);
                return setALL;
            }
        }
    }

    /**
     * 生成Set
     * @param totalCount
     * @return
     */
    private Set<Integer> getSetALL(Integer totalCount){
        Set<Integer> set = new HashSet<Integer>();
        for(int i=0;i<totalCount;i++){
            set.add(i);
        }
        return set;
    }

//    public static void main(String[] args) {
//        Logger logger = getLogger(RandomDataUtil.class);
//        RandomDataUtil randomDataUtil = new RandomDataUtil();
//        List<String> list = new ArrayList<>();
//        list.add("a");
//        list.add("b");
//        list.add("c");
//        list.add("d");
//        list.add("e");
//        list.add("f");
//        list.add("g");
//        for(int i=0;i<10000;i++){
//            logger.debugv(JSONObject.toJSONString(list));
//            List<String> strings = randomDataUtil.generateRandomPermutation(list, 3);
//            logger.debugv("---  i="+i);
//            logger.debugv(JSONObject.toJSONString(strings) + " " + strings.size());
//        }
//
//    }

}
