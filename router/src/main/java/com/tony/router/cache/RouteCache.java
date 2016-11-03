package com.tony.router.cache;

import android.util.LruCache;

import com.tony.router.route.IRoute;

/**
 * Created by tony on 11/3/16.
 */
public class RouteCache extends LruCache<String, IRoute>{
    public RouteCache(int maxSize) {
        super(maxSize);
    }

    //    private static int CAPACITY = 30;
//    private LinkedList<HashMap<String, Object>> mLinkedList;
//    private final String KEY = "key", VALUE = "value";
//
//    public RouteCache() {
//        init();
//    }
//
//    public RouteCache(int capacity) {
//        CAPACITY = capacity;
//        init();
//    }
//
//    private void init() {
//        mLinkedList = new LinkedList<HashMap<String, Object>>();
//    }
//
//    // 动态调整缓存空间大小。
//    // 注意！该方法极可能线程不安全。
//    public void ensureCapacity(int capacity) {
//        if (capacity >= CAPACITY) {
//            // 若比原先大，直接赋值即可
//            // Capacity = capacity;
//        } else {
//            // 若新调整的容量比原先还要小，那么一个一个的删掉头部元素直到相等
//            while (mLinkedList.size() > capacity) {
//                HashMap<String, Object> map = mLinkedList.removeFirst();
//                System.out.println("\n删除-> " + map.get(KEY) + ":"
//                        + map.get(VALUE));
//            }
//        }
//
//        CAPACITY = capacity;
//        System.out.println("\n重新调整缓存容量为:" + CAPACITY);
//    }
//
//    // 把需要缓存的数据以<key,value>键值对的形式存入缓存。
//    // 存之前，要检查缓存是否满，满了的话就删除LinkedList第一个元素。
//    public void put(String key, Object obj) {
//        if (mLinkedList.size() < CAPACITY) {
//
//        } else {
//            HashMap<String, Object> map = mLinkedList.removeFirst();
//            System.out.println("\n缓存空间已满!删除-> " + map.get(KEY) + ":"
//                    + map.get(VALUE));
//        }
//
//        HashMap<String, Object> map = new HashMap<String, Object>();
//        map.put(KEY, key);
//        map.put(VALUE, obj);
//        mLinkedList.addLast(map);
//        System.out.println("\n缓存-> " + map.get(KEY) + ":" + map.get(VALUE));
//    }
//
//    // 根据key读出缓存数据
//    // 原理：从头到尾遍历整个链表LinkedList，只要检查到元素中的key和给定的key相同，立即返回。
//    // 同时更新该元素在LinkedList中位置:从原先的位置放到最后一个位置。
//    public Object get(String key) {
//        Object obj = null;
//        for (HashMap<String, Object> map : mLinkedList) {
//            if (map.get(KEY).equals(key)) {
//                System.out.println("读取->" + key + ":" + map.get(VALUE));
//                mLinkedList.remove(map);
//                mLinkedList.addLast(map);
//                return map.get(VALUE);
//            }
//        }
//        return obj;
//    }
}
