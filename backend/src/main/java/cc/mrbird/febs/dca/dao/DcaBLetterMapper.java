package cc.mrbird.febs.dca.dao;

import cc.mrbird.febs.dca.entity.DcaBLetter;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 通讯评审 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2022-07-26
 */
public interface DcaBLetterMapper extends BaseMapper<DcaBLetter> {
        void updateDcaBLetter(DcaBLetter dcaBLetter);
        IPage<DcaBLetter> findDcaBLetter(Page page, @Param("dcaBLetter") DcaBLetter dcaBLetter);

@Update(" update dca_b_letter set IS_DELETEMARK=0 where user_account=#{useraccount}  and (state=0 or state=2)")
        void deleteByAccount(@Param("useraccount") String useraccount);
@Select(" select IFNULL(max(display_index),0) As maxIndex from  dca_b_letter  where user_account=#{useraccount} ")
        int getMaxDisplayIndexByuseraccount(@Param("useraccount") String useraccount);
        }
