package cc.mrbird.febs.doctor.dao;

import cc.mrbird.febs.doctor.entity.DcaBDocAuditfiveother;
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
public interface DcaBDocAuditfiveotherMapper extends BaseMapper<DcaBDocAuditfiveother> {
        void updateDcaBDocAuditfiveother(DcaBDocAuditfiveother dcaBDocAuditfiveother);
        IPage<DcaBDocAuditfiveother> findDcaBDocAuditfiveother(Page page, @Param("dcaBDocAuditfiveother") DcaBDocAuditfiveother dcaBDocAuditfiveother);

        @Delete("delete   from xhdoctor.dca_b_doc_auditfiveother")
       void deleteAll();
        }
