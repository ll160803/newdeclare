package cc.mrbird.febs.doctor.dao;

import cc.mrbird.febs.doctor.entity.DcaBDocAuditfivemonth;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 月度考核 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2022-11-14
 */
public interface DcaBDocAuditfivemonthMapper extends BaseMapper<DcaBDocAuditfivemonth> {
        void updateDcaBDocAuditfivemonth(DcaBDocAuditfivemonth dcaBDocAuditfivemonth);
        IPage<DcaBDocAuditfivemonth> findDcaBDocAuditfivemonth(Page page, @Param("dcaBDocAuditfivemonth") DcaBDocAuditfivemonth dcaBDocAuditfivemonth);

        @Delete("delete   from xhdoctor.dca_b_doc_auditfivemonth")
        void deleteAll();
        }
