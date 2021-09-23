package cc.mrbird.febs.dcacopy.dao;

import cc.mrbird.febs.dcacopy.entity.DcaBCopySureachievement;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 医疗认可 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2021-09-15
 */
public interface DcaBCopySureachievementMapper extends BaseMapper<DcaBCopySureachievement> {
        void updateDcaBCopySureachievement(DcaBCopySureachievement dcaBCopySureachievement);
        IPage<DcaBCopySureachievement> findDcaBCopySureachievement(Page page, @Param("dcaBCopySureachievement") DcaBCopySureachievement dcaBCopySureachievement);

@Update(" update dca_b_copy_sureachievement set IS_DELETEMARK=0 where user_account=#{useraccount}  and (state=0 or state=2)")
        void deleteByAccount(@Param("useraccount") String useraccount);
@Select(" select IFNULL(max(display_index),0) As maxIndex from  dca_b_copy_sureachievement  where user_account=#{useraccount} ")
        int getMaxDisplayIndexByuseraccount(@Param("useraccount") String useraccount);
        }
