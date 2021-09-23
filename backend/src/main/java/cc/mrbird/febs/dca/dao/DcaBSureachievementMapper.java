package cc.mrbird.febs.dca.dao;

import cc.mrbird.febs.dca.entity.DcaBSureachievement;
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
public interface DcaBSureachievementMapper extends BaseMapper<DcaBSureachievement> {
        void updateDcaBSureachievement(DcaBSureachievement dcaBSureachievement);
        IPage<DcaBSureachievement> findDcaBSureachievement(Page page, @Param("dcaBSureachievement") DcaBSureachievement dcaBSureachievement);

@Update(" update dca_b_sureachievement set IS_DELETEMARK=0 where user_account=#{useraccount}  and (state=0 or state=2)")
        void deleteByAccount(@Param("useraccount") String useraccount);
@Select(" select IFNULL(max(display_index),0) As maxIndex from  dca_b_sureachievement  where user_account=#{useraccount} ")
        int getMaxDisplayIndexByuseraccount(@Param("useraccount") String useraccount);
        }
