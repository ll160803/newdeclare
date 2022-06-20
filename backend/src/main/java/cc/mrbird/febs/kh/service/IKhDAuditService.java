package cc.mrbird.febs.kh.service;

import cc.mrbird.febs.kh.entity.KhDAudit;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 打分人 服务类
 * </p>
 *
 * @author viki
 * @since 2022-05-12
 */
public interface IKhDAuditService extends IService<KhDAudit> {

        IPage<KhDAudit> findKhDAudits(QueryRequest request, KhDAudit khDAudit);

        IPage<KhDAudit> findKhDAuditList(QueryRequest request, KhDAudit khDAudit);

        void createKhDAudit(KhDAudit khDAudit);

        void updateKhDAudit(KhDAudit khDAudit);

        void deleteKhDAudits(String[]Ids);

        List<KhDAudit> getAll(String userAccount,String dcaYear);
        }
