package cc.mrbird.febs.dcacopy.dao;

import cc.mrbird.febs.dcacopy.entity.DcaBCopyTeacheryj;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 主要教学业绩 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2021-09-14
 */
public interface DcaBCopyTeacheryjMapper extends BaseMapper<DcaBCopyTeacheryj> {
        void updateDcaBCopyTeacheryj(DcaBCopyTeacheryj dcaBCopyTeacheryj);
        IPage<DcaBCopyTeacheryj> findDcaBCopyTeacheryj(Page page, @Param("dcaBCopyTeacheryj") DcaBCopyTeacheryj dcaBCopyTeacheryj);

@Update(" update dca_b_copy_teacheryj set IS_DELETEMARK=0 where user_account=#{useraccount}  and (state=0 or state=2)")
        void deleteByAccount(@Param("useraccount") String useraccount);
@Select(" select IFNULL(max(display_index),0) As maxIndex from  dca_b_copy_teacheryj  where user_account=#{useraccount} ")
        int getMaxDisplayIndexByuseraccount(@Param("useraccount") String useraccount);
        }
