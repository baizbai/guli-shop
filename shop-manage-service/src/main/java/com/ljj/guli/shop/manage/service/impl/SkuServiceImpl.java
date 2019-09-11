package com.ljj.guli.shop.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.ljj.guli.shop.bean.PmsSkuAttrValue;
import com.ljj.guli.shop.bean.PmsSkuImage;
import com.ljj.guli.shop.bean.PmsSkuInfo;
import com.ljj.guli.shop.bean.PmsSkuSaleAttrValue;
import com.ljj.guli.shop.manage.mapper.PmsSkuAttrValueMapper;
import com.ljj.guli.shop.manage.mapper.PmsSkuImageMapper;
import com.ljj.guli.shop.manage.mapper.PmsSkuInfoMapper;
import com.ljj.guli.shop.manage.mapper.PmsSkuSaleAttrValueMapper;
import com.ljj.guli.shop.service.SkuService;
import com.ljj.guli.shop.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Objects;

@Service
public class SkuServiceImpl implements SkuService {

    @Autowired
    PmsSkuInfoMapper pmsSkuInfoMapper;

    @Autowired
    PmsSkuAttrValueMapper pmsSkuAttrValueMapper;

    @Autowired
    PmsSkuSaleAttrValueMapper pmsSkuSaleAttrValueMapper;

    @Autowired
    PmsSkuImageMapper pmsSkuImageMapper;

    @Autowired
    RedisUtil redisUtil;


    @Override
    public void saveSkuInfo(PmsSkuInfo pmsSkuInfo) {

        // 插入skuInfo
        int i = pmsSkuInfoMapper.insertSelective(pmsSkuInfo);
        String skuId = pmsSkuInfo.getId();

        // 插入平台属性关联
        List<PmsSkuAttrValue> skuAttrValueList = pmsSkuInfo.getSkuAttrValueList();
        for (PmsSkuAttrValue pmsSkuAttrValue : skuAttrValueList) {
            pmsSkuAttrValue.setSkuId(skuId);
            pmsSkuAttrValueMapper.insertSelective(pmsSkuAttrValue);
        }

        // 插入销售属性关联
        List<PmsSkuSaleAttrValue> skuSaleAttrValueList = pmsSkuInfo.getSkuSaleAttrValueList();
        for (PmsSkuSaleAttrValue pmsSkuSaleAttrValue : skuSaleAttrValueList) {
            pmsSkuSaleAttrValue.setSkuId(skuId);
            pmsSkuSaleAttrValueMapper.insertSelective(pmsSkuSaleAttrValue);
        }

        // 插入图片信息
        List<PmsSkuImage> skuImageList = pmsSkuInfo.getSkuImageList();
        for (PmsSkuImage pmsSkuImage : skuImageList) {
            pmsSkuImage.setSkuId(skuId);
            pmsSkuImageMapper.insertSelective(pmsSkuImage);
        }


    }

    public PmsSkuInfo getSkuByIdFormDB(String skuId) {
        PmsSkuInfo psi = new PmsSkuInfo();
        psi.setId(skuId);
        PmsSkuInfo pmsSkuInfo = pmsSkuInfoMapper.selectOne(psi);

        // 加载图片列表
        PmsSkuImage pmsSkuImage = new PmsSkuImage();
        pmsSkuImage.setSkuId(skuId);
        List<PmsSkuImage> images = pmsSkuImageMapper.select(pmsSkuImage);
        pmsSkuInfo.setSkuImageList(images);
        return pmsSkuInfo;
    }

    @Override
    public PmsSkuInfo getSkuById(String skuId) {
        PmsSkuInfo psi = new PmsSkuInfo();

        // 连接缓存
        Jedis jedis = redisUtil.getJedis();
        // 查询缓存
        String skuKey = "sku:" + skuId + ":info";
        String skuJson = jedis.get(skuKey);

        if (StringUtils.isNotBlank(skuJson)) {
            psi = JSON.parseObject(skuJson, PmsSkuInfo.class);
        } else {
            // 存入一个不可重复，有失效时间的key,作为锁
            String OK = jedis.set("sku:" + skuId + ":lock", "1", "nx", "px", 10);
            // 存入成功
            if (StringUtils.equals("OK", OK)) {
                // 查询数据库
                psi = getSkuByIdFormDB(skuId);
                // 数据库中可以查询到数据就正常存入redis
                if (Objects.nonNull(psi)) {
                    jedis.set("sku:" + skuId + ":info", JSON.toJSONString(psi));
                }
                // 数据库查询不到数据，就存入一个空对象，设置过期时间，防止穿透
                else{
                    jedis.setex("sku:" + skuId + ":info",60*3, JSON.toJSONString(""));
                }
            }
            // 存入失败
            else {
                // 自旋（线程睡眠一段时间后重新访问本方法）
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // 必须使用 return，不然会新建一个新的线程导致当前线程无结果，新线程成为孤儿线程
                return getSkuByIdFormDB(skuId);
            }
        }

        jedis.close();

        return psi;
    }

    @Override
    public List<PmsSkuInfo> getSkuSaleAttrValueListBySpu(String productId) {

        List<PmsSkuInfo> pmsSkuInfos = pmsSkuInfoMapper.selectSkuSaleAttrValueListBySpu(productId);

        return pmsSkuInfos;
    }
}
