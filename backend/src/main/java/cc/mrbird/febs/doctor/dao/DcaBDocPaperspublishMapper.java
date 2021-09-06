package cc.mrbird.febs.doctor.dao;

import cc.mrbird.febs.doctor.entity.DcaBDocPaperspublish;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 教学论文出版教材 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2021-01-11
 */
public interface DcaBDocPaperspublishMapper extends BaseMapper<DcaBDocPaperspublish> {
        void updateDcaBDocPaperspublish(DcaBDocPaperspublish dcaBDocPaperspublish);
        IPage<DcaBDocPaperspublish> findDcaBDocPaperspublish(Page page, @Param("dcaBDocPaperspublish") DcaBDocPaperspublish dcaBDocPaperspublish);

@Update(" update dca_b_doc_paperspublish set IS_DELETEMARK=0 where user_account=#{useraccount}  and (state=0 or state=2)")
        void deleteByAccount(@Param("useraccount") String useraccount);
@Select(" select IFNULL(max(display_index),0) As maxIndex from  dca_b_doc_paperspublish  where user_account=#{useraccount} ")
        int getMaxDisplayIndexByuseraccount(@Param("useraccount") String useraccount);
        }
