package com.xuxueli.crawler.test.test;

import cn.hutool.core.collection.CollUtil;
import com.xuxueli.crawler.XxlCrawler;
import com.xuxueli.crawler.annotation.PageFieldSelect;
import com.xuxueli.crawler.annotation.PageSelect;
import com.xuxueli.crawler.conf.XxlCrawlerConf;
import com.xuxueli.crawler.parser.PageParser;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by slovej on 2020/1/16.
 */
public class Test01 {

    @PageSelect(cssQuery = ".table .ip")
    public static class PageVo {

        @PageFieldSelect(cssQuery = ".ip :not([style~=none]):not(.port)")
        private List<String> ip;


        @PageFieldSelect(cssQuery = ".ip .port",selectType = XxlCrawlerConf.SelectType.ATTR, selectVal = "class")
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

    // 快代理
//    public static void main(String[] args) {
//        XxlCrawler build = new XxlCrawler.Builder()
//                .setUrls("https://www.kuaidaili.com/free/inha/1/")
////                .setWhiteUrlRegexs("https://www.kuaidaili.com/free/inha/d[1-2]/")
//                .setThreadCount(1)
//                .setPageParser(new PageParser<PageVo>() {
//                    @Override
//                    public void parse(Document html, Element pageVoElement, PageVo pageVo) {
//                        System.out.println(pageVo.getIp());
//                    }
//                }).build();
//        build.start(true);
//    }

    // 全网IP
    public static void main(String[] args) {
        XxlCrawler build = new XxlCrawler.Builder()
                .setUrls("http://www.goubanjia.com")
                .setThreadCount(1)
                .setPageParser(new PageParser<PageVo>() {
                    @Override
                    public void parse(Document html, Element pageVoElement, PageVo pageVo) {
                        System.out.println(CollUtil.join(pageVo.getIp(), ""));
                        System.out.println(changePort(pageVo.getPort()));
                    }
                }).build();

        build.start(true);
    }


    public static int changePort(String port) {
        String rawNum = Stream.of(port.split(" ")[1].split(""))
                .map("ABCDEFGHIZ"::indexOf)
                .map(Object::toString)
                .collect(Collectors.joining());
        return Integer.parseInt(rawNum) >> 3;
    }

}
