package cc.mrbird.febs.zq.service;

import cc.mrbird.febs.zq.entity.ZqBCopyPublicarticle;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author viki
 * @since 2022-06-24
 */
public interface IZqBCopyPublicarticleService extends IService<ZqBCopyPublicarticle> {

        IPage<ZqBCopyPublicarticle> findZqBCopyPublicarticles(QueryRequest request, ZqBCopyPublicarticle zqBCopyPublicarticle);

        IPage<ZqBCopyPublicarticle> findZqBCopyPublicarticleList(QueryRequest request, ZqBCopyPublicarticle zqBCopyPublicarticle);

        void createZqBCopyPublicarticle(ZqBCopyPublicarticle zqBCopyPublicarticle);

        void updateZqBCopyPublicarticle(ZqBCopyPublicarticle zqBCopyPublicarticle);

        void deleteZqBCopyPublicarticles(String[]Ids);

        List<ZqBCopyPublicarticle> getAll(String userAccount,String dcaYear);
        }
