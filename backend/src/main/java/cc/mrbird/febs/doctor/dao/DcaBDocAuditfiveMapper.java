package cc.mrbird.febs.doctor.dao;

import cc.mrbird.febs.doctor.entity.DcaBDocAuditfive;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 年度考核 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2022-11-14
 */
public interface DcaBDocAuditfiveMapper extends BaseMapper<DcaBDocAuditfive> {
        void updateDcaBDocAuditfive(DcaBDocAuditfive dcaBDocAuditfive);
        IPage<DcaBDocAuditfive> findDcaBDocAuditfive(Page page, @Param("dcaBDocAuditfive") DcaBDocAuditfive dcaBDocAuditfive);

        @Delete("delete  from xhdoctor.dca_b_doc_auditfive")
        void deleteAll();
        }
