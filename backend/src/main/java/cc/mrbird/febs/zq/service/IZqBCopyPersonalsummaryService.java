package cc.mrbird.febs.zq.service;

import cc.mrbird.febs.zq.entity.ZqBCopyPersonalsummary;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 个人总结 服务类
 * </p>
 *
 * @author viki
 * @since 2022-06-24
 */
public interface IZqBCopyPersonalsummaryService extends IService<ZqBCopyPersonalsummary> {

        IPage<ZqBCopyPersonalsummary> findZqBCopyPersonalsummarys(QueryRequest request, ZqBCopyPersonalsummary zqBCopyPersonalsummary);

        IPage<ZqBCopyPersonalsummary> findZqBCopyPersonalsummaryList(QueryRequest request, ZqBCopyPersonalsummary zqBCopyPersonalsummary);

        void createZqBCopyPersonalsummary(ZqBCopyPersonalsummary zqBCopyPersonalsummary);

        void updateZqBCopyPersonalsummary(ZqBCopyPersonalsummary zqBCopyPersonalsummary);

        void deleteZqBCopyPersonalsummarys(String[]Ids);

        List<ZqBCopyPersonalsummary> getAll(String userAccount,String dcaYear);
        }
