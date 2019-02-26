package com.atguigu.gmall.service;

import com.atguigu.gmall.bean.CartInfo;

import java.util.List;

public interface CartService {
    CartInfo exists(CartInfo exists);

    void saveCart(CartInfo cartInfo);

    void updateCart(CartInfo ifCart);

    void flushCartCacheByUser(String userId);

    List<CartInfo> cartListFromCache(String userId);


    void mergCart(String listCartCookie, String userId);
}
