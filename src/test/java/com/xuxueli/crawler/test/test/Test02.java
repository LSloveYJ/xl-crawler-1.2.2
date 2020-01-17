package com.xuxueli.crawler.test.test;

import cn.hutool.core.collection.CollUtil;
import com.xuxueli.crawler.XxlCrawler;
import com.xuxueli.crawler.annotation.PageFieldSelect;
import com.xuxueli.crawler.annotation.PageSelect;
import com.xuxueli.crawler.conf.XxlCrawlerConf;
import com.xuxueli.crawler.loader.strategy.SeleniumPhantomjsPageLoader;
import com.xuxueli.crawler.parser.PageParser;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 爬虫示例08：JS渲染方式采集数据，"selenisum + phantomjs" 方案
 * (仅供学习测试使用，如有侵犯请联系删除； )
 *
 * @author xuxueli 2018-10-16
 */
public class Test02 {

    private static Logger logger = LoggerFactory.getLogger(Test02.class);


    @PageSelect(cssQuery = "table .ip")
    public static class PageVo {

        @PageFieldSelect(cssQuery = ".ip :not([style~=none]):not(.port)")
        private List<String> ip;


        @PageFieldSelect(cssQuery = ".ip .port")
        private String port;


        public List<String> getIp() {
            return ip;
        }

        public void setIp(List<String> ip) {
            this.ip = ip;
        }

        public String getPort() {
            return port;
        }

        public void setPort(String port) {
            this.port = port;
        }
    }

    public static void main(String[] args) {
        String driverPath = "E:\\soft\\phantomjs-2.1.1-windows\\bin\\phantomjs.exe";
        // 构造爬虫
        XxlCrawler crawler = new XxlCrawler.Builder()
                .setUrls("http://www.goubanjia.com/")
                .setAllowSpread(false)
                .setPageLoader(new SeleniumPhantomjsPageLoader(driverPath))        // "selenisum + phantomjs" 版本 PageLoader：支持 JS 渲染
                .setPageParser(new PageParser<PageVo>() {
                    @Override
                    public void parse(Document html, Element pageVoElement, PageVo pageVo) {
                        System.out.println(CollUtil.join(pageVo.getIp(), "") + ":" + pageVo.getPort());
                    }
                })
                .build();
        // 启动
        crawler.start(true);
    }
}
