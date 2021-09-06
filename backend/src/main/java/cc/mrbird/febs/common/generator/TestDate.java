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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class TestDate {

    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
        RfcNOC rfcNOC =new RfcNOC();
        List<BackInfo> backInfoList=  rfcNOC.GetInfoList("9457A555D8DC1EDBB2CAF0A0DD31681A");
        System.out.print(backInfoList.get(0).getNACHN());
    }
}
