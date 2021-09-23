package cc.mrbird.febs.dca.dao;

import cc.mrbird.febs.dca.entity.DcaDYj;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 学术业绩 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2021-09-23
 */
public interface DcaDYjMapper extends BaseMapper<DcaDYj> {
        void updateDcaDYj(DcaDYj dcaDYj);
        IPage<DcaDYj> findDcaDYj(Page page, @Param("dcaDYj") DcaDYj dcaDYj);

@Update(" update dca_d_yj set IS_DELETEMARK=0 where user_account=#{useraccount}  and (state=0 or state=2)")
        void deleteByAccount(@Param("useraccount") String useraccount);
@Select(" select IFNULL(max(display_index),0) As maxIndex from  dca_d_yj  where user_account=#{useraccount} ")
        int getMaxDisplayIndexByuseraccount(@Param("useraccount") String useraccount);
        }
