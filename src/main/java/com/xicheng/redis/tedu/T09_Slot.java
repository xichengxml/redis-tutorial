package com.xicheng.redis.tedu;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * description redis槽道的计算方式
 *
 * 思考: cluster nodes命令输出的槽道信息是怎么计算的
 *
 * @author xichengxml
 * @date 2020-06-14 16:46
 */
@Slf4j
public class T09_Slot {

    public static void main(String[] args) {
        int slotNum = 12344;
        String slotBinary = Integer.toBinaryString(slotNum);
        log.info("槽道二级制: {}", slotBinary);
        StringBuilder stringBuilder01 = algorithm01(slotNum);
        StringBuilder stringBuilder02 = algorithm02(slotBinary.split(""));
        Queue<String> slotQueue = algorithm03(slotBinary.split(""));

        log.info("算法1--该节点拥有的槽道号为: {}", stringBuilder01.toString());
        log.info("算法2--该节点拥有的槽道号为: {}", stringBuilder02.toString());
        log.info("算法3--该节点拥有的槽道号为: {}", slotQueue);
    }

    /**
     * 这个算法是老师讲的，感觉算法有点冗余，为什么要去做位运算呢，直接判断某一位是否为1不就行了?
     * 而且为什么要循环16384呢，直接循环slotBinary的长度不就可以吗?
     * 而且 int会溢出
     * @param slotNum
     * @return
     */
    private static StringBuilder algorithm01(int slotNum) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 16384; i++) {
            int slot = (slotNum >> i) & 1;
            if (slot == 1) {
                stringBuilder.append(i).append(" ");
            }
        }
        return stringBuilder;
    }

    /**
     * 字符串比较，可能有点慢
     * @param slotNumArr
     * @return
     */
    private static StringBuilder algorithm02(String[] slotNumArr) {
        StringBuilder stringBuilder = new StringBuilder();
        int length = slotNumArr.length;
        for (int i = 0; i < length; i++) {
            if ("1".equals(slotNumArr[i])) {
                stringBuilder.append(length - 1 - i).append(" ");
            }
        }
        return stringBuilder;
    }

    /**
     * 上面槽道号输出的逆序的，使用队列来进行正序输出
     * @param slotNumArr
     * @return
     */
    private static Queue<String> algorithm03(String[] slotNumArr) {
        ArrayDeque<String> slotQueue = new ArrayDeque<>();
        int length = slotNumArr.length;
        for (int i = 0; i < length; i++) {
            if ("1".equals(slotNumArr[i])) {
                slotQueue.push(length - 1 - i + "");
            }
        }
        return slotQueue;
    }
}
