package cc.mrbird.febs.kh.service;

import cc.mrbird.febs.kh.entity.KhBCopyPublicarticle;
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
 * @since 2022-05-13
 */
public interface IKhBCopyPublicarticleService extends IService<KhBCopyPublicarticle> {

        IPage<KhBCopyPublicarticle> findKhBCopyPublicarticles(QueryRequest request, KhBCopyPublicarticle khBCopyPublicarticle);

        IPage<KhBCopyPublicarticle> findKhBCopyPublicarticleList(QueryRequest request, KhBCopyPublicarticle khBCopyPublicarticle);

        void createKhBCopyPublicarticle(KhBCopyPublicarticle khBCopyPublicarticle);

        void updateKhBCopyPublicarticle(KhBCopyPublicarticle khBCopyPublicarticle);

        void deleteKhBCopyPublicarticles(String[]Ids);

        List<KhBCopyPublicarticle> getAll(String userAccount,String dcaYear);
        }
