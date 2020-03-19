package com.qf.controller;

import com.qf.entity.TGoodsInfo;
import com.qf.service.SearchWebService;
import com.qf.vo.GoodsInfoVO;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class SearchWebController {

    @Autowired
    private SearchWebService searchWebService;
    @Autowired
    private SolrClient solrClient;

    @RequestMapping("setSolr")
    @ResponseBody
    public String setSolr(){
        List<TGoodsInfo> goodsInfos = searchWebService.selectAll();
        //存放所有的doc集合
        List<SolrInputDocument> dtoList = new ArrayList<>();

        //遍历goodsInfos集合，将每一个goodsInfo对象封装成一个SolrInputDocument对象
        for (TGoodsInfo goodsInfo : goodsInfos) {
            SolrInputDocument doc = new SolrInputDocument();

            doc.setField("id",goodsInfo.getId());
            doc.setField("t_goods_description",goodsInfo.getGoodsDescription());
            doc.setField("t_goods_price",goodsInfo.getGoodsPrice());
            doc.setField("t_goods_pic",goodsInfo.getGoodsPic());
            doc.setField("t_goods_stock",goodsInfo.getGoodsStock());

            dtoList.add(doc);
        }

        //将该集合添加到solr库中
        try {
            solrClient.add(dtoList);
            solrClient.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "ok";
    }

    @RequestMapping("showSearch")
    public String showSearch(@RequestParam String keywords, ModelMap map) throws IOException, SolrServerException {
        //先设置查询参数，创建一个solrQuery对象
        SolrQuery query = new SolrQuery();
        query.setQuery(keywords);
        //复制域
        query.set("df","t_item_keywords");
        //设置分页
        query.setStart(0);
        query.setRows(10);

        //开启高亮
        query.setHighlight(true);
        //添加高亮字段
        query.addHighlightField("t_goods_description");
        query.setHighlightSimplePre("<span style='color:red'>");
        query.setHighlightSimplePost("</span>");

        //提交给solrClient执行查询并得到结果集
        QueryResponse response = solrClient.query(query);
        SolrDocumentList docs = response.getResults();
        Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();

        List<GoodsInfoVO> list = new ArrayList<>();

        for (SolrDocument doc : docs) {

            GoodsInfoVO goods = new GoodsInfoVO();

            String id = (String) doc.getFieldValue("id");
            System.out.println(id);
            Long id_L = Long.parseLong(id);
            goods.setId(Math.toIntExact(id_L));

            //=======高亮的商品名称的封装==========
            Map<String, List<String>> stringListMap = highlighting.get(id);
            List<String> t_goods_descriptions = stringListMap.get("t_goods_description");
            String goodsDescription = t_goods_descriptions.get(0);
            goods.setGoodsDescription(goodsDescription);
            //=======高亮end=======

            String t_goods_pic = (String) doc.getFieldValue("t_goods_pic");
            goods.setGoodsPic(t_goods_pic);

            Double t_goods_price = (Double) doc.getFieldValue("t_goods_price");
            goods.setGoodsPrice(t_goods_price);

            Long t_goods_stock = (Long) doc.getFieldValue("t_goods_stock");
            goods.setGoodsStock(Math.toIntExact(t_goods_stock));


            list.add(goods);
        }

        map.put("keywords",keywords);
        map.put("GoodsInfos",list);
        return "search";
    }

    @RequestMapping("showIntroduct")
    public String showIntroduct(@RequestParam Integer id, ModelMap map){
        TGoodsInfo GoodsInfo = searchWebService.selectByID(id);
        map.put("GoodsInfo",GoodsInfo);
        return "introduction";
    }
}
