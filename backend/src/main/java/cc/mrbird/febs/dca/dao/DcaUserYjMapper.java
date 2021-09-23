package cc.mrbird.febs.dca.dao;

import cc.mrbird.febs.dca.entity.DcaUserYj;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 学术业绩用户表 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2021-09-23
 */
public interface DcaUserYjMapper extends BaseMapper<DcaUserYj> {
        void updateDcaUserYj(DcaUserYj dcaUserYj);
        IPage<DcaUserYj> findDcaUserYj(Page page, @Param("dcaUserYj") DcaUserYj dcaUserYj);

@Update(" update dca_user_yj set IS_DELETEMARK=0 where user_account=#{useraccount}  and (state=0 or state=2)")
        void deleteByAccount(@Param("useraccount") String useraccount);
@Select(" select IFNULL(max(display_index),0) As maxIndex from  dca_user_yj  where user_account=#{useraccount} ")
        int getMaxDisplayIndexByuseraccount(@Param("useraccount") String useraccount);

        @Delete("delete from dca_user_yj where user_id =#{userId} and dca_year=#{year}")
       void deleteByuserid(@Param("userId") String userId,@Param("year") String year);
        }
