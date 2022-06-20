package cc.mrbird.febs.kh.service;

import cc.mrbird.febs.kh.entity.KhBCopySciencepublish;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 科研论文 服务类
 * </p>
 *
 * @author viki
 * @since 2022-05-12
 */
public interface IKhBCopySciencepublishService extends IService<KhBCopySciencepublish> {

        IPage<KhBCopySciencepublish> findKhBCopySciencepublishs(QueryRequest request, KhBCopySciencepublish khBCopySciencepublish);

        IPage<KhBCopySciencepublish> findKhBCopySciencepublishList(QueryRequest request, KhBCopySciencepublish khBCopySciencepublish);

        void createKhBCopySciencepublish(KhBCopySciencepublish khBCopySciencepublish);

        void updateKhBCopySciencepublish(KhBCopySciencepublish khBCopySciencepublish);

        void deleteKhBCopySciencepublishs(String[]Ids);

        List<KhBCopySciencepublish> getAll(String userAccount,String dcaYear);
        }
