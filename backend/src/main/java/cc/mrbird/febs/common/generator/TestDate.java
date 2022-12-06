package cc.mrbird.febs.common.generator;

import cc.mrbird.febs.common.domain.FebsResponse;
import cc.mrbird.febs.common.utils.SnowIdUtils;
import cc.mrbird.febs.scm.RFC.BackInfo;
import cc.mrbird.febs.scm.RFC.RfcNOC;
import cn.hutool.core.lang.Snowflake;
import com.ruiyun.jvppeteer.core.Puppeteer;
import com.ruiyun.jvppeteer.core.browser.Browser;
import com.ruiyun.jvppeteer.core.browser.BrowserFetcher;
import com.ruiyun.jvppeteer.core.page.Page;
import com.ruiyun.jvppeteer.options.LaunchOptions;
import com.ruiyun.jvppeteer.options.LaunchOptionsBuilder;
import com.ruiyun.jvppeteer.options.PDFOptions;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class TestDate {

    public static void main(String[] args) throws IOException, InterruptedException, ParseException, ExecutionException {
//        String stringDate = "Sun Sep 15 1991 00:00:00 GMT+0800";
//
//        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM ddHH:mm:ss 'GMT' yyyy", Locale.US);
//
//        Date date =sdf.parse(stringDate);
//
////     System.out.println(date.toString());
//
//        sdf=new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");

       BigDecimal a= new BigDecimal("344.555").setScale(2,BigDecimal.ROUND_DOWN);
        System.out.println(a.toString());
    }
}
