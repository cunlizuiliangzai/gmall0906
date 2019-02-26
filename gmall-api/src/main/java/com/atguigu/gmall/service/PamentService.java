package com.atguigu.gmall.service;

import com.atguigu.gmall.bean.PaymentInfo;

public interface PamentService {
    void save(PaymentInfo paymentInfo);

    void sendDelayPaymentCheck(String outTradeNo, int i);

    boolean checkPaymentStatus(String out_trade_no);

    void updatePayment(PaymentInfo paymentInfo);

    void sendPaymentSuccess(String outTradeNo, String paymentStatus, String trade_no);

    PaymentInfo checkPaymentResult(String out_trade_no);
}
