package cc.mrbird.febs.kh.service;

import cc.mrbird.febs.kh.entity.KhBCopyPersonalsummary;
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
 * @since 2022-05-12
 */
public interface IKhBCopyPersonalsummaryService extends IService<KhBCopyPersonalsummary> {

        IPage<KhBCopyPersonalsummary> findKhBCopyPersonalsummarys(QueryRequest request, KhBCopyPersonalsummary khBCopyPersonalsummary);

        IPage<KhBCopyPersonalsummary> findKhBCopyPersonalsummaryList(QueryRequest request, KhBCopyPersonalsummary khBCopyPersonalsummary);

        void createKhBCopyPersonalsummary(KhBCopyPersonalsummary khBCopyPersonalsummary);

        void updateKhBCopyPersonalsummary(KhBCopyPersonalsummary khBCopyPersonalsummary);

        void deleteKhBCopyPersonalsummarys(String[]Ids);

        List<KhBCopyPersonalsummary> getAll(String userAccount,String dcaYear);
        }
