package com.atguigu.gmall.cart.serviceImpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.atguigu.gmall.bean.CartInfo;
import com.atguigu.gmall.cart.mapper.CartInfoMapper;
import com.atguigu.gmall.service.CartService;
import com.atguigu.gmall.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    CartInfoMapper cartInfoMapper;
    @Autowired
    RedisUtil redisUtil;




    @Override
    public CartInfo exists(CartInfo exists) {
        //传入cartinfo对象，判断是否存在
        CartInfo cartInfo = cartInfoMapper.selectOne(exists);
        return cartInfo;
    }

    @Override
    public void saveCart(CartInfo cartInfo) {
        //保存
        cartInfoMapper.insertSelective(cartInfo);
    }

    @Override
    public void updateCart(CartInfo ifCart) {

        Example e = new Example(CartInfo.class);
        //相当于执行update set 字段 = ？，字段 = ？
        e.createCriteria().andEqualTo("userId",ifCart.getUserId()).andEqualTo("skuId",ifCart.getSkuId());
        //更新cartInfo对象
        cartInfoMapper.updateByExampleSelective(ifCart,e);
    }

    @Override
    public void flushCartCacheByUser(String userId) {
        //通过userId获取当下的cartinfos
        List<CartInfo> cartInfos = getCartInfosByUserId(userId);
        //获取jedis对象
        Jedis jedis = redisUtil.getJedis();
        if(cartInfos != null){
            Map<String,String> stringStringMap = new HashMap<>();
            for (CartInfo cartInfo : cartInfos) {
                stringStringMap.put(cartInfo.getId(), JSON.toJSONString(cartInfo));
            }
            jedis.hmset("cart:"+userId+":info",stringStringMap);
        }
        jedis.close();
    }

    private List<CartInfo> getCartInfosByUserId(String userId) {
        CartInfo cartInfo = new CartInfo();
        cartInfo.setUserId(userId);
        List<CartInfo> cartInfos = cartInfoMapper.select(cartInfo);
        return cartInfos;
    }

    @Override
    public List<CartInfo> cartListFromCache(String userId) {
        Jedis jedis = redisUtil.getJedis();
        List<CartInfo> cartInfos = new ArrayList<>();

        List<String> hvals = jedis.hvals("cart:" + userId + ":info");
        for (String hval : hvals) {
            CartInfo cartInfo = JSON.parseObject(hval, CartInfo.class);
            cartInfos.add(cartInfo);
        }
        return cartInfos;
    }

    @Override
    public void mergCart(String listCartCookie, String userId) {
        List<CartInfo> cartInfosByDB = getCartInfosByUserId(userId);
        List<CartInfo> cartInfosByCookie = JSON.parseArray(listCartCookie, CartInfo.class);
        for (CartInfo cartInfo : cartInfosByCookie) {
            boolean b = if_new_cart(cartInfosByDB, cartInfo);
            if(b){
                cartInfo.setUserId(userId);
                cartInfoMapper.insertSelective(cartInfo);
            }else{
                for (CartInfo info : cartInfosByDB) {
                    if(info.getSkuId().equals(cartInfo.getSkuId())){
                      info.setSkuNum(info.getSkuNum()+cartInfo.getSkuNum());
                      info.setCartPrice(info.getCartPrice().add(cartInfo.getCartPrice()));
                      cartInfoMapper.updateByPrimaryKeySelective(info);
                    }
                }
            }
        }


    }

    private boolean if_new_cart(List<CartInfo> cartInfos,CartInfo cartInfo){

        boolean b = true;
        for (CartInfo info : cartInfos) {

            if(info.getSkuId().equals(cartInfo.getSkuId())){
                b = false;
            }
        }

        return b;
    }

}
