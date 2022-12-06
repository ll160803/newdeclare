package cc.mrbird.febs.doctor.service;

import cc.mrbird.febs.doctor.entity.DcaBDocAuditfive;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 年度考核 服务类
 * </p>
 *
 * @author viki
 * @since 2022-11-14
 */
public interface IDcaBDocAuditfiveService extends IService<DcaBDocAuditfive> {

        IPage<DcaBDocAuditfive> findDcaBDocAuditfives(QueryRequest request, DcaBDocAuditfive dcaBDocAuditfive);

        IPage<DcaBDocAuditfive> findDcaBDocAuditfiveList(QueryRequest request, DcaBDocAuditfive dcaBDocAuditfive);

        void createDcaBDocAuditfive(DcaBDocAuditfive dcaBDocAuditfive);

        void updateDcaBDocAuditfive(DcaBDocAuditfive dcaBDocAuditfive);

        void deleteDcaBDocAuditfives(String[]Ids);

        List<DcaBDocAuditfive> getAll(String userAccount,String dcaYear);

        void deleteAll();
        }
