package cc.mrbird.febs.doctor.service;

import cc.mrbird.febs.doctor.entity.DcaBDocAuditfivemonth;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 月度考核 服务类
 * </p>
 *
 * @author viki
 * @since 2022-11-14
 */
public interface IDcaBDocAuditfivemonthService extends IService<DcaBDocAuditfivemonth> {

        IPage<DcaBDocAuditfivemonth> findDcaBDocAuditfivemonths(QueryRequest request, DcaBDocAuditfivemonth dcaBDocAuditfivemonth);

        IPage<DcaBDocAuditfivemonth> findDcaBDocAuditfivemonthList(QueryRequest request, DcaBDocAuditfivemonth dcaBDocAuditfivemonth);

        void createDcaBDocAuditfivemonth(DcaBDocAuditfivemonth dcaBDocAuditfivemonth);

        void updateDcaBDocAuditfivemonth(DcaBDocAuditfivemonth dcaBDocAuditfivemonth);

        void deleteDcaBDocAuditfivemonths(String[]Ids);

        List<DcaBDocAuditfivemonth> getAll(String userAccount,String dcaYear);
        void deleteAll();
        }
