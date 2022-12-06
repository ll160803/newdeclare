package cc.mrbird.febs.doctor.dao;

import cc.mrbird.febs.doctor.entity.DcaBDocAuditfivemiddle;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 中期考核 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2022-11-14
 */
public interface DcaBDocAuditfivemiddleMapper extends BaseMapper<DcaBDocAuditfivemiddle> {
        void updateDcaBDocAuditfivemiddle(DcaBDocAuditfivemiddle dcaBDocAuditfivemiddle);
        IPage<DcaBDocAuditfivemiddle> findDcaBDocAuditfivemiddle(Page page, @Param("dcaBDocAuditfivemiddle") DcaBDocAuditfivemiddle dcaBDocAuditfivemiddle);
        @Delete("delete  from xhdoctor.dca_b_doc_auditfivemiddle")
        void deleteAll();
        }
