package cc.mrbird.febs.doctor.service;

import cc.mrbird.febs.doctor.entity.DcaBDocAuditfivemiddle;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 中期考核 服务类
 * </p>
 *
 * @author viki
 * @since 2022-11-14
 */
public interface IDcaBDocAuditfivemiddleService extends IService<DcaBDocAuditfivemiddle> {

        IPage<DcaBDocAuditfivemiddle> findDcaBDocAuditfivemiddles(QueryRequest request, DcaBDocAuditfivemiddle dcaBDocAuditfivemiddle);

        IPage<DcaBDocAuditfivemiddle> findDcaBDocAuditfivemiddleList(QueryRequest request, DcaBDocAuditfivemiddle dcaBDocAuditfivemiddle);

        void createDcaBDocAuditfivemiddle(DcaBDocAuditfivemiddle dcaBDocAuditfivemiddle);

        void updateDcaBDocAuditfivemiddle(DcaBDocAuditfivemiddle dcaBDocAuditfivemiddle);

        void deleteDcaBDocAuditfivemiddles(String[]Ids);

        List<DcaBDocAuditfivemiddle> getAll(String userAccount,String dcaYear);
        void deleteAll();
        }
