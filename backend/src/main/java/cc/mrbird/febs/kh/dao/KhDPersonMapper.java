package cc.mrbird.febs.kh.dao;

import cc.mrbird.febs.kh.entity.KhDPerson;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 被打分人 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2022-05-12
 */
public interface KhDPersonMapper extends BaseMapper<KhDPerson> {
        void updateKhDPerson(KhDPerson khDPerson);
        IPage<KhDPerson> findKhDPerson(Page page, @Param("khDPerson") KhDPerson khDPerson);
        }
