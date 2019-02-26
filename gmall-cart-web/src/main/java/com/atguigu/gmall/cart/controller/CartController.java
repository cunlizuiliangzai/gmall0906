package com.atguigu.gmall.cart.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.atguigu.gmall.annotations.LoginRequired;
import com.atguigu.gmall.bean.CartInfo;
import com.atguigu.gmall.bean.SkuInfo;
import com.atguigu.gmall.service.CartService;
import com.atguigu.gmall.service.SkuService;
import com.atguigu.gmall.util.CookieUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CartController {

    @Reference
    SkuService skuService;

    @Reference
    CartService cartService;


    @LoginRequired(isNeedLogin = false)
    @RequestMapping("checkCart")
    //页面点击check按钮，发送ischeack和skuId，用cartInfo接收
    public String checkCart(CartInfo cartInfo, HttpServletRequest request, HttpServletResponse response, ModelMap map) {
        String userId = "";
        List<CartInfo> cartInfos = new ArrayList<>();
        //从redis缓存或者cookie中获取cartinfos对象
        if (StringUtils.isNotBlank(userId)) {
            cartInfos = cartService.cartListFromCache(userId);
        } else {
            String listCartCookie = CookieUtil.getCookieValue(request, "listCartCookie", true);
            cartInfos = JSON.parseArray(listCartCookie, CartInfo.class);
        }
        //
        for (CartInfo info : cartInfos) {
            if (info.getSkuId().equals(cartInfo.getSkuId())) {
                //设置cartinfos中的所有ischecked状态
                info.setIsChecked(cartInfo.getIsChecked());
                if (StringUtils.isNotBlank(userId)) {
                    //更新数据库
                    cartService.updateCart(info);
                    //更新redis缓存中的cartinfo
                    cartService.flushCartCacheByUser(userId);
                } else {
                    //更新cookie中的cartinfo
                    CookieUtil.setCookie(request, response, "listCartCookie", JSON.toJSONString(cartInfos), 1000 * 60 * 60 * 24, true);

                }
            }
        }
        map.put("cartList", cartInfos);
        BigDecimal b = getMySum(cartInfos);
        map.put("totalPrice", b);


        return "cartListInner";
    }


    @LoginRequired(isNeedLogin = false)
    @RequestMapping("cartList")
    public String cartList(HttpServletRequest request, HttpServletResponse response, Model model) {
        String userId = "";
        List<CartInfo> cartList = null;
        if (StringUtils.isNotBlank(userId)) {
            //userId存在，操作redis缓存，获取购物车对象
            cartList = cartService.cartListFromCache(userId);
        } else {
            //操作cookie获取购物车对象
            String listCartCookie = CookieUtil.getCookieValue(request, "listCartCookie", true);
            if (StringUtils.isNotBlank(listCartCookie)) {
                cartList = JSON.parseArray(listCartCookie, CartInfo.class);
            }
        }
        //把总价和购物车对象放到域对象中
        model.addAttribute("cartList", cartList);
        BigDecimal b = getMySum(cartList);
        model.addAttribute("totalPrice", b);
        return "cartList";
    }

    private BigDecimal getMySum(List<CartInfo> cartList) {
        BigDecimal b = new BigDecimal("0");
        for (CartInfo cartInfo : cartList) {
            String isChecked = cartInfo.getIsChecked();
            if ("1".equals(isChecked)) {
                b = b.add(cartInfo.getCartPrice());
            }

        }
        return b;
    }


    @LoginRequired(isNeedLogin = false)
    @RequestMapping("addToCart")
    public String addToCart(HttpServletRequest request, HttpServletResponse response, String skuId, int num) {
        String userId = "";
        //根据skuId获取skuINFO信息
        SkuInfo skuInfo = skuService.getSkuById(skuId);
        CartInfo cartInfo = new CartInfo();
        //把skuInfo信息设置到cartInfo中
        cartInfo.setSkuId(skuId);
        cartInfo.setSkuPrice(skuInfo.getPrice());
        cartInfo.setSkuNum(num);
        cartInfo.setCartPrice(skuInfo.getPrice().multiply(new BigDecimal(num)));
        if (StringUtils.isNotBlank(userId)) {
            cartInfo.setUserId(userId);
        }
        cartInfo.setIsChecked("1");
        cartInfo.setImgUrl(skuInfo.getSkuDefaultImg());
        cartInfo.setSkuName(skuInfo.getSkuName());

        List<CartInfo> cartInfos = new ArrayList<>();
        if (StringUtils.isBlank(userId)) {
            //userId是空，用户未登陆,操作cookie
            //根据cookieName获取cookie的value，类型为json字符串
            String listCartCookieStr = CookieUtil.getCookieValue(request, "listCartCookie", true);
            //转换成cartInfo的对象
            cartInfos = JSON.parseArray(listCartCookieStr, CartInfo.class);
            //cartInfos不存在，为null
            if (StringUtils.isBlank(listCartCookieStr)) {
                cartInfos = new ArrayList<>();
                cartInfos.add(cartInfo);
            } else {
                //判断cartinfos中是否存在同一个cartinfo，即判断skuid
                boolean b = if_new_cart(cartInfos, cartInfo);
                //不存在，直接添加到cartInfos中
                if (b) {
                    cartInfos.add(cartInfo);
                } else {

                    for (CartInfo info : cartInfos) {
                        if (info.getSkuId().equals(skuId)) {
                            //修改skunum和price
                            info.setSkuNum(info.getSkuNum() + cartInfo.getSkuNum());
                            info.setCartPrice(info.getSkuPrice().multiply(new BigDecimal(info.getSkuNum())));
                        }

                    }
                }
            }
            //把cartinfos添加到cookie中
            CookieUtil.setCookie(request, response, "listCartCookie", JSON.toJSONString(cartInfos), 1000 * 60 * 60 * 24, true);

        } else {
            CartInfo exists = new CartInfo();
            exists.setSkuId(skuId);
            exists.setUserId(userId);
            //用户已登陆，操作db
            CartInfo ifCart = cartService.exists(exists);
            if (ifCart == null) {
                //不存在，直接保存你
                cartService.saveCart(cartInfo);
            } else {
                //ifcart存在，进行更新，
                ifCart.setSkuNum(ifCart.getSkuNum() + num);
                ifCart.setCartPrice(ifCart.getSkuPrice().multiply(new BigDecimal(ifCart.getSkuNum())));
                cartService.updateCart(ifCart);
            }
            //刷新redis缓存
            cartService.flushCartCacheByUser(userId);
        }
        //重定向到购物车页面
        return "redirect:http://cart.gmall.com:8084/success.html";
    }

    private boolean if_new_cart(List<CartInfo> cartInfos, CartInfo cartInfo) {
        boolean b = true;
        //遍历购物车所有cartInfo
        for (CartInfo info : cartInfos) {
            String skuId = info.getSkuId();
            //存在相等skuId，旧车，需要对数量和cartprice进行修改
            if (skuId.equals(cartInfo.getSkuId())) {
                b = false;
            }
        }
        return b;
    }
}
